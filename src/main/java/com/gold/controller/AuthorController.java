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

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return authorService.findOne(id);
    }

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable Long id, Author author) {
        authorService.update(id, author);
    }
}
