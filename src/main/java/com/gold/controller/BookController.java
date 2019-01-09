package com.gold.controller;
import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.Book;
import com.gold.model.Genre;
import com.gold.service.interfaces.BookService;
import com.gold.service.interfaces.GenreService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;
    private GenreService genreService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(value = "/books/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooksByGenre(@PathVariable(value = "genre") String genreName) {
        Genre genre = genreService.findByName(genreName);
        return bookService.findByGenre(genre);
    }
}
