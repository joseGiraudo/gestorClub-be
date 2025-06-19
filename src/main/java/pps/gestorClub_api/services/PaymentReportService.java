package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.reports.payments.*;

import java.util.List;

public interface PaymentReportService {

    public List<MonthlyPaymentDto> getMonthlyTotals();

    public List<PaymentStatusSummaryDto> getStatusSummary();

    public List<PaymentMethodSummaryDto> getMethodSummary();

    public CollectionRateDto getCollectionRate();

    public FullPaymentReportDto getFullReport();
    public FeeCollectionReportDto getFeeCollectionReport(Long feeId);
}
