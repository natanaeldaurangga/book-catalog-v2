package com.nael.catalog.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "book_detail")
public class BookDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "settings")
    private String settings;

    @Column(name = "thumbnail")
    private String thumbnail;

    // cara melakukan relasi antar objek di spring hibernate
    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


}
