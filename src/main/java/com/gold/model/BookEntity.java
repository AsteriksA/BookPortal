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
public class BookEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "books")
    private Set<AuthorEntity> authors;

    @ManyToOne()
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    @ManyToOne()
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy")
    private Date publisherYear;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private BookContentEntity content;

    @Column(length = 1048576)
    private byte[] image;

    private int pageCount;

    private String isbn;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double rating;

    private Integer voteCount;
}

