package com.gold.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
@Getter @Setter @NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    private String firstName;

    private String secondName;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "authors_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    private Set<Book> books;

    public Author(String fName, String secondName) {
        this.firstName = fName;
        this.secondName = secondName;
    }
}
