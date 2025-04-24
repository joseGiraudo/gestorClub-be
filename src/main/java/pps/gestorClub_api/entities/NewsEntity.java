package pps.gestorClub_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.NewsStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "news")
@EqualsAndHashCode(callSuper = true)
public class NewsEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "body")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NewsStatus status;

}
