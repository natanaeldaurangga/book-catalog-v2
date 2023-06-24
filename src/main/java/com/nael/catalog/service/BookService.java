package com.nael.catalog.service;

import java.util.List;

import com.nael.catalog.DTO.BookCreateRequestDTO;
import com.nael.catalog.DTO.BookDetailResponseDTO;
import com.nael.catalog.DTO.BookListResponseDTO;
import com.nael.catalog.DTO.BookUpdateRequestDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;

public interface BookService {

    public BookDetailResponseDTO findBookDetailById(String bookId);

    public List<BookDetailResponseDTO> findBookListDetail();

    public void createNewBook(BookCreateRequestDTO dto);

    public void updateBook(String bookId, BookUpdateRequestDTO dto);

    public void deleteBook(Long bookId);

    public ResultPageResponseDTO<BookListResponseDTO> findBookList(
            Integer page, Integer limit, String sortBy, String direction,
            String publisherName,
            String bookTitle,
            String authorName);

}
