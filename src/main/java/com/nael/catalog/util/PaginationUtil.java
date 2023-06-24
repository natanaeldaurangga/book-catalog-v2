package com.nael.catalog.util;

import com.nael.catalog.DTO.ResultPageResponseDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtil {

    public static <T>ResultPageResponseDTO<T> createResultPageDTO(List<T> dtos, Long totalElement, Integer pages){
        ResultPageResponseDTO<T> result = new ResultPageResponseDTO<T>();
        result.setPages(pages);
        result.setElements(totalElement);
        result.setResult(dtos);
        return result;
    }

    public static Sort.Direction getSortBy(String sortBy){
        if(sortBy.equalsIgnoreCase("asc")){
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }

}
