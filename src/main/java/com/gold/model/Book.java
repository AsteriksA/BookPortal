package com.gold.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "books")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "withoutBookContent", attributeNodes = {
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
    private Long id;

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

