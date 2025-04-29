package pps.gestorClub_api.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fee {

    private Long id;

    private Integer month;

    private Integer year;

    private BigDecimal amount;
}
