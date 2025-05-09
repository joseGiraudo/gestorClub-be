package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
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
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró usuario con email: " + email));

        if(userEntity.getPassword().equals(password)) {
            return Optional.of(userEntity);
        }
        return Optional.empty();
    }
}
