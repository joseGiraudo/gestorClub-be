package pps.gestorClub_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.MemberType;
import pps.gestorClub_api.enums.UserRole;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {

    private Long id;

    @NotNull(message = "Member name cannot be null")
    private String name;

    @NotNull(message = "Member last name cannot be null")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Member email cannot be null")
    private String email;

    @NotNull(message = "Member birthdate cannot be null")
    private Date birthdate;

    @NotNull(message = "Member type cannot be null")
    private MemberType role;

    @JsonProperty("is_active")
    private boolean isActive;
}
