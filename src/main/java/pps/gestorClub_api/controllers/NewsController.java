package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pps.gestorClub_api.models.News;
import pps.gestorClub_api.services.NewsService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    
    @Autowired
    private NewsService newsService;


    @GetMapping("")
    public ResponseEntity<List<News>> getAllActiveNews() {
        List<News> news = newsService.getAllActive();
        return ResponseEntity.ok(news);
    }

    @GetMapping("/last-news")
    public ResponseEntity<List<News>> getLastNews() {
        List<News> news = newsService.getLast3News();
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        News news = newsService.getById(id);
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable("id") Long newsId) {
        newsService.delete(newsId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity createNews(
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam String content,
            @RequestParam String date,
            @RequestParam MultipartFile image
    ) {
        // Guardás o procesás como quieras
        System.out.println(image.getOriginalFilename());

        newsService.create(title, summary, content, date, image);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Collections.singletonMap("message", "Noticia creada con éxito"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam String content,
            @RequestParam String date,
            @RequestParam(required = false) MultipartFile image
    ) {
        newsService.update(id, title, summary, content, date, image);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Collections.singletonMap("message", "Noticia actualizada con éxito"));
    }
}
