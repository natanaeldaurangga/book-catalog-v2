package com.nael.catalog.DTO;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class BookListResponseDTO implements Serializable {

    private String id;

    private String title;

    private String description;

    private String publisherName;

    private List<String> categoryCodes;

    private List<String> authorNames;

}
