package pps.gestorClub_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.MemberType;
import pps.gestorClub_api.enums.UserRole;

import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "members")
@EqualsAndHashCode(callSuper = true)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private Date birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MemberType type;

    @Column(nullable = false, name = "is_active")
    private boolean isActive = true;
}
