package com.gold.controller;

import com.gold.dto.Publisher;
import com.gold.service.interfaces.PublisherService;
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
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public List<Publisher> getGenres() {
        return publisherService.findAll();
    }

    @GetMapping("/books/{bookId}/publishers")
    public Publisher getGenre(@PathVariable Long bookId) {
        return publisherService.findByBookId(bookId);
    }

    @PutMapping("/{publisherId}")
    public void update(@PathVariable Long publisherId, Publisher publisher) {
        publisherService.update(publisherId, publisher);
    }
}
