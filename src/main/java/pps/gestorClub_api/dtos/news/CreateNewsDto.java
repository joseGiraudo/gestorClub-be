package pps.gestorClub_api.dtos.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsDto {

    private String title;

    private String summary;

    private String content;

    private String date;
}
