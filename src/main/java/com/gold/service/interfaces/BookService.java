package com.gold.service.interfaces;

import com.gold.dto.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService extends BaseService<Book, Long> {

    List<Book> findByName(String name);
    List<Book> findByGenre(String genreName);
    List<Book> findByPublisher(String publisherName);
    List<Book> findByAuthor(String authorName);
    Book update(Long id, Book book);
    void changeRating(Long id, Integer rating);
    List<Book> findBooksByParam(String param);
    Book add(Book book, MultipartFile imageFile, MultipartFile contentFile) throws IOException;
    void add(String book, MultipartFile imageFile, MultipartFile contentFile) throws IOException;
}
