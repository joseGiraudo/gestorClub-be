package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.user.PutUserDto;
import pps.gestorClub_api.dtos.user.UserDto;
import pps.gestorClub_api.models.User;

import java.util.List;

public interface UserService {

    User getById(Long id);

    List<User> getAll();

    User create(UserDto user);

    User update(Long id, PutUserDto user);

    void delete(Long id);
    
    User getByEmail(String email);

    Boolean getEmailExists(String email);
}
