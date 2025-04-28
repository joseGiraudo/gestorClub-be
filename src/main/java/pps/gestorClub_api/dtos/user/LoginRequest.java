package pps.gestorClub_api.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @JsonProperty("email")
    private String email;


    @JsonProperty("password")
    private String password;
}
