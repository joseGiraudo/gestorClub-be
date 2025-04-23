package pps.gestorClub_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import pps.gestorClub_api.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    @NotNull(message = "User name cannot be null")
    private String name;

    @NotNull(message = "User last name cannot be null")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "User email cannot be null")
    private String email;

    @NotNull(message = "User password cannot be null")
    private String password;

    @NotNull(message = "user role cannot be null")
    private UserRole role;

    @JsonProperty("is_active")
    private boolean isActive;
}
