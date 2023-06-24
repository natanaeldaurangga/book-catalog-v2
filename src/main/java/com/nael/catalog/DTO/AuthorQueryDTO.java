package com.nael.catalog.DTO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorQueryDTO implements Serializable {
    
    private Long bookId;

    private String authorName;

}
