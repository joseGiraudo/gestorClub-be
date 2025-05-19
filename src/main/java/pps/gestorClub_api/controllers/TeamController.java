package pps.gestorClub_api.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.dtos.members.PostMemberDto;
import pps.gestorClub_api.dtos.teams.CreateTeamDto;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.models.Team;
import pps.gestorClub_api.services.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAll();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getMTeamById(@PathVariable Long id) {
        Team team = teamService.getById(id);
        return ResponseEntity.ok(team);
    }

    @PostMapping("")
    public ResponseEntity<Team> createTeam(@Valid @RequestBody CreateTeamDto teamDto) {
        Team response = teamService.create(teamDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreateTeamDto teamDto) {
        Team response = teamService.update(id, teamDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/add-member")
    public ResponseEntity<Void> addMember(
            @PathVariable("id") Long id,
            @RequestBody Long memberId) {
        teamService.addMember(id, memberId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/remove-member")
    public ResponseEntity<Void> removeMember(
            @PathVariable("id") Long id,
            @RequestBody Long memberId) {
        teamService.removeMember(id, memberId);

        return ResponseEntity.ok().build();
    }
}
