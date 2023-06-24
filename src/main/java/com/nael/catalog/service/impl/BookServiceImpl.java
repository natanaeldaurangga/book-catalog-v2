package com.nael.catalog.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nael.catalog.exception.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nael.catalog.DTO.BookCreateRequestDTO;
import com.nael.catalog.DTO.BookDetailResponseDTO;
import com.nael.catalog.DTO.BookListResponseDTO;
import com.nael.catalog.DTO.BookQueryDTO;
import com.nael.catalog.DTO.BookUpdateRequestDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;
import com.nael.catalog.domain.Author;
import com.nael.catalog.domain.Book;
import com.nael.catalog.domain.Category;
import com.nael.catalog.domain.Publisher;
import com.nael.catalog.repository.BookRepository;
import com.nael.catalog.service.AuthorService;
import com.nael.catalog.service.BookService;
import com.nael.catalog.service.CategoryService;
import com.nael.catalog.service.PublisherService;
import com.nael.catalog.util.PaginationUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// karena book service impl ini beradap pada layer service
// kita telah mendefinisikan BookServiceImpl sebagai bean yang nantinya dikelola oleh spring container
// pada javabase kita menggunakan nama fungsinya sedangkan pada annotation kita menggunakan nama kelasnya langsung

@Slf4j
@AllArgsConstructor
@Service("bookService") // memberikan nama bean pada anotasi service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    private final PublisherService publisherService;

    public BookDetailResponseDTO findBookDetailById(String bookId) {
        Book book = bookRepository.findBySecureId(bookId).orElseThrow(() -> new BadRequestException("book_id invalid"));
        BookDetailResponseDTO dto = new BookDetailResponseDTO();
        dto.setBookId(book.getSecureId());
        dto.setCategories(categoryService.contructDTO(book.getCategories()));
        dto.setAuthors(authorService.constructDTO(book.getAuthors()));
        dto.setPublisher(publisherService.constructDTO(book.getPublisher()));
        dto.setBookTitle(book.getTitle());
        dto.setBookDescription(book.getDescription());
        return dto;
    }

    @Override
    public List<BookDetailResponseDTO> findBookListDetail() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map((b) -> {
            BookDetailResponseDTO dto = new BookDetailResponseDTO();
            dto.setBookId(b.getSecureId());
            dto.setBookTitle(b.getTitle());
            dto.setBookDescription(b.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createNewBook(BookCreateRequestDTO dto) {
        List<Author> authors = authorService.findAuthors(dto.getAuthorIdList());
        List<Category> categories = categoryService.findCategories(dto.getCategoryList());
        Publisher publisher = publisherService.findPublisher(dto.getPublisherId());
        Book book = new Book();
        book.setAuthors(authors);
        book.setCategories(categories);
        book.setPublisher(publisher);
        book.setTitle(dto.getBookTitle());
        book.setDescription(dto.getBookDescription());
        bookRepository.save(book);
    }

    @Override
    public void updateBook(String bookId, BookUpdateRequestDTO dto) {
        // get book from respository
        Book book = bookRepository.findBySecureId(bookId).orElseThrow(() -> new BadRequestException("book_id invalid"));
        // update
        book.setTitle(dto.getBookTitle());
        book.setDescription(dto.getBookDescription());
        // save
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public ResultPageResponseDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy,
            String direction, String publisherName, String bookTitle, String authorName) {
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<BookQueryDTO> pageResult = bookRepository.findBookList(bookTitle, publisherName, authorName, pageable);
        List<Long> idList = pageResult.stream().map(b -> b.getId()).collect(Collectors.toList());
        Map<Long, List<String>> categoryMap = categoryService.findCategoriesMap(idList);
        Map<Long, List<String>> authorMap = authorService.findAuthorMap(idList);
        List<BookListResponseDTO> dtos = pageResult.stream().map(b -> {
            BookListResponseDTO dto = new BookListResponseDTO();
            dto.setAuthorNames(authorMap.get(b.getId()));
            dto.setCategoryCodes(categoryMap.get(b.getId()));
            dto.setTitle(b.getBooktTitle());
            dto.setPublisherName(b.getPublisherName());
            dto.setDescription(b.getDescription());
            dto.setId(b.getBookId());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

}
