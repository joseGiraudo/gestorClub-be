package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.teams.CreateTeamDto;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.models.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAll();

    Team getById(Long id);

    Team create(CreateTeamDto team);

    Team update(Long id, CreateTeamDto team);

    void delete(Long id);

    void addMember(Long teamId, Long memberId);

    void removeMember(Long teamId, Long memberId);

    List<Member> getTeamMembers(Long teamId);

}
