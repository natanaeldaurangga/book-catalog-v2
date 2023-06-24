package com.nael.catalog.DTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nael.catalog.anotation.LogThisArg;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@LogThisArg
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PublisherCreateRequestDTO implements Serializable {

    @NotBlank(message = "publisher.must.not.be.blank")
    private String publisherName;

    @NotBlank(message = "company_name.must.not.be.blank")
    private String companyName;

    private String address;

    // TODO: buat dto untuk Publisher beres tinggal buat service

}
