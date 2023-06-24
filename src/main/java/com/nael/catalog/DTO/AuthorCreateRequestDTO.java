package com.nael.catalog.DTO;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nael.catalog.validator.annotation.ValidAuthorName;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AuthorCreateRequestDTO {

    @ValidAuthorName
    @NotBlank
    private String authorName;

    @NotNull
    private Long birthDate;

    private List<AddressCreateRequestDTO> addresses;

}
