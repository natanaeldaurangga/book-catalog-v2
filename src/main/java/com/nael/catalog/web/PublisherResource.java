package com.nael.catalog.web;

import com.nael.catalog.DTO.PublisherCreateRequestDTO;
import com.nael.catalog.DTO.PublisherListResponseDTO;
import com.nael.catalog.DTO.PublisherUpdateRequestDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;
import com.nael.catalog.anotation.LogThisMethod;
import com.nael.catalog.exception.BadRequestException;
import com.nael.catalog.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import java.net.URI;
import java.util.List;

@Validated // semua argumen bisa divalidasi
@AllArgsConstructor
@RestController
public class PublisherResource {

    private final PublisherService publisherService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/publisher")
    public ResponseEntity<Void> createNewPublisher(@RequestBody @Valid PublisherCreateRequestDTO dto) {
        publisherService.createPublisher(dto);
        return ResponseEntity.created(URI.create("/v1/publisher")).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/v1/publisher/{publisherId}")
    public ResponseEntity<Void> updatePublisher(
            @PathVariable @Size(max = 36, min = 36, message = "Publisher id is not UUID") String publisherId,
            @RequestBody @Valid PublisherUpdateRequestDTO dto) {
        publisherService.updatePublisher(publisherId, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/publishers")
    public ResponseEntity<Void> createNewPublishers(@RequestBody @Valid List<PublisherCreateRequestDTO> dtos) {
        publisherService.createPublishers(dtos);
        return ResponseEntity.created(URI.create("/publisher")).build();
    }

    // FIXME: kenapa si admin nggak terauthorisasi pada ADMIN, dan si epen tidak
    // bisa masuk padahal sudah terdaftar sebagai reader
    @PreAuthorize("isAuthenticated()")
    @LogThisMethod
    @GetMapping("/v1/publisher")
    public ResponseEntity<ResultPageResponseDTO<PublisherListResponseDTO>> findPublisherList(
            @RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
            @RequestParam(name = "publisherName", required = false) String publisherName) {
        if (pages < 0)
            throw new BadRequestException("invalid page");
        return ResponseEntity.ok()
                .body(publisherService.findPublisherList(pages, limit, sortBy, direction, publisherName));
    }
}
