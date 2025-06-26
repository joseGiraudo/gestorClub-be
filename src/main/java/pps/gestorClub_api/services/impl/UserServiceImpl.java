package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.dtos.user.PutUserDto;
import pps.gestorClub_api.dtos.user.UserDto;
import pps.gestorClub_api.entities.UserEntity;
import pps.gestorClub_api.models.User;
import pps.gestorClub_api.repositories.UserRepository;
import pps.gestorClub_api.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public User getById(Long id) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con id: " + id));
        userEntity.setPassword(null);
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public List<User> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();

        if(userEntities.isEmpty())
            throw new EntityNotFoundException("No se encontraron usuarios");

        return userEntities.stream()
                .map(userEntity -> {
                    User user = modelMapper.map(userEntity, User.class);
                    user.setPassword(null);
                    return user;
                }).collect(Collectors.toList());
    }

    @Override
    public User create(UserDto user) {

        if(getEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        UserEntity userEntitySaved = userRepository.save(userEntity);

        return modelMapper.map(userEntitySaved, User.class);
    }

    @Override
    public User update(Long id, PutUserDto user) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con id: " + id));

        // Si el email cambió, validar que no esté usado por otro usuario
        if (!userEntity.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmailAndIdNot(user.getEmail(), id)) {
                throw new IllegalArgumentException("Email already in use");
            }
            userEntity.setEmail(user.getEmail());
        }

        userEntity.setName(user.getName());
        userEntity.setLastName(user.getLastName());
        userEntity.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userEntity.setPassword(user.getPassword());
        }

        UserEntity userEntityUpdated = userRepository.save(userEntity);

        return modelMapper.map(userEntityUpdated, User.class);
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con id: " + id));

        userEntity.setActive(false);
        userRepository.save(userEntity);
    }

    @Override
    public void activate(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con id: " + id));

        userEntity.setActive(true);
        userRepository.save(userEntity);
    }

    @Override
    public User getByEmail(String email) {

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con email: " + email));

        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public Boolean getEmailExists(String email) {
        return userRepository.existsByEmail(email);

    }
}
