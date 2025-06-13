package pps.gestorClub_api.dtos.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.PaymentMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPayDTO {

    public Long paymentId;
    public PaymentMethod method;
}
