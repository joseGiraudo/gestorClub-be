package pps.gestorClub_api.services;

import org.springframework.web.multipart.MultipartFile;
import pps.gestorClub_api.models.News;

import java.util.List;

public interface NewsService {

    News getById(Long id);

    List<News> getAll();

    News create(String title, String summary, String content, String date, MultipartFile image);

    News update(Long id, News news);

    void delete(Long id);
}
