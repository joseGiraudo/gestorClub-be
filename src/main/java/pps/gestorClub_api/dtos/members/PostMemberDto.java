package pps.gestorClub_api.dtos.members;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.MemberType;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMemberDto {

    @NotNull(message = "Member name cannot be null")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Member last name cannot be null")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Member dni cannot be null")
    @JsonProperty("dni")
    private String dni;

    @NotNull(message = "Member email cannot be null")
    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @NotNull(message = "Member birthdate cannot be null")
    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @NotNull(message = "Member type cannot be null")
    @JsonProperty("type")
    private MemberType type;
}
