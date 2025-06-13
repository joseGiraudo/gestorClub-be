package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.dtos.payments.MonthlyDTO;
import pps.gestorClub_api.dtos.payments.PaymentDto;
import pps.gestorClub_api.dtos.payments.PaymentPayDTO;
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


    @GetMapping("/filters")
    public ResponseEntity<Page<PaymentDto>> getPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String memberSearch,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String method,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDir.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        Page<PaymentDto> payments = paymentService.getPayments(
                pageable, memberSearch, status, method, month, year, dateFrom, dateTo
        );
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
    public ResponseEntity<PaymentDto> updatePayment(
            @PathVariable("id") Long id,
            @Valid @RequestBody PaymentDto payment) {
        PaymentDto response = paymentService.update(id, payment);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate-orders")
    public ResponseEntity createMonthlyPayments(@Valid @RequestBody MonthlyDTO monthlyDTO) {

        paymentService.generateMonthlyPayments(monthlyDTO.getMonth(), monthlyDTO.getYear());

        return ResponseEntity.status(HttpStatus.CREATED).body("Ordenes de pago creadas");
    }

    @GetMapping("/pending/{dni}")
    public ResponseEntity<List<Payment>> getPending(@PathVariable String dni) {

        return ResponseEntity.ok(paymentService.getPendingPayments(dni));
    }

    @PostMapping("/pendings")
    public ResponseEntity<String> sendPendingEmails() {

        paymentService.sendPendingPaymentsEmail();
        return ResponseEntity.ok("Emails enviados");
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@Valid @RequestBody PaymentPayDTO payDTO) {

        paymentService.markAsPaid(payDTO);
        return ResponseEntity.ok("Pago aprobado correctamente");
    }
}
