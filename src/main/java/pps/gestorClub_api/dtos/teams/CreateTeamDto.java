package pps.gestorClub_api.dtos.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.TeamSport;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTeamDto {
    private String name;
    private String description;
    private TeamSport sport;
}
