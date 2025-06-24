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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    public List<News> getAllActive() {

        return newsRepository.findAllByIsActiveTrueOrderByDateDesc().stream()
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
        LocalDate localFromDate = LocalDate.parse(date);
        entity.setDate(java.sql.Date.valueOf(localFromDate));
        entity.setImageUrl(imageUrl);
        entity.setStatus(NewsStatus.PUBLISHED);
        entity.setActive(true);
        NewsEntity saved = newsRepository.save(entity);

        System.out.println("URL CLOUDINARY: " + imageUrl);

        return modelMapper.map(saved, News.class);
    }

    @Override
    public News update(Long id, String title, String summary, String content, String date, MultipartFile image) {
        NewsEntity entity = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la noticia con id: " + id));

        entity.setTitle(title);
        entity.setSummary(summary);
        entity.setContent(content);
        entity.setDate(java.sql.Date.valueOf(LocalDate.parse(date)));

        // Solo actualiza imagen si se envió una nueva
        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(image);
            entity.setImageUrl(imageUrl);
            System.out.println("NUEVA URL CLOUDINARY: " + imageUrl);
        }

        NewsEntity updated = newsRepository.save(entity);
        return modelMapper.map(updated, News.class);
    }

    @Override
    public void delete(Long id) {

        NewsEntity newsEntity = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la noticia con id: " + id));

        newsEntity.setStatus(NewsStatus.ELIMINATED);
        newsEntity.setActive(false);
        newsRepository.save(newsEntity);
    }

    @Override
    public List<News> getLast3News() {
        return newsRepository.findTop3ByIsActiveTrueOrderByDateDesc().stream()
                .map(news -> modelMapper.map(news, News.class))
                .collect(Collectors.toList());
    }
}
