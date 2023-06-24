package com.nael.catalog.DTO;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AuthorUpdateRequestDTO {

    private String authorName;

    private Long birthDate;

    private List<AddressUpdateRequestDTO> addresses; 

}
