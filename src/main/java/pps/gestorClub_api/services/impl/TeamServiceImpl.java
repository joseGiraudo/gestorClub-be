package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import pps.gestorClub_api.dtos.teams.CreateTeamDto;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.entities.TeamEntity;
import pps.gestorClub_api.enums.TeamSport;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.models.Team;
import pps.gestorClub_api.repositories.MemberRepository;
import pps.gestorClub_api.repositories.TeamRepository;
import pps.gestorClub_api.services.TeamService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Team> getAll() {
        List<TeamEntity> teamEntities = teamRepository.findAll();

        return teamEntities.stream()
                .map(teamEntity -> {
                    Team team = modelMapper.map(teamEntity, Team.class);
                    return team;
                }).collect(Collectors.toList());
    }

    @Override
    public Team getById(Long id) {
        TeamEntity teamEntity = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el equipo con id: " + id));

        return modelMapper.map(teamEntity, Team.class);
    }

    @Override
    public Team create(CreateTeamDto team) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setName(team.getName());
        teamEntity.setDescription(team.getDescription());
        teamEntity.setSport(team.getSport());

        TeamEntity saved = teamRepository.save(teamEntity);
        return modelMapper.map(saved, Team.class);
    }

    @Override
    public Team update(Long id, CreateTeamDto team) {
        TeamEntity teamEntity = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el equipo con id: " + id));

        teamEntity.setName(team.getName());
        teamEntity.setDescription(team.getDescription());
        teamEntity.setSport(team.getSport());

        TeamEntity saved = teamRepository.save(teamEntity);
        return modelMapper.map(saved, Team.class);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Team> getBySport(TeamSport sport) {
        List<TeamEntity> teamEntities = teamRepository.findBySport(sport);

        return teamEntities.stream()
                .map(teamEntity -> {
                    Team team = modelMapper.map(teamEntity, Team.class);
                    return team;
                }).collect(Collectors.toList());
    }

    @Override
    public void updateMembers(Long teamId, List<Long> memberIds) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el equipo con id: " + teamId));

        List<MemberEntity> newMembers = memberRepository.findAllById(memberIds);

        team.getMembers().clear();              // Quita todos los miembros actuales
        team.getMembers().addAll(newMembers);   // Agrega los nuevos miembros

        teamRepository.save(team);
    }

//    @Override
//    public void removeMembers(Long teamId, List<Long> memberIds) {
//
//        TeamEntity teamEntity = teamRepository.findById(teamId)
//                .orElseThrow(() -> new EntityNotFoundException("No se encontró el equipo con id: " + teamId));
//
//        List<MemberEntity> membersToRemove = memberRepository.findAllById(memberIds);
//
//        if (membersToRemove.size() != memberIds.size()) {
//            throw new EntityNotFoundException("Uno o más socios a eliminar no se encontraron.");
//        }
//
//        teamEntity.getMembers().removeAll(membersToRemove);
//        teamRepository.save(teamEntity);
//    }

    @Override
    public List<Member> getTeamMembers(Long teamId) {

        return getById(teamId).getMembers();
    }
}
