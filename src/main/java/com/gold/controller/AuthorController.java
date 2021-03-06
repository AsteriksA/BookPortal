package com.gold.controller;

import com.gold.dto.Author;
import com.gold.service.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

import static com.gold.config.WebSecurityConfig.API_URL;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(API_URL)
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/books/{bookId}/authors")
    public Collection<Author> getAuthor(@PathVariable Long bookId) {
        return authorService.findByBookId(bookId);
    }

    @PutMapping("/{authorId}")
    public void update(@PathVariable Long authorId, Author author) {
        authorService.update(authorId, author);
    }
}
