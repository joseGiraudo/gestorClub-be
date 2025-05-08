package pps.gestorClub_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pps.gestorClub_api.entities.FeeEntity;

import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<FeeEntity, Long> {
    Optional<FeeEntity> findByMonthAndYear(Integer month, Integer year);
}
