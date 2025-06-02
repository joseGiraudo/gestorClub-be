package pps.gestorClub_api.dtos.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.MemberStatus;
import pps.gestorClub_api.enums.MemberType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String name;
    private String lastName;
    private String dni;
    private String email;
    private Date birthdate;
    private String phone;
    private String address;
    private MemberType type;
    private MemberStatus status;
    private Boolean isActive;

    // Campos de BaseEntity
    private Date createdAt;
    private Date updatedAt;
}
