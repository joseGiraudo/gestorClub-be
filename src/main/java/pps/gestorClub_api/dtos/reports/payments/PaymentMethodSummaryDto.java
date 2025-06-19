package pps.gestorClub_api.dtos.reports.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodSummaryDto {

    private String method;
    private Long count;
}
