package pps.gestorClub_api.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.NewsStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class News {

    private Long id;

    @NotNull(message = "News title cannot be null")
    private String title;

    @NotNull(message = "News subtitle cannot be null")
    private String subtitle;

    @NotNull(message = "News body cannot be null")
    private String body;

    @NotNull(message = "News status cannot be null")
    private NewsStatus status;
}
