package pps.gestorClub_api.dtos.fees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDto {

    private Integer month;
    private Integer year;
    private BigDecimal amount;
}
