package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.dtos.payments.PaymentDto;
import pps.gestorClub_api.entities.FeeEntity;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.entities.PaymentEntity;
import pps.gestorClub_api.enums.MemberStatus;
import pps.gestorClub_api.enums.PaymentMethod;
import pps.gestorClub_api.enums.PaymentStatus;
import pps.gestorClub_api.models.Payment;
import pps.gestorClub_api.repositories.FeeRepository;
import pps.gestorClub_api.repositories.MemberRepository;
import pps.gestorClub_api.repositories.PaymentRepository;
import pps.gestorClub_api.services.EmailService;
import pps.gestorClub_api.services.PaymentService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Payment getById(Long id) {
        PaymentEntity paymentEntity = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el pago con id: " + id));

        return modelMapper.map(paymentEntity, Payment.class);
    }

    @Override
    public List<Payment> getAll() {
        List<PaymentEntity> paymentEntities = paymentRepository.findAll();

        System.out.println("response: " + paymentEntities);

        return paymentEntities.stream()
                .map(paymentEntity -> {
                    return modelMapper.map(paymentEntity, Payment.class);
                }).collect(Collectors.toList());
    }

    public Page<PaymentDto> getPayments(Pageable pageable, String memberSearch, String status,
                                        String method, Integer month, Integer year,
                                        String dateFrom, String dateTo) {

        // Construir especificación para filtros dinámicos
        Specification<PaymentEntity> spec = Specification.where(null);

        // Filtro por búsqueda de miembro (nombre, apellido, DNI)
        if (memberSearch != null && !memberSearch.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<PaymentEntity, MemberEntity> memberJoin = root.join("member", JoinType.LEFT);
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(memberJoin.get("name")),
                                "%" + memberSearch.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(memberJoin.get("lastName")),
                                "%" + memberSearch.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(memberJoin.get("dni")),
                                "%" + memberSearch.toLowerCase() + "%")
                );
            });
        }

        // Filtro por status
        if (status != null && !status.trim().isEmpty()) {
            try {
                PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("status"), paymentStatus)
                );
            } catch (IllegalArgumentException e) {
                // Status inválido, ignorar filtro
            }
        }

        // Filtro por método de pago
        if (method != null && !method.trim().isEmpty()) {
            try {
                PaymentMethod paymentMethod = PaymentMethod.valueOf(method.toUpperCase());
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("method"), paymentMethod)
                );
            } catch (IllegalArgumentException e) {
                // Método inválido, ignorar filtro
            }
        }

        // Filtro por período (mes y año de la cuota)
        if (month != null || year != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<PaymentEntity, FeeEntity> feeJoin = root.join("fee", JoinType.LEFT);
                Predicate predicate = criteriaBuilder.conjunction();

                if (month != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.equal(feeJoin.get("month"), month));
                }

                if (year != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.equal(feeJoin.get("year"), year));
                }

                return predicate;
            });
        }

        // Filtro por rango de fechas de pago
        if (dateFrom != null || dateTo != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Predicate predicate = criteriaBuilder.conjunction();

                try {
                    if (dateFrom != null && !dateFrom.trim().isEmpty()) {
                        LocalDate localFromDate = LocalDate.parse(dateFrom);
                        Date fromDate = java.sql.Date.valueOf(localFromDate);
                        predicate = criteriaBuilder.and(predicate,
                                criteriaBuilder.greaterThanOrEqualTo(root.get("paymentDate"), fromDate));
                    }

                    if (dateTo != null && !dateTo.trim().isEmpty()) {
                        LocalDate localToDate = LocalDate.parse(dateTo);
                        Date toDate = java.sql.Date.valueOf(localToDate);
                        predicate = criteriaBuilder.and(predicate,
                                criteriaBuilder.lessThanOrEqualTo(root.get("paymentDate"), toDate));
                    }
                } catch (Exception e) {
                    // Fechas inválidas, ignorar filtro
                }

                return predicate;
            });
        }

        Page<PaymentEntity> paymentsPage = paymentRepository.findAll(spec, pageable);
        return paymentsPage.map(this::convertToDto);
    }

    @Override
    public Payment create(Payment payment) {
        PaymentEntity paymentEntity = modelMapper.map(payment, PaymentEntity.class);

        PaymentEntity savedEntity = paymentRepository.save(paymentEntity);
        return modelMapper.map(savedEntity, Payment.class);
    }

    @Override
    public Payment update(Long id, Payment payment) {
        PaymentEntity existingEntity = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el pago con id: " + id));

        PaymentEntity updatedEntity = paymentRepository.save(modelMapper.map(payment, PaymentEntity.class));
        return modelMapper.map(updatedEntity, Payment.class);
    }

    @Override
    public void generateMonthlyPayments(Integer month, Integer year) {
        // busco la cuota a pagar
        FeeEntity fee = feeRepository.findByMonthAndYear(month, year)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuota para el mes: " + month + " del " + year));

        // obtengo todos los miembros que esten activos
        List<MemberEntity> members = memberRepository.findByStatus(MemberStatus.ACTIVE);

        for (MemberEntity member : members) {
            PaymentEntity payment = generatePayment(member, fee);
            paymentRepository.save(payment);
        }
    }


    @Override
    public List<Payment> getPendingPayments(String dni) {
        MemberEntity memberEntity = memberRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con dni: " + dni));

        List<PaymentEntity> paymentEntities = paymentRepository.findByMemberIdAndStatus(memberEntity.getId(), PaymentStatus.PENDING);

        return paymentEntities.stream()
                .map(paymentEntity -> {
                    return modelMapper.map(paymentEntity, Payment.class);
                }).collect(Collectors.toList());
    }

    public void sendPendingPaymentsEmail() {
        // obtengo todos los miembros
        List<MemberEntity> members = memberRepository.findAll();

        for(MemberEntity member : members) {
            List<Payment> payments = getPendingPayments(member.getDni());
            if(!payments.isEmpty()) {
                String fullName = member.getName() + " " + member.getLastName();
                emailService.sendPaymentsEmail(member.getEmail(), fullName, payments);
            }
        }
    }

    @Override
    public void markAsPaidMercadoPago(Long paymentId, Long mercadoPagoPaymentId) {
        Payment payment = getById(paymentId);

        // Evitar sobrescrituras si ya está pagado
        if (payment.getStatus() == PaymentStatus.APPROVED) {
            return;
        }

        payment.setStatus(PaymentStatus.APPROVED);
        payment.setMethod(PaymentMethod.MERCADO_PAGO);
        payment.setMercadoPagoId(String.valueOf(mercadoPagoPaymentId));
        payment.setPaymentDate(Date.from(Instant.now()));

        paymentRepository.save(modelMapper.map(payment, PaymentEntity.class));
    }

    // metodo para generar una orden de pago para un miembro a partir de una cuota
    private PaymentEntity generatePayment(MemberEntity member, FeeEntity fee) {
        PaymentEntity payment = new PaymentEntity();

        payment.setMember(member);
        payment.setFee(fee);
        payment.setIssuedDate(new Date());
        payment.setPaymentDate(null);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setMethod(null);
        payment.setMercadoPagoId(null);
        payment.setRecordedBy(null);

        return payment;
    }

    private PaymentDto convertToDto(PaymentEntity payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .memberId(payment.getMember().getId())
                .memberName(payment.getMember().getName() + " " + payment.getMember().getLastName())
                .memberDni(payment.getMember().getDni())
                .feeId(payment.getFee().getId())
                .feeMonth(payment.getFee().getMonth())
                .feeYear(payment.getFee().getYear())
                .feeAmount(payment.getFee().getAmount())
                .issuedDate(payment.getIssuedDate())
                .paymentDate(payment.getPaymentDate())
                .status(payment.getStatus())
                .method(payment.getMethod())
                .mercadoPagoId(payment.getMercadoPagoId())
                .recordedBy(payment.getRecordedBy() != null ?
                        payment.getRecordedBy().getName() : null)
                .build();
    }
}
