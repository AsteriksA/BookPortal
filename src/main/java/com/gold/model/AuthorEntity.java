package com.gold.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authors",
        uniqueConstraints = { @UniqueConstraint( columnNames = { "firstName", "lastName" } ) } )
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "authors_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    private Set<BookEntity> books;
}
