package pps.gestorClub_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pps.gestorClub_api.dtos.payments.PaymentDto;
import pps.gestorClub_api.models.Fee;
import pps.gestorClub_api.models.Payment;

import java.util.List;

public interface PaymentService {

    Payment getById(Long id);

    List<Payment> getAll();

    Page<PaymentDto> getPayments(Pageable pageable, String memberSearch, String status,
                                 String method, Integer month, Integer year,
                                 String dateFrom, String dateTo);

    Payment create(Payment payment);

    Payment update(Long id, Payment payment);

    List<Payment> getPendingPayments(Long memberId);

    void generateMonthlyPayments(Integer month, Integer Year);
    void sendPaymentsEmail();
}
