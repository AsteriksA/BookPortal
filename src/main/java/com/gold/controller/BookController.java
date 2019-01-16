package com.gold.controller;
import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.BookContentDto;
import com.gold.dto.BookDto;
import com.gold.model.Book;
import com.gold.model.Genre;
import com.gold.service.interfaces.BookContentService;
import com.gold.service.interfaces.BookService;
import com.gold.service.interfaces.GenreService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private BookContentService bookContentService;

    @Autowired
    public BookController(BookService bookService, BookContentService bookContentService) {
        this.bookService = bookService;
        this.bookContentService = bookContentService;
    }

    @JsonView(View.Public.class)
    @GetMapping()
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/{genre}")
    public List<BookDto> getBooksByGenre(@PathVariable(value = "genre") String genreName) {
        return bookService.findByGenre(genreName);
    }

    @JsonView(View.Public.class)
    @GetMapping("/{id}")
    public BookContentDto getBookContent(@PathVariable(value = "id") Long id) {
        return bookContentService.findById(id);
    }
}
