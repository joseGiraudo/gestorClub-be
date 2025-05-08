package pps.gestorClub_api.models;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.entities.FeeEntity;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.entities.UserEntity;
import pps.gestorClub_api.enums.PaymentMethod;
import pps.gestorClub_api.enums.PaymentStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long id;

    private Member memberId;

    private Fee feeId;

    private Date issuedDate;

    private Date paymentDate;

    private PaymentStatus status;

    private PaymentMethod method;

    private String mercadoPagoId;

    private User recordedBy;
}
