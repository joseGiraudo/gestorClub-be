package pps.gestorClub_api.dtos.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamMemberDto {
    private List<Long> memberIds;
}
