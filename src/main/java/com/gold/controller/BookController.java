package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.BookContent;
import com.gold.dto.Book;
import com.gold.service.interfaces.BookContentService;
import com.gold.service.interfaces.BookService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
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
//    @PreAuthorize("hasAuthority('USER')")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping("/addBook")
    public void add(@RequestBody @Valid Book book) {
        bookService.add(book);
    }

    @PutMapping("/update/{bookId}")
    public void update(@PathVariable(name = "bookId") Long id, @RequestBody Book book) {
        bookService.update(id, book);
    }

    @JsonView(View.Public.class)
    @GetMapping("/id/{bookId}")
    public BookContent getBookContent(@PathVariable(value = "bookId") Long id) {
        return bookContentService.findOne(id);
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable(value = "genre") String genreName) {
        return bookService.findByGenre(genreName);
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable(value = "author") String authorName) {
        return bookService.findByAuthor(authorName);
    }

//    TODO: do it
    @PutMapping("/id/{id}")
    public ResponseEntity<Object> rating(@PathVariable Long id,
                                         @RequestParam(value = "rating") Integer rating) {
        bookService.changeRating(id, rating);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Book> getBooksBuSearch(@RequestParam String param) {
        return bookService.findBySearch(param);
    }
}
