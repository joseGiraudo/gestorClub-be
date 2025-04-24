package pps.gestorClub_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pps.gestorClub_api.entities.NewsEntity;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
}
