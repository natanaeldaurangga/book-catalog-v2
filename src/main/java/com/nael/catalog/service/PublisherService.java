package com.nael.catalog.service;

import com.nael.catalog.DTO.PublisherCreateRequestDTO;
import com.nael.catalog.DTO.PublisherListResponseDTO;
import com.nael.catalog.DTO.PublisherResponseDTO;
import com.nael.catalog.DTO.PublisherUpdateRequestDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;
import com.nael.catalog.domain.Publisher;

import java.util.List;

public interface PublisherService {

    public void createPublisher(PublisherCreateRequestDTO dto);

    public void updatePublisher(String publisherId, PublisherUpdateRequestDTO dto);

    public void createPublishers(List<PublisherCreateRequestDTO> dtos);

    public Publisher findPublisher(String publisherId);

    public ResultPageResponseDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit,
            String sortBy, String direction, String publisherName);

    public PublisherResponseDTO constructDTO(Publisher publisher);

}
