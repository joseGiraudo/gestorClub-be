package pps.gestorClub_api.services.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderPayment;
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

    // Inyección por constructor con @RequiredArgsConstructor
    private final PaymentService paymentService;
    private final UserService userService;

    // Clientes de MercadoPago - se inicializan después de @PostConstruct
    private PaymentClient paymentClient;
    private PreferenceClient preferenceClient;
    private MerchantOrderClient merchantOrderClient;
    private PaymentRefundClient paymentRefundClient;

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

        // Inicializar clientes después de configurar el token
        this.paymentClient = new PaymentClient();
        this.preferenceClient = new PreferenceClient();
        this.merchantOrderClient = new MerchantOrderClient();
        this.paymentRefundClient = new PaymentRefundClient();
    }


    @Override
    public Preference createPreference(List<Long> paymentsIds) throws MPException, MPApiException {

        List<PreferenceItemRequest> itemRequests = new ArrayList<>();
        for (Long id : paymentsIds) {
            Payment payment = paymentService.getById(id);

            // Crear item
            PreferenceItemRequest preferenceItemRequest = PreferenceItemRequest.builder()
                    .title("Club Independencia. Cuota N°: " + payment.getId() +
                            ". Referencia " + payment.getFee().getMonth() + " - " + payment.getFee().getYear())
                    .quantity(1)
                    .unitPrice(payment.getFee().getAmount())
                    .currencyId(CURRENCY)
                    .build();
            itemRequests.add(preferenceItemRequest);
        }

        String notificationUrl = this.BACK_URL + "/api/v1/mp/notification";

        // Configurar URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(BACK_URL + "/api/v1/mp/success")
                .pending(BACK_URL + "/api/v1/mp/pending")
                .failure(BACK_URL + "/api/v1/mp/failure")
                .build();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("payment_ids", paymentsIds);

        // Crear preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(itemRequests)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference("MP-" + System.currentTimeMillis())
                .metadata(metadata)
                .notificationUrl(notificationUrl)
                .build();

        return preferenceClient.create(preferenceRequest);
    }

            @Override
            public String receiveNotification(String resource, String topic) throws MPException, MPApiException {

                if ("merchant_order".equalsIgnoreCase(topic) && resource != null) {
                    System.out.println("✅ Notificación recibida: topic = " + topic + ", resource = " + resource);

                    // Extraer el ID de la orden desde la URL del recurso
                    String[] resourceParts = resource.split("/");
                    Long merchantOrderId = Long.valueOf(resourceParts[resourceParts.length - 1]);

                    MerchantOrder merchantOrder = this.merchantOrderClient.get(merchantOrderId);

                    System.out.println("🧾 Orden de pago encontrada: " + merchantOrder.getId());

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

                            System.out.println("📦 Metadata del pago: " + metadata);

                            if (metadata != null && metadata.containsKey("payment_ids")) {
                                List<?> rawIds = (List<?>) metadata.get("payment_ids");

                                for (Object rawId : rawIds) {
                                    Long paymentId = ((Number) rawId).longValue(); // ⚠️ Maneja Double, Integer, etc.

                                    if (processedPaymentIds.add(paymentId)) {
                                        paymentService.markAsPaidMercadoPago(paymentId, mpPayment.getId());
                                        System.out.println("✅ Cuota marcada como pagada: " + paymentId);
                                    }
                                }
                            }
                        }
                    }
                }

                return "Notificación recibida";
            }
}
