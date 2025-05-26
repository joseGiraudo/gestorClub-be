package pps.gestorClub_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.enums.MemberStatus;
import pps.gestorClub_api.models.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByDni(String dni);
    List<MemberEntity> findByStatus(MemberStatus status);
}
