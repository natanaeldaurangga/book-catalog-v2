package com.nael.catalog.DTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.sql.rowset.serial.SerialArray;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PublisherUpdateRequestDTO implements Serializable {

    private String publisherName;

    private String companyName;

    private String address;

}
