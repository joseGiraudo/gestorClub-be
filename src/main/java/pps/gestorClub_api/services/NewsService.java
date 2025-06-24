package pps.gestorClub_api.services;

import org.springframework.web.multipart.MultipartFile;
import pps.gestorClub_api.models.News;

import java.util.List;

public interface NewsService {

    News getById(Long id);

    List<News> getAll();
    List<News> getAllActive();

    News create(String title, String summary, String content, String date, MultipartFile image);

    News update(Long id, String title, String summary, String content, String date, MultipartFile image);

    void delete(Long id);
    List<News> getLast3News();
}
