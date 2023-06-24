package com.nael.catalog.DTO;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class BookUpdateRequestDTO {

    @NotBlank
    private String bookTitle;

    @JsonProperty("synopsis")
    @NotBlank
    private String bookDescription;

}
