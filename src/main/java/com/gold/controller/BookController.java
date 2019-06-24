package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gold.dto.BookContent;
import com.gold.dto.Book;
import com.gold.form.BookForm;
import com.gold.service.interfaces.BookContentService;
import com.gold.service.interfaces.BookService;
import com.gold.view.View;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final BookService bookService;
    private final BookContentService bookContentService;
    private final ObjectMapper mapper;

    @GetMapping()
    @JsonView(View.Public.class)
//    @PreAuthorize("hasPermission('department','member')")
    @PreAuthorize("@restAccessService.hasPermitState('ACTIVATED')")
//    @PreAuthorize("@restAccessService.hasPermitState()")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void add(@RequestPart(name = "metaData") String book,
                    @RequestPart(name="image")  MultipartFile imageFile,
                    @RequestPart(name="content") MultipartFile contentFile) throws IOException {
        BookForm bookForm = mapper.readValue(book, BookForm.class);
        bookService.add(bookForm, imageFile, contentFile);
    }

    @PutMapping("/{bookId}")
    public void update(@PathVariable(name = "bookId") Long id, @RequestBody Book book) {
        bookService.update(id, book);
    }

    @GetMapping("/{bookId}")
    @JsonView(View.Public.class)
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

//    TODO: return object or void
    @PutMapping("/rating/{bookId}")
    public void rating(@PathVariable Long bookId,
                                         @RequestParam(value = "rating") Integer rating) {
        bookService.changeRating(bookId, rating);
    }

    @GetMapping("/search")
    public List<Book> getBooksBySearch(@RequestParam String param) {
        return bookService.findBySearch(param);
    }
}
