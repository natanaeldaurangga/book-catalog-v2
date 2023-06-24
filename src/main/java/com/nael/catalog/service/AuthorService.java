package com.nael.catalog.service;

import java.util.List;
import java.util.Map;

import com.nael.catalog.DTO.AuthorCreateRequestDTO;
import com.nael.catalog.DTO.AuthorResponseDTO;
import com.nael.catalog.DTO.AuthorUpdateRequestDTO;
import com.nael.catalog.domain.Author;

public interface AuthorService {

    public AuthorResponseDTO findAuthorBySecureId(String secureId);

    public List<AuthorResponseDTO> allAuthor();

    public void createNewAuthor(List<AuthorCreateRequestDTO> dtos);

    public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto);

    public void deleteAuthor(String authorId);

    public List<Author> findAuthors(List<String> authorIdList);
    
    public List<AuthorResponseDTO> constructDTO(List<Author> authors);

    public Map<Long, List<String>> findAuthorMap(List<Long> bookIdList);

}
