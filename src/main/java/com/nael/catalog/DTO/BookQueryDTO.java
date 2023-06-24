package com.nael.catalog.DTO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookQueryDTO implements Serializable {

    private Long id;

    private String bookId;

    private String booktTitle;

    private String publisherName;

    private String description;

}
