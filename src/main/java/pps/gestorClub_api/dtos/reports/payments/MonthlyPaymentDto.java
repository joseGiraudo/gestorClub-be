package pps.gestorClub_api.dtos.reports.payments;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPaymentDto {
    private String month;
    private Integer year;
    private BigDecimal total;
}
