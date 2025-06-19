package pps.gestorClub_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pps.gestorClub_api.entities.PaymentEntity;
import pps.gestorClub_api.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {

    List<PaymentEntity> findByMemberIdAndStatus(Long memberId, PaymentStatus status);

    List<PaymentEntity> findByFeeMonthAndFeeYear(Integer month, Integer year);

    @Query("SELECT p FROM PaymentEntity p WHERE p.member.id = :memberId AND p.fee.month = :month AND p.fee.year = :year")
    Optional<PaymentEntity> findByMemberAndPeriod(@Param("memberId") Long memberId,
                                                  @Param("month") Integer month,
                                                  @Param("year") Integer year);

    @Query("SELECT COUNT(p) FROM PaymentEntity p WHERE p.status = :status")
    Long countByStatus(@Param("status") PaymentStatus status);

    List<PaymentEntity> findByFeeId(Long feeId);
}
