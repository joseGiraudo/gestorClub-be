package pps.gestorClub_api.services;

import pps.gestorClub_api.entities.UserEntity;
import pps.gestorClub_api.models.User;

import java.util.Optional;

public interface AuthService {

    Optional<UserEntity> authenticate(String email, String password);
}
