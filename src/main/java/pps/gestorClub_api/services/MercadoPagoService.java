package pps.gestorClub_api.services;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import java.util.List;

public interface MercadoPagoService {

    Preference createPreference(List<Long> paymentsIds) throws MPException, MPApiException;

    String receiveNotification(String resource, String topic) throws MPException, MPApiException;

}
