package pps.gestorClub_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "fees")
public class FeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month_period", nullable = false)
    private Integer month;

    @Column(name = "year_period", nullable = false)
    private Integer year;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}
