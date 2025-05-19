package pps.gestorClub_api.services.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.services.MercadoPagoService;
import pps.gestorClub_api.services.UserService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MercadoPagoServiceImpl implements MercadoPagoService {

    private final PreferenceClient preferenceClient;

    private final MerchantOrderClient merchantOrderClient;

    private final PaymentRefundClient paymentRefundClient;

    private final UserService userService;

    @Value("${front.url}")
    private String WEB_URL;

    @Value("${back.url}")
    private String BACK_URL;

    @Value("${mercado.pago.token}")
    private String MP_TOKEN;

    private static final String CURRENCY = "ARS";

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(MP_TOKEN);
    }



    @Override
    public Preference createPreference(Long userId) throws MPException, MPApiException {

        PreferenceItemRequest preferenceItemRequest = PreferenceItemRequest.builder()
                .title("Club Dep. Independencia")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(5000))
                .currencyId(CURRENCY)
                .build();

        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success(WEB_URL)
                .failure(WEB_URL)
                .pending(WEB_URL)
                .build();

        String notificationUrl = this.BACK_URL + "/api/v1/mp/notification/" + userId;

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(preferenceItemRequest))
                .backUrls(backUrlsRequest)
                .notificationUrl(notificationUrl)
                .autoReturn("approved")
                .expires(true)
                .expirationDateTo(OffsetDateTime.now().plusMinutes(5L))
                .build();

        return preferenceClient.create(preferenceRequest);
    }

    @Override
    public String receiveNotification(String topic, String resource, Long userId) throws MPException, MPApiException {
        return "";
    }
}
