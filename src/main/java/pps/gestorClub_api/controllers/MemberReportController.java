package pps.gestorClub_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pps.gestorClub_api.dtos.reports.members.MemberReportDto;
import pps.gestorClub_api.dtos.reports.members.MonthlyCountDto;
import pps.gestorClub_api.dtos.reports.members.SportCountDto;
import pps.gestorClub_api.services.MemberReportService;

import java.util.List;

@RestController
@RequestMapping("/reports/members")
public class MemberReportController {

    @Autowired
    private MemberReportService memberReportService;

    @GetMapping("/summary")
    public MemberReportDto getSummary() {
        return memberReportService.getReport();
    }

    @GetMapping("/athletes-by-sport")
    public List<SportCountDto> getAthletesBySport() {
        return memberReportService.getAthletesBySport();
    }

    @GetMapping("/new-members-per-month")
    public List<MonthlyCountDto> getNewMembersPerMonth() {
        return memberReportService.getNewMembersPerMonth();
    }
}
