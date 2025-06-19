package pps.gestorClub_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pps.gestorClub_api.dtos.reports.payments.FeeCollectionReportDto;
import pps.gestorClub_api.dtos.reports.payments.FullPaymentReportDto;
import pps.gestorClub_api.services.PaymentReportService;

@RestController
@RequestMapping("/reports/payments")
public class PaymentReportController {

    @Autowired
    private PaymentReportService paymentReportService;

    @GetMapping("/full-report")
    public FullPaymentReportDto getFullReport() {
        return paymentReportService.getFullReport();
    }

    @GetMapping("/fee-report/{feeId}")
    public ResponseEntity<FeeCollectionReportDto> getFeeReport(
            @PathVariable Long feeId
    ) {
        FeeCollectionReportDto report = paymentReportService.getFeeCollectionReport(feeId);
        return ResponseEntity.ok(report);
    }
}
