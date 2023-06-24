package com.nael.catalog.web;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nael.catalog.DTO.AuthorCreateRequestDTO;
import com.nael.catalog.DTO.AuthorResponseDTO;
import com.nael.catalog.DTO.AuthorUpdateRequestDTO;
import com.nael.catalog.service.AuthorService;

import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
@RestController
public class AuthorResource {

    private final AuthorService authorService;

    @RequestMapping(value = "/v1/author", method = RequestMethod.GET)
    public ResponseEntity<List<AuthorResponseDTO>> findAll() {
        return ResponseEntity.ok().body(authorService.allAuthor());
    }

    @GetMapping("/v1/author/{secureId}/detail")
    public ResponseEntity<AuthorResponseDTO> findAuthorById(@PathVariable String secureId) {
        return ResponseEntity.ok().body(authorService.findAuthorBySecureId(secureId));
    }

    @PostMapping("/v1/author")
    public ResponseEntity<Void> createNewBook(@RequestBody @Valid List<AuthorCreateRequestDTO> dtos) {
        authorService.createNewAuthor(dtos);
        return ResponseEntity.created(URI.create("/author")).build();
    }

    @PutMapping("/v1/author/{authorId}")
    public ResponseEntity<Void> updateAuthor(@PathVariable String authorId, @RequestBody AuthorUpdateRequestDTO dto) {
        authorService.updateAuthor(authorId, dto);
        return ResponseEntity.ok().build();
    }

    // TODO: Lanjut untuk update database part
    @DeleteMapping("/v1/author/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok().build();
    }

    // TODO: Lanjut untuk softDelete, artinya kita harus menambahkan kolom baru di
    // authro yaitu kolom deleted_at

}
