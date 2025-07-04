package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.dtos.user.LoginRequest;
import pps.gestorClub_api.models.User;
import pps.gestorClub_api.security.jwt.JwtUtil;
import pps.gestorClub_api.services.AuthService;
import pps.gestorClub_api.services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) throws InvalidCredentialsException {
        return authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

                    // Crear un mapa con token y datos del usuario
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", token);

                    // Agregar información del usuario
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("id", user.getId());
                    userData.put("email", user.getEmail());
                    userData.put("name", user.getName());
                    userData.put("role", user.getRole());

                    response.put("user", userData);

                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas"));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println(userDetails.getUsername());
        // Acá asumimos que el email es el username
        User user = userService.getByEmail(userDetails.getUsername());

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("email", user.getEmail());
        userData.put("name", user.getName());
        userData.put("role", user.getRole());

        return ResponseEntity.ok(userData);
    }

}
