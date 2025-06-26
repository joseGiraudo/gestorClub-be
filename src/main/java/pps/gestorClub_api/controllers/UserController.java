package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.dtos.user.PutUserDto;
import pps.gestorClub_api.dtos.user.UserDto;
import pps.gestorClub_api.entities.UserEntity;
import pps.gestorClub_api.models.User;
import pps.gestorClub_api.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto user) {
        User response = userService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> activateUser(@PathVariable("id") Long userId) {
        userService.activate(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody PutUserDto user) {
        User response = userService.update(id, user);

        return ResponseEntity.ok(response);
    }
}
