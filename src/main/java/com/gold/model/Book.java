package com.gold.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.gold.view.View;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="books")
@Getter @Setter @NoArgsConstructor

@NamedEntityGraphs({
        @NamedEntityGraph(name = "allJoinButContent", attributeNodes = {
                @NamedAttributeNode("genre"),
                @NamedAttributeNode("publisher")
        }),
        @NamedEntityGraph(name = "allJoins", attributeNodes = {
                @NamedAttributeNode("genre"),
                @NamedAttributeNode("publisher"),
                @NamedAttributeNode("content")
        })
})
public class Book {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @JsonView(View.Public.class)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "books")
    private Set<Author> authors;

    @ManyToOne()
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne()
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy")
    private Date publisherYear;

    @JsonView(View.Public.class)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contents_id")
    private BookContent bookContent;

    @Column(length = 1048576)
    private byte[] image;

    private int pageCount;

    private String isbn;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer rating;

    private Integer voteCount;

}

