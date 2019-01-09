package com.gold.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_contents")
@Getter @Setter @NoArgsConstructor
public class BookContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contents_id")
    private Long id;

    private byte[] content;

    @OneToOne(mappedBy = "content")
    private Book book;
}
