package pps.gestorClub_api.dtos.reports.members;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberReportDto {

    private Integer totalMembers;

    private Map<String, Long> byAgeGroup;

    private Long newThisMonth;

}
