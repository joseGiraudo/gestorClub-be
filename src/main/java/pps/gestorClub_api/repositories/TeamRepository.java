package pps.gestorClub_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pps.gestorClub_api.entities.TeamEntity;
import pps.gestorClub_api.enums.TeamSport;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    List<TeamEntity> findBySport(TeamSport sport);
}
