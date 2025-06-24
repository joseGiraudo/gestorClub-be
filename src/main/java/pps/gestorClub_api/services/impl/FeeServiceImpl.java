package pps.gestorClub_api.services.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.dtos.fees.FeeDto;
import pps.gestorClub_api.dtos.fees.FeeStatsDto;
import pps.gestorClub_api.entities.FeeEntity;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.entities.PaymentEntity;
import pps.gestorClub_api.enums.PaymentStatus;
import pps.gestorClub_api.models.Fee;
import pps.gestorClub_api.models.Payment;
import pps.gestorClub_api.repositories.FeeRepository;
import pps.gestorClub_api.services.FeeService;
import pps.gestorClub_api.services.PaymentService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Fee getById(Long id) {
        FeeEntity feeEntity = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuota con id: " + id));

        return modelMapper.map(feeEntity, Fee.class);
    }

    @Override
    public List<Fee> getAll() {

        List<FeeEntity> feeEntities = feeRepository.findAll();

        return feeEntities.stream()
                .sorted(Comparator
                        .comparing(FeeEntity::getYear).reversed()
                        .thenComparing(FeeEntity::getMonth, Comparator.reverseOrder()))
                .map(feeEntity -> modelMapper.map(feeEntity, Fee.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeeStatsDto> getAllWithStats() {

        List<FeeEntity> feeEntities = feeRepository.findAll();

        return feeEntities.stream()
                .sorted(Comparator
                        .comparing(FeeEntity::getYear, Comparator.reverseOrder())
                        .thenComparing(FeeEntity::getMonth, Comparator.reverseOrder())
                )
                .map(fee -> {
                    List<Payment> payments = paymentService.getPaymentsByFeeId(fee.getId());

                    long issuedCount = payments.size();
                    long paidCount = payments.stream()
                            .filter(p -> p.getStatus() == PaymentStatus.APPROVED)
                            .count();

                    FeeStatsDto dto = new FeeStatsDto();
                    dto.setId(fee.getId());
                    dto.setMonth(fee.getMonth());
                    dto.setYear(fee.getYear());
                    dto.setAmount(fee.getAmount());
                    dto.setIssuedCount(issuedCount);
                    dto.setPaidCount(paidCount);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Fee createFeeAndGeneratePayments(FeeDto fee) {
        // 1. Verificar existencia de la cuota
        Optional<FeeEntity> existingFee = feeRepository.findByMonthAndYear(fee.getMonth(), fee.getYear());
        if (existingFee.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuota para ese mes y año");
        }

        // 2. Crear cuota
        FeeEntity feeEntity = modelMapper.map(fee, FeeEntity.class);
        FeeEntity savedFee = feeRepository.save(feeEntity);

        // 3. Generar pagos (esto debe lanzar excepción si algo falla)
        paymentService.generateMonthlyPayments(fee.getMonth(), fee.getYear());

        return modelMapper.map(savedFee, Fee.class);
    }

    @Override
    public Fee create(FeeDto fee) {
        Optional<FeeEntity> existingFee = feeRepository.findByMonthAndYear(fee.getMonth(), fee.getYear());

        if(existingFee.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuota para ese mes y año");
        }

        FeeEntity feeEntity = new FeeEntity();
        feeEntity.setMonth(fee.getMonth());
        feeEntity.setYear(fee.getYear());
        feeEntity.setAmount(fee.getAmount());

        return modelMapper.map(feeRepository.save(feeEntity), Fee.class);
    }

    @Override
    public Fee update(Long id, FeeDto fee) {
        FeeEntity existingEntity = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuota con id: " + id));

        existingEntity.setMonth(fee.getMonth());
        existingEntity.setYear(fee.getYear());
        existingEntity.setAmount(fee.getAmount());
        FeeEntity updatedEntity = feeRepository.save(existingEntity);

        return modelMapper.map(updatedEntity, Fee.class);
    }

    @Override
    public Fee getByMonthAndYear(Integer month, Integer year) {
        FeeEntity feeEntity = feeRepository.findByMonthAndYear(month, year)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuota para el mes: " + month + " del " + year));

        return modelMapper.map(feeEntity, Fee.class);
    }
}
