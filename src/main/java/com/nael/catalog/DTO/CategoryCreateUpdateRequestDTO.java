package com.nael.catalog.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class CategoryCreateUpdateRequestDTO implements Serializable {

    @NotBlank
    private String code;

    @NotBlank
    private String name;


    private String description;



}
