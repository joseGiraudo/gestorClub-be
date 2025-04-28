package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pps.gestorClub_api.dtos.user.LoginRequest;
import pps.gestorClub_api.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResponseEntity<Long> login(@Valid @RequestBody LoginRequest login) {

        Long response = loginService.login(login);
        return ResponseEntity.ok(response);
    }
}
