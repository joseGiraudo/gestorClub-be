package pps.gestorClub_api.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.UserRole;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutUserDto {

    @NotBlank
    private String name;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank
    private String email;

    private String password; // puede venir null o vacío

    @NotNull(message = "user role cannot be null")
    private UserRole role;
}
