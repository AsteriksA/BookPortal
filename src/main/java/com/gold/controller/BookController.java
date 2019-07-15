package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gold.dto.Book;
import com.gold.dto.BookContent;
import com.gold.service.interfaces.BookContentService;
import com.gold.service.interfaces.BookService;
import com.gold.view.View;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.gold.config.WebSecurityConfig2.API_URL;

@RestController
@RequestMapping(API_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final BookService bookService;
    private final BookContentService bookContentService;
    private final ObjectMapper mapper;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void add(@RequestPart(name = "metaData") String book,
                    @RequestPart(name="image")  MultipartFile imageFile,
                    @RequestPart(name="content") MultipartFile contentFile) throws IOException {
        Book bookForm = mapper.readValue(book, Book.class);
        bookService.add(bookForm, imageFile, contentFile);
    }

    @GetMapping("/books")
    public List<Book> getBooksByParam(@RequestParam(required = false) String param) {
        return bookService.findBooksByParam(param);
    }

    @PutMapping("/books/{bookId}")
    public void update(@PathVariable(name = "bookId") Long id, @RequestBody Book book) {
        bookService.update(id, book);
    }

    //    TODO: return object or void
    @PutMapping("books/rating/{bookId}")
    public void changeRating(@PathVariable Long bookId,
                             @RequestParam(value = "rating") Integer rating) {
        bookService.changeRating(bookId, rating);
    }

    @GetMapping("/books/{bookId}")
    @JsonView(View.Public.class)
    public BookContent getBookContent(@PathVariable(value = "bookId") Long id) {
        return bookContentService.findOne(id);
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/genres/books")
    public List<Book> getBooksByGenre(@RequestParam(name="genre") String genreName) {
        return bookService.findByGenre(genreName);
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/authors/books")
    public List<Book> getBooksByAuthor(@RequestParam(name="author") String authorName) {
        return bookService.findByAuthor(authorName);
    }
}
