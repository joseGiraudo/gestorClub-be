package pps.gestorClub_api.services.impl;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.dtos.reports.payments.*;
import pps.gestorClub_api.entities.FeeEntity;
import pps.gestorClub_api.entities.PaymentEntity;
import pps.gestorClub_api.enums.PaymentStatus;
import pps.gestorClub_api.repositories.FeeRepository;
import pps.gestorClub_api.repositories.PaymentRepository;
import pps.gestorClub_api.services.PaymentReportService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentReportServiceImpl implements PaymentReportService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    FeeRepository feeRepository;


    @Override
    public List<MonthlyPaymentDto> getMonthlyTotals() {
        // Agrupar por año-mes
        Map<String, List<PaymentEntity>> grouped = paymentRepository.findAll().stream()
                .filter(p -> p.getIssuedDate() != null)
                .collect(Collectors.groupingBy(p -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(p.getIssuedDate());
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH) + 1;
                    return year + "-" + month;
                }));

        // Transformar a DTO con ambos totales
        return grouped.entrySet().stream()
                .map(entry -> {
                    String[] parts = entry.getKey().split("-");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    List<PaymentEntity> payments = entry.getValue();

                    BigDecimal totalIssued = payments.stream()
                            .map(p -> p.getFee().getAmount())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalPaid = payments.stream()
                            .filter(p -> p.getStatus().equals(PaymentStatus.APPROVED))
                            .map(p -> p.getFee().getAmount())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new MonthlyPaymentDto(month, year, totalIssued, totalPaid);
                })
                .sorted(Comparator.comparing(MonthlyPaymentDto::getYear)
                        .thenComparing(MonthlyPaymentDto::getMonth))
                .toList();
    }

    @Override
    public List<PaymentStatusSummaryDto> getStatusSummary() {
        return paymentRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getStatus().name(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(e -> new PaymentStatusSummaryDto(e.getKey(), e.getValue()))
                .toList();
    }

    @Override
    public List<PaymentMethodSummaryDto> getMethodSummary() {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getMethod() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getMethod().name(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(e -> new PaymentMethodSummaryDto(e.getKey(), e.getValue()))
                .toList();
    }

    @Override
    public CollectionRateDto getCollectionRate() {
        long totalIssued = paymentRepository.count();
        long totalPaid = paymentRepository.findAll().stream()
                .filter(p -> p.getStatus().name().equalsIgnoreCase("APPROVED"))
                .count();

        double percentage = totalIssued == 0 ? 0.0 : (totalPaid * 100.0) / totalIssued;

        return new CollectionRateDto(totalIssued, totalPaid, percentage);
    }

    @Override
    public FullPaymentReportDto getFullReport() {
        return new FullPaymentReportDto(
                getMonthlyTotals(),
                getStatusSummary(),
                getMethodSummary(),
                getCollectionRate()
        );
    }

    @Override
    public FeeCollectionReportDto getFeeCollectionReport(Long feeId) {
        FeeEntity fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new EntityNotFoundException("Fee no encontrada"));

        List<PaymentEntity> payments = paymentRepository.findByFeeId(feeId);

        long issuedCount = payments.size();
        long paidCount = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.APPROVED)
                .count();

        BigDecimal totalCollected = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.APPROVED)
                .map(p -> fee.getAmount()) // o p.getAmount() si lo tenés por pago
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expectedTotal = fee.getAmount().multiply(BigDecimal.valueOf(issuedCount));

        BigDecimal collectionRate = expectedTotal.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : totalCollected
                .multiply(BigDecimal.valueOf(100))
                .divide(expectedTotal, 2, RoundingMode.HALF_UP);

        String label = fee.getMonth() + "/" + fee.getYear();

        // Agrupar por estado y contar
        List<PaymentStatusSummaryDto> statusSummary = payments.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getStatus().name(),
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> new PaymentStatusSummaryDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        // Agrupar por método de pago y contar
        List<PaymentMethodSummaryDto> methodSummary = payments.stream()
                .filter(p -> p.getMethod() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getMethod().name(),
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> new PaymentMethodSummaryDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return new FeeCollectionReportDto(
                fee.getId(),
                label,
                issuedCount,
                paidCount,
                totalCollected,
                expectedTotal,
                collectionRate,
                statusSummary,
                methodSummary
        );
    }
}
