package pps.gestorClub_api.dtos.reports.members;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyCountDto {

    private String month; // Formato "YYYY-MM"

    private long count;

}
