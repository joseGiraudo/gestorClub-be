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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
