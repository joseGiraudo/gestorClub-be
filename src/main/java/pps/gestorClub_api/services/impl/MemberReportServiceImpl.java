package pps.gestorClub_api.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.dtos.reports.members.MemberReportDto;
import pps.gestorClub_api.dtos.reports.members.MonthlyCountDto;
import pps.gestorClub_api.dtos.reports.members.SportCountDto;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.entities.TeamEntity;
import pps.gestorClub_api.enums.TeamSport;
import pps.gestorClub_api.repositories.MemberRepository;
import pps.gestorClub_api.repositories.TeamRepository;
import pps.gestorClub_api.services.MemberReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberReportServiceImpl implements MemberReportService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;


    @Override
    public MemberReportDto getReport() {
        List<MemberEntity> allMembers = memberRepository.findAll();

        Integer total = allMembers.size();

        Map<String, Long> byAgeGroup = allMembers.stream()
                .map(this::calculateAge)
                .collect(Collectors.groupingBy(this::getAgeRange, Collectors.counting()));

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        long newThisMonth = allMembers.stream()
                .filter(m -> {
                    LocalDateTime created = m.getCreatedDate();
                    return created != null && created.isAfter(oneMonthAgo);
                })
                .count();

        return new MemberReportDto(total, byAgeGroup, newThisMonth);
    }


    public List<MonthlyCountDto> getNewMembersPerMonth() {
        List<MemberEntity> allMembers = memberRepository.findAll();

        return allMembers.stream()
                .filter(m -> m.getCreatedDate() != null)
                .collect(Collectors.groupingBy(
                        m -> m.getCreatedDate().getYear() + "-" + String.format("%02d", m.getCreatedDate().getMonthValue()),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(e -> new MonthlyCountDto(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(MonthlyCountDto::getMonth))
                .collect(Collectors.toList());
    }

    @Override
    public List<SportCountDto> getAthletesBySport() {
        List<TeamEntity> allTeams = teamRepository.findAll();

        // Map<TeamSport, Set<Long>> para evitar contar duplicados
        Map<TeamSport, Set<Long>> sportToMemberIds = new HashMap<>();

        for (TeamEntity team : allTeams) {
            TeamSport sport = team.getSport();
            List<MemberEntity> members = team.getMembers();

            sportToMemberIds
                    .computeIfAbsent(sport, k -> new HashSet<>())
                    .addAll(members.stream()
                            .map(MemberEntity::getId)
                            .collect(Collectors.toSet()));
        }

        return sportToMemberIds.entrySet().stream()
                .map(e -> new SportCountDto(e.getKey().name(), e.getValue().size()))
                .collect(Collectors.toList());
    }



    private int calculateAge(MemberEntity member) {
        return Period.between(member.getBirthdate(), LocalDate.now()).getYears();
    }

    private String getAgeRange(int age) {
        if (age < 18) return "0-17";
        if (age < 26) return "18-25";
        if (age < 31) return "25-30";
        if (age < 41) return "30-40";
        return "41+";
    }
}
