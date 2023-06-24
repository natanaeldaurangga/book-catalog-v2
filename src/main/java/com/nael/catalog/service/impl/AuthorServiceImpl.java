package com.nael.catalog.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nael.catalog.DTO.AuthorCreateRequestDTO;
import com.nael.catalog.DTO.AuthorQueryDTO;
import com.nael.catalog.DTO.AuthorResponseDTO;
import com.nael.catalog.domain.Address;
import com.nael.catalog.domain.Author;
import com.nael.catalog.exception.BadRequestException;
import com.nael.catalog.exception.ResourceNotFoundException;
import com.nael.catalog.repository.AuthorRepository;
import com.nael.catalog.service.AuthorService;
import com.nael.catalog.DTO.AuthorUpdateRequestDTO;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponseDTO findAuthorBySecureId(String secureId) {
        // 1. fetch data from database
        Author author = authorRepository.findBySecureId(secureId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid.authorId"));
        // 2. parsing ke dalam response dto: ubah ke AuthorResponseDTO
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setAuthorName(author.getName());
        dto.setBirthDate(author.getBirthDate().toEpochDay());
        return dto;
    }

    @Override
    public List<AuthorResponseDTO> allAuthor() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(author -> {
            AuthorResponseDTO dto = new AuthorResponseDTO();
            dto.setAuthorName(author.getName());
            dto.setBirthDate(author.getBirthDate().toEpochDay());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createNewAuthor(List<AuthorCreateRequestDTO> dtos) {
        List<Author> authors = dtos.stream().map((dto) -> {
            Author author = new Author();
            author.setName(dto.getAuthorName());
            author.setBirthDate(LocalDate.ofEpochDay(dto.getBirthDate()));
            author.setDeleted(false);
            List<Address> addresses = dto.getAddresses().stream().map(a -> {
                Address address = new Address();
                address.setCityName(a.getCityName());
                address.setStreetName(a.getStreetName());
                address.setZipCode(a.getZipCode());
                address.setAuthor(author); // memberitahu address jika parentnya adalah author yang di atas
                return address;
            }).collect(Collectors.toList());
            author.setAddresses(addresses);
            return author;
        }).collect(Collectors.toList());
        authorRepository.saveAll(authors);
    }

    @Override
    public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto) {
        // TODO Auto-generated method stub
        // 1. fetch data from database
        Author author = authorRepository.findBySecureId(authorId)
                .orElseThrow(() -> new BadRequestException("invalid.authorId"));
        // 2. parsing ke dalam response dto: ubah ke AuthorResponseDTO
        Map<Long, Address> addressMap = author.getAddresses().stream().map(a -> a)
                .collect(Collectors.toMap(Address::getId, Function.identity()));
        List<Address> addresses = dto.getAddresses().stream().map(a -> {
            Address address = addressMap.get(a.getAddressId());
            address.setCityName(a.getCityName());
            address.setStreetName(a.getStreetName());
            address.setZipCode(a.getZipCode());
            address.setAuthor(author);
            return address;// TODO: lanjut untuk cascade tipe remove
        }).collect(Collectors.toList());
        author.setAddresses(addresses);
        author.setName(dto.getAuthorName() == null ? author.getName() : dto.getAuthorName());
        author.setBirthDate(
                dto.getBirthDate() == null ? author.getBirthDate() : LocalDate.ofEpochDay(dto.getBirthDate()));

        authorRepository.save(author);// method save dapat berfungsi 2, yaitu mengcreate data baru, atau mengupdate
                                      // data yang lama
    }

    @Override
    public void deleteAuthor(String authorId) {
        // TODO Auto-generated method stub
        // 1. select data
        // 2. delete
        // or
        // 1. delete (hard delete)
        // authorRepository.deleteBySecureId(authorId); // karena pada author sudah
        // diberikan anotasi SQLDelete jadi kita bisa
        // mengganti yang tadinya hard delete, jadinya otomatis soft delete
        Author author = authorRepository.findBySecureId(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid.authorId"));
        authorRepository.delete(author);
        // softdelete
        // 1. select data deleted=false
        // 2. update deleted
        // Author author = authorRepository.findByIdAndDeletedFalse(authorId)
        // .orElseThrow(() -> new BadRequestException("invalid.authorId"));

        // author.setDeleted(true);
        // authorRepository.save(author);
    }

    @Override
    public List<Author> findAuthors(List<String> authorIdList) {
        List<Author> authors = authorRepository.findBySecureIdIn(authorIdList);
        if (authors.isEmpty())
            throw new BadRequestException("author can't empty");
        return authors;
    }

    @Override
    public List<AuthorResponseDTO> constructDTO(List<Author> authors) {
        return authors.stream().map((a) -> {
            AuthorResponseDTO dto = new AuthorResponseDTO();
            dto.setAuthorName(a.getName());
            dto.setBirthDate(a.getBirthDate().toEpochDay());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<String>> findAuthorMap(List<Long> bookIdList) {
        List<AuthorQueryDTO> queryList = authorRepository.findAuthorByBookIdList(bookIdList);
        Map<Long, List<String>> authorMap = new HashMap<>();
        List<String> authorList = null;
        for (AuthorQueryDTO q : queryList) {
            if (!authorMap.containsKey(q.getBookId())) {
                authorList = new ArrayList<>();
            } else {
                authorList = authorMap.get(q.getBookId());
            }
            authorList.add(q.getAuthorName());
            authorMap.put(q.getBookId(), authorList);
        }
        return authorMap;
    }
}
