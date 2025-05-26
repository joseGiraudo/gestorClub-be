package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.dtos.payments.MonthlyDTO;
import pps.gestorClub_api.models.Payment;
import pps.gestorClub_api.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        Payment response = paymentService.create(payment);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long paymentId) {
//        paymentService.delete(paymentId);
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable("id") Long id,
            @Valid @RequestBody Payment payment) {
        Payment response = paymentService.update(id, payment);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate-orders")
    public ResponseEntity createMonthlyPayments(@Valid @RequestBody MonthlyDTO monthlyDTO) {

        paymentService.generateMonthlyPayments(monthlyDTO.getMonth(), monthlyDTO.getYear());

        return ResponseEntity.status(HttpStatus.CREATED).body("Ordenes de pago creadas");
    }

    @GetMapping("/pending/{memberId}")
    public ResponseEntity<List<Payment>> getPending(@PathVariable Long memberId) {

        return ResponseEntity.ok(paymentService.getPendingPayments(memberId));
    }

    @PostMapping("/pendings")
    public ResponseEntity<String> sendPendingEmails() {

        paymentService.sendPaymentsEmail();
        return ResponseEntity.ok("Emails enviados");
    }
}
