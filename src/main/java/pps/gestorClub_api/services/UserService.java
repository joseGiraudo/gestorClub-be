package pps.gestorClub_api.services;

import pps.gestorClub_api.models.User;

import java.util.List;

public interface UserService {

    User getById(Long id);

    List<User> getAll();

    User create(User user);

    User update(Long id, User user);

    void delete(Long id);

    Boolean getEmailExists(String email);
}
