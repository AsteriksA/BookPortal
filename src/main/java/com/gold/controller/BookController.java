package com.gold.controller;
import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.BookContent;
import com.gold.dto.Book;
import com.gold.service.interfaces.BookContentService;
import com.gold.service.interfaces.BookService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/{genre}")
    public List<Book> getBooksByGenre(@PathVariable(value = "genre") String genreName) {
        return bookService.findByGenre(genreName);
    }

    @JsonView(View.Public.class)
    @GetMapping("/{id}")
    public BookContent getBookContent(@PathVariable(value = "id") Long id) {
        return bookContentService.findOne(id);
    }

    @PostMapping("/addBook")
    public void add(@RequestBody @Valid Book book) {
        bookService.add(book);
    }

    @PutMapping("/update/{bookId}")
    public void update(@PathVariable(name = "bookId") Long id, @RequestBody Book book) {
        bookService.update(id, book);
    }
}
