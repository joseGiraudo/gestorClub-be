package pps.gestorClub_api.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.services.MercadoPagoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mp")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Value("${front.url}")
    private String WEB_URL;

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


//    // URLs para redirigir al front



//    @GetMapping("/success")
//    public String paymentSuccess(Model model, @RequestParam Map<String, String> params) {
//        model.addAttribute("message", "¡Pago aprobado con éxito!");
//        model.addAttribute("returnUrl", WEB_URL + "/payment/success");
//        return "payment-result";
//    }



    @GetMapping("/success")
    public ResponseEntity<Void> redirectSuccess(@RequestParam Map<String, String> allParams,
                                                HttpServletResponse response) throws IOException {
        String redirectUrl = WEB_URL + "/payment/success?" + buildQuery(allParams);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @GetMapping("/pending")
    public ResponseEntity<Void> redirectPending(@RequestParam Map<String, String> allParams,
                                                HttpServletResponse response) throws IOException {
        String redirectUrl = WEB_URL + "/payment/pending?" + buildQuery(allParams);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @GetMapping("/failure")
    public ResponseEntity<Void> redirectFailure(@RequestParam Map<String, String> allParams,
                                                HttpServletResponse response) throws IOException {
        String redirectUrl = WEB_URL + "/payment/failure?" + buildQuery(allParams);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    private String buildQuery(Map<String, String> params) {
        return params.entrySet().stream()
                .map(p -> p.getKey() + "=" + p.getValue())
                .collect(Collectors.joining("&"));
    }
}
