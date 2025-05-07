package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.dtos.user.LoginRequest;
import pps.gestorClub_api.security.jwt.JwtUtil;
import pps.gestorClub_api.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())
                .map(user -> jwtUtil.generateToken(user.getEmail(), user.getRole()))
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
}
