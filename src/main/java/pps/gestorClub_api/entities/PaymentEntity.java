package pps.gestorClub_api.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.MemberType;
import pps.gestorClub_api.enums.PaymentMethod;
import pps.gestorClub_api.enums.PaymentStatus;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberId;

    @ManyToOne
    @JoinColumn(name = "fee_id", nullable = false)
    private FeeEntity feeId;

    @Column(name = "issued_date", nullable = false)
    private Date issuedDate;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentMethod method;

    @Column(name = "mercado_pago_id")
    private String mercadoPagoId;

    @ManyToOne
    @JoinColumn(name = "recorded_by")
    private UserEntity recordedBy;
}
