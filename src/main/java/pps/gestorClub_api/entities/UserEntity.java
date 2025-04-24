package pps.gestorClub_api.entities;

import jakarta.persistence.*;
import lombok.*;
import pps.gestorClub_api.enums.UserRole;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(nullable = false, name = "is_active")
    private boolean isActive = true;
}
