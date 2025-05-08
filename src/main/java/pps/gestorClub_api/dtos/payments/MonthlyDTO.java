package pps.gestorClub_api.dtos.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDTO {

    private Integer month;
    private Integer year;
}
