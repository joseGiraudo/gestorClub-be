package pps.gestorClub_api.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pps.gestorClub_api.enums.NewsStatus;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class News {

    private Long id;

    @NotNull(message = "News title cannot be null")
    private String title;

    @NotNull(message = "News summary cannot be null")
    private String summary;

    @NotNull(message = "News content cannot be null")
    private String content;

    @NotNull(message = "News image url cannot be null")
    private String imageUrl;

    @NotNull(message = "News image url cannot be null")
    private Date date;

    @NotNull(message = "News status cannot be null")
    private NewsStatus status;
}
