package com.nael.catalog.DTO;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class BookDetailResponseDTO implements Serializable {

    private String bookId;

    private List<AuthorResponseDTO> authors;

    private List<CategoryListResponseDTO> categories;

    private PublisherResponseDTO publisher;

    private String bookTitle;

    private String bookDescription;

}
