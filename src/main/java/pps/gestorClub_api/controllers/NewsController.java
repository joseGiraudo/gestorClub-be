package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pps.gestorClub_api.models.News;
import pps.gestorClub_api.services.NewsService;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    
    @Autowired
    private NewsService newsService;


    @GetMapping("")
    public ResponseEntity<List<News>> getAllNews() {
        List<News> news = newsService.getAll();
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        News news = newsService.getById(id);
        return ResponseEntity.ok(news);
    }

//    @PostMapping("")
//    public ResponseEntity<News> createNews(@Valid @RequestBody News news) {
//        News response = newsService.create(news);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable("id") Long newsId) {
        newsService.delete(newsId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> createNews(
            @PathVariable("id") Long id,
            @Valid @RequestBody News news) {
        News response = newsService.update(id, news);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNews(
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam String content,
            @RequestParam String date,
            @RequestParam MultipartFile image
    ) {
        // Guardás o procesás como quieras
        System.out.println(image.getOriginalFilename());

        newsService.create(title, summary, content, date, image);

        return ResponseEntity.ok("Noticia guardada");
    }
}
