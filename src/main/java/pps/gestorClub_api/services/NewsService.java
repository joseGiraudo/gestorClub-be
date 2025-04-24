package pps.gestorClub_api.services;

import pps.gestorClub_api.models.News;

import java.util.List;

public interface NewsService {

    News getById(Long id);

    List<News> getAll();

    News create(News news);

    News update(Long id, News news);

    void delete(Long id);
}
