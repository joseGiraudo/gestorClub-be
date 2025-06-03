package pps.gestorClub_api.dtos.payments;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.PaymentMethod;
import pps.gestorClub_api.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long id;
    private Long memberId;
    private String memberName;
    private String memberDni;
    private Long feeId;
    private Integer feeMonth;
    private Integer feeYear;
    private BigDecimal feeAmount;
    private Date issuedDate;
    private Date paymentDate;
    private PaymentStatus status;
    private PaymentMethod method;
    private String mercadoPagoId;
    private String recordedBy;
}
