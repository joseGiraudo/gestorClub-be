package pps.gestorClub_api.dtos.reports.payments;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullPaymentReportDto {

    private List<MonthlyPaymentDto> monthlyTotals;
    private List<PaymentStatusSummaryDto> statusSummary;
    private List<PaymentMethodSummaryDto> methodSummary;
    private CollectionRateDto collectionRate;
}
