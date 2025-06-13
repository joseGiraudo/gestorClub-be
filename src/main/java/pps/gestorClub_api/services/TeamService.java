package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.teams.CreateTeamDto;
import pps.gestorClub_api.dtos.teams.TeamMemberDto;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.enums.TeamSport;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.models.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAll();

    Team getById(Long id);

    Team create(CreateTeamDto team);

    Team update(Long id, CreateTeamDto team);

    void delete(Long id);

    List<Team> getBySport(TeamSport sport);

    void updateMembers(Long teamId, List<Long> memberIds);

    // void removeMembers(Long teamId, List<Long> memberIds);

    List<Member> getTeamMembers(Long teamId);

}
