package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pps.gestorClub_api.entities.NewsEntity;
import pps.gestorClub_api.enums.NewsStatus;
import pps.gestorClub_api.models.News;
import pps.gestorClub_api.repositories.NewsRepository;
import pps.gestorClub_api.services.CloudinaryService;
import pps.gestorClub_api.services.NewsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public News getById(Long id) {
        NewsEntity newsEntity = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la noticia con id: " + id));

        return modelMapper.map(newsEntity, News.class);
    }

    @Override
    public List<News> getAll() {

        return newsRepository.findAll().stream()
                .map(news -> modelMapper.map(news, News.class))
                .collect(Collectors.toList());
    }

    @Override
    public News create(String title, String summary, String content, String date, MultipartFile image) {

        String imageUrl = cloudinaryService.uploadImage(image);

        NewsEntity entity = new NewsEntity();
        entity.setTitle(title);
        entity.setSummary(summary);
        entity.setContent(content);
        // entity.setCreatedDate(LocalDateTime.parse(date));
        entity.setImageUrl(imageUrl);
        entity.setStatus(NewsStatus.CREATED);
        NewsEntity saved = newsRepository.save(entity);

        System.out.println("URL CLOUDINARY: " + imageUrl);

        return modelMapper.map(saved, News.class);
    }

    @Override
    public News update(Long id, News news) {

        NewsEntity newsEntity = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la noticia con id: " + id));

        // Actualizamos los campos editables
        newsEntity.setTitle(news.getTitle());
        newsEntity.setSummary(news.getSummary());
        newsEntity.setContent(news.getContent());
        // Podés también actualizar el status si corresponde
        // entity.setStatus(news.getStatus());

        NewsEntity updated = newsRepository.save(newsEntity);
        return modelMapper.map(updated, News.class);
    }

    @Override
    public void delete(Long id) {

        NewsEntity newsEntity = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la noticia con id: " + id));

        newsEntity.setStatus(NewsStatus.ELIMINATED);
        newsEntity.setIsActive(false);
        newsRepository.save(newsEntity);
    }
}
