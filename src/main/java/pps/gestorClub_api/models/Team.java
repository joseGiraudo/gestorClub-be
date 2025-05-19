package pps.gestorClub_api.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.enums.TeamSport;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Team {

    private Long id;

    private String name;

    private String description;

    private TeamSport sport;

    private List<Member> members = new ArrayList<>();
}
