package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.entities.FeeEntity;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.entities.PaymentEntity;
import pps.gestorClub_api.enums.PaymentStatus;
import pps.gestorClub_api.models.Payment;
import pps.gestorClub_api.repositories.FeeRepository;
import pps.gestorClub_api.repositories.MemberRepository;
import pps.gestorClub_api.repositories.PaymentRepository;
import pps.gestorClub_api.services.EmailService;
import pps.gestorClub_api.services.PaymentService;

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

        return paymentEntities.stream()
                .map(paymentEntity -> {
                    return modelMapper.map(paymentEntity, Payment.class);
                }).collect(Collectors.toList());
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

        // obtengo todos los miembros
        List<MemberEntity> members = memberRepository.findAll();

        for (MemberEntity member : members) {
            PaymentEntity payment = generatePayment(member, fee);
            paymentRepository.save(payment);
        }
    }


    @Override
    public List<Payment> getPendingPayments(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + memberId));

        List<PaymentEntity> paymentEntities = paymentRepository.findByStatusAndMemberId(PaymentStatus.PENDING, memberEntity);

        return paymentEntities.stream()
                .map(paymentEntity -> {
                    return modelMapper.map(paymentEntity, Payment.class);
                }).collect(Collectors.toList());
    }

    public void sendPaymentsEmail() {
        // obtengo todos los miembros
        List<MemberEntity> members = memberRepository.findAll();

        for(MemberEntity member : members) {
            List<Payment> payments = getPendingPayments(member.getId());
            if(!payments.isEmpty()) {
                String fullName = member.getName() + " " + member.getLastName();
                emailService.sendPaymentsEmail(member.getEmail(), fullName, payments);
            }
        }
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
}
