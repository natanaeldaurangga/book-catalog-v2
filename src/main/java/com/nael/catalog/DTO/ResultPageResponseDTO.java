package com.nael.catalog.DTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class ResultPageResponseDTO<T> implements Serializable {

    private List<T> result;

    private Integer pages;// jumlah halaman yang dapat ditampilkan dalam result tersebut

    private Long elements;// jumlah dari records yang dapat ditampilkan dalam suatu query

}
