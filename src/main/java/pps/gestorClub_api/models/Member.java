package pps.gestorClub_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.MemberStatus;
import pps.gestorClub_api.enums.MemberType;
import pps.gestorClub_api.enums.UserRole;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {

    private Long id;

    private String name;

    private String lastName;

    private String dni;

    private String email;

    private String phone;

    private String address;

    private Date birthdate;

    private MemberType type;

    private MemberStatus status;

    // private List<Team> teams = new ArrayList<>();
}
