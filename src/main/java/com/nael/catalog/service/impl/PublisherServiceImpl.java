package com.nael.catalog.service.impl;

import com.nael.catalog.DTO.PublisherCreateRequestDTO;
import com.nael.catalog.DTO.PublisherListResponseDTO;
import com.nael.catalog.DTO.PublisherResponseDTO;
import com.nael.catalog.DTO.PublisherUpdateRequestDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;
import com.nael.catalog.domain.Publisher;
import com.nael.catalog.exception.BadRequestException;
import com.nael.catalog.repository.PublisherRepository;
import com.nael.catalog.service.PublisherService;
import com.nael.catalog.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public void createPublisher(PublisherCreateRequestDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getPublisherName());
        publisher.setCompanyName(dto.getCompanyName());
        publisher.setAddress(dto.getAddress());

        publisherRepository.save(publisher);
    }

    @Override
    public void updatePublisher(String publisherId, PublisherUpdateRequestDTO dto) {
        Publisher publisher = publisherRepository.findBySecureId(publisherId)
                .orElseThrow(() -> new BadRequestException("invalid.publisherId"));
        publisher.setName(dto.getPublisherName() == null || dto.getPublisherName().isBlank() ? publisher.getName()
                : dto.getPublisherName());
        publisher.setCompanyName(dto.getCompanyName() == null || dto.getCompanyName().isBlank() ? publisher.getName()
                : dto.getCompanyName());
        publisher.setAddress(dto.getAddress());
        // TODO: Lanjut untuk testing di presentation layer (http client)
        publisherRepository.save(publisher);
    }

    @Override
    public void createPublishers(List<PublisherCreateRequestDTO> dtos) {
        List<Publisher> publishers = dtos.stream().map((dto) -> {
            Publisher publisher = new Publisher();
            publisher.setName(dto.getPublisherName());
            publisher.setAddress(dto.getAddress());
            publisher.setCompanyName(dto.getCompanyName());
            return publisher;
        }).collect(Collectors.toList());

        publisherRepository.saveAll(publishers);
    }

    @Override
    public ResultPageResponseDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit,
            String sortBy, String direction, String publisherName) {
        publisherName = StringUtils.isEmpty(publisherName) ? "%" : publisherName + "%";
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Publisher> pageResult = publisherRepository.findByNameLikeIgnoreCase(publisherName, pageable);
        List<PublisherListResponseDTO> dtos = pageResult.stream().map((p) -> {
            PublisherListResponseDTO dto = new PublisherListResponseDTO();
            dto.setPublisherId(p.getSecureId());
            dto.setPublisherName(p.getName());
            dto.setCompanyName(p.getCompanyName());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

    @Override
    public Publisher findPublisher(String publisherId) {
        return publisherRepository.findBySecureId(publisherId).orElseThrow(
                () -> new BadRequestException("invalid.publisher_id"));
    } // TODO: Lanjut ke custom validation

    @Override
    public PublisherResponseDTO constructDTO(Publisher publisher) {
        PublisherResponseDTO dto = new PublisherResponseDTO();
        dto.setPublisherId(publisher.getSecureId());
        dto.setPublisherName(publisher.getName());
        return dto;
    }

}
