package pps.gestorClub_api.services;

import pps.gestorClub_api.models.Fee;
import pps.gestorClub_api.models.Payment;

import java.util.List;

public interface PaymentService {

    Payment getById(Long id);

    List<Payment> getAll();

    Payment create(Payment payment);

    Payment update(Long id, Payment payment);
}
