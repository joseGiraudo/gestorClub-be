package pps.gestorClub_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.entities.PaymentEntity;
import pps.gestorClub_api.enums.PaymentStatus;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByStatusAndMemberId(PaymentStatus status, Long memberId);

}
