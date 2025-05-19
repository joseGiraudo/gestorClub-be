package pps.gestorClub_api.services;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

public interface MercadoPagoService {

    Preference createPreference(Long userId) throws MPException, MPApiException;

    String receiveNotification(String topic, String resource, Long userId) throws MPException, MPApiException;

}
