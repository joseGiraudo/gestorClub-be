package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.fees.FeeDto;
import pps.gestorClub_api.models.Fee;
import pps.gestorClub_api.models.Member;

import java.util.List;

public interface FeeService {

    Fee getById(Long id);

    List<Fee> getAll();

    Fee create(FeeDto fee);

    Fee update(Long id, FeeDto fee);

    Fee getByMonthAndYear(Integer month, Integer year);

}
