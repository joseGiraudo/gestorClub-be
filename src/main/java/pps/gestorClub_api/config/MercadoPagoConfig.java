package pps.gestorClub_api.config;


import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.PreferenceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfig {

    @Bean
    public PreferenceClient preferenceClient() {
        return new PreferenceClient();
    }

    @Bean
    public PaymentClient paymentClient() {
        return new PaymentClient();
    }

    @Bean
    public MerchantOrderClient merchantOrderClient() {
        return new MerchantOrderClient();
    }

    @Bean
    public PaymentRefundClient paymentRefundClient() {
        return new PaymentRefundClient();
    }
}
