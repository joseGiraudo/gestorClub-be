package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.reports.members.MemberReportDto;
import pps.gestorClub_api.dtos.reports.members.MonthlyCountDto;
import pps.gestorClub_api.dtos.reports.members.SportCountDto;

import java.util.List;

public interface MemberReportService {

    public MemberReportDto getReport();

    public List<SportCountDto> getAthletesBySport();

    public List<MonthlyCountDto> getNewMembersPerMonth();
}
