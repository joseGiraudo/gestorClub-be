package pps.gestorClub_api.services.impl;

import org.springframework.stereotype.Service;
import pps.gestorClub_api.dtos.user.LoginRequest;
import pps.gestorClub_api.services.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public Long login(LoginRequest login) {
        return 0L;
    }
}
