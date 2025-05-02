package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.entities.FeeEntity;
import pps.gestorClub_api.entities.PaymentEntity;
import pps.gestorClub_api.models.Payment;
import pps.gestorClub_api.repositories.PaymentRepository;
import pps.gestorClub_api.services.PaymentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

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
}
