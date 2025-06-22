package pps.gestorClub_api.dtos.fees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeStatsDto {
    private Long id;
    private int month;
    private int year;
    private BigDecimal amount;
    private long issuedCount;
    private long paidCount;
}
