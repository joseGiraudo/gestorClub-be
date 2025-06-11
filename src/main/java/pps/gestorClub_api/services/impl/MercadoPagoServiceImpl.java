package pps.gestorClub_api.services.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderPayment;
import com.mercadopago.resources.preference.Preference;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.models.Payment;
import pps.gestorClub_api.services.MercadoPagoService;
import pps.gestorClub_api.services.PaymentService;
import pps.gestorClub_api.services.UserService;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentClient paymentClient; // Cliente de Mercado Pago SDK

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
    public Preference createPreference(List<Long> paymentsIds) throws MPException, MPApiException {

        List<PreferenceItemRequest> itemRequests = new ArrayList<>();
        for (Long id : paymentsIds) {
            Payment payment = paymentService.getById(id);

            // Crear item
            PreferenceItemRequest preferenceItemRequest = PreferenceItemRequest.builder()
                    .title("Cuota " + payment.getFee().getMonth() + "/" + payment.getFee().getYear() +
                            " -  N°: " + payment.getId())
                    .quantity(1)
                    .unitPrice(payment.getFee().getAmount())
                    .currencyId(CURRENCY)
                    .build();
            itemRequests.add(preferenceItemRequest);
        }

        String notificationUrl = this.BACK_URL + "/api/v1/mp/notification";

        // Configurar URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(BACK_URL + "/payment/success")
                .pending(BACK_URL + "/payment/pending")
                .failure(BACK_URL + "/payment/failure")
                .build();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("paymentIds", paymentsIds);

        // Crear preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(itemRequests)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference("MP-" + System.currentTimeMillis())
                .metadata(metadata)
                .build();

        return preferenceClient.create(preferenceRequest);
    }

    @Override
    public String receiveNotification(String topic, String resource) throws MPException, MPApiException {

        if ("merchant_order".equalsIgnoreCase(topic) && resource != null) {
            // Extraer el ID de la orden desde la URL del recurso
            String[] resourceParts = resource.split("/");
            Long merchantOrderId = Long.valueOf(resourceParts[resourceParts.length - 1]);

            MerchantOrder merchantOrder = this.merchantOrderClient.get(merchantOrderId);

            if ("paid".equalsIgnoreCase(merchantOrder.getOrderStatus())) {

                // Recorremos los pagos asociados
                List<MerchantOrderPayment> payments = merchantOrder.getPayments();
                Set<Long> processedPaymentIds = new HashSet<>();

                for (MerchantOrderPayment mop : payments) {
                    // Validamos estado del pago
                    if (!"approved".equalsIgnoreCase(mop.getStatus())) continue;

                    // Obtener el Payment completo
                    com.mercadopago.resources.payment.Payment mpPayment = paymentClient.get(mop.getId());

                    Map<String, Object> metadata = mpPayment.getMetadata();

                    if (metadata != null && metadata.containsKey("paymentIds")) {

                        List<Integer> paymentIds = (List<Integer>) metadata.get("paymentIds");

                        for (Integer id : paymentIds) {
                            Long paymentId = id.longValue();
                            if (processedPaymentIds.add(paymentId)) {
                                paymentService.markAsPaidMercadoPago(paymentId, mpPayment.getId()); // Marcás la cuota como pagada en tu BD
                            }
                        }
                    }
                }
            }
        }

        return "Notificación recibida";
    }
}
