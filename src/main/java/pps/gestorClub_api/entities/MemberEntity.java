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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MemberType type;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
