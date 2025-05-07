package pps.gestorClub_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.entities.UserEntity;
import pps.gestorClub_api.models.User;
import pps.gestorClub_api.repositories.UserRepository;
import pps.gestorClub_api.services.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserEntity> authenticate(String email, String password) {
        if (email.equals("admin@correo.com") && password.equals("admin123")) {

            return userRepository.findByEmail(email);
        } else if (email.equals("user@club.com") && password.equals("user123")) {
            return userRepository.findByEmail(email);
        }
        return Optional.empty();
    }
}
