package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.user.LoginRequest;

public interface LoginService {

    Long login(LoginRequest login);
}
