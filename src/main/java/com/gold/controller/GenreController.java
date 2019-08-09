package com.gold.controller;

import com.gold.dto.Genre;
import com.gold.service.interfaces.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.gold.config.WebSecurityConfig.API_URL;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(API_URL)
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return genreService.findAll();
    }

    @GetMapping("/books/{bookId}/genres")
    public Genre getGenre(@PathVariable Long bookId) {
        return genreService.findByBookId(bookId);
    }

    @PutMapping("/{genreId}")
    public void update(@PathVariable Long genreId, Genre genre) {
        genreService.update(genreId, genre);
    }
}
