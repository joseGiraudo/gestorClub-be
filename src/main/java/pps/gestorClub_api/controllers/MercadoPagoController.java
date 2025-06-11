package pps.gestorClub_api.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.services.MercadoPagoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/mp")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/preference")
    public ResponseEntity<Map<String, String>> createPreference(@RequestBody List<Long> paymentIds)
            throws MPException, MPApiException {

        Preference preference = mercadoPagoService.createPreference(paymentIds);
        Map<String, String> response = new HashMap<>();
        response.put("init_point", preference.getInitPoint()); // URL para redirigir al usuario

        return ResponseEntity.ok(response);
    }

    @PostMapping("/notification")
    public ResponseEntity<Void> receiveNotification(@RequestParam("id") String id,
                                                    @RequestParam("topic") String topic) throws MPException, MPApiException {
        mercadoPagoService.receiveNotification(id, topic);
        return ResponseEntity.ok().build();
    }
}
