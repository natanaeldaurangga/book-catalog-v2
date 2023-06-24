package com.nael.catalog.repository;

import java.util.List;
import java.util.Optional;

import com.nael.catalog.DTO.BookQueryDTO;
import com.nael.catalog.domain.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

        public Optional<Book> findById(Long id);

        // SQL : select n from book b WHERE b.secure_id = :id --> yang diquery adalah
        // tabel book
        // JPQL: select b from Book b where secureId=:id --> yang diquery adalah entity
        // dari Book
        public Optional<Book> findBySecureId(String secureId);

        // SELECT b FROM book b INNER JOIN publisher p ON p.id = b.publisher_id
        // WHERE p.name = :publisherName AND b.title= :bookTitle
        @Query("SELECT DISTINCT new com.nael.catalog.DTO.BookQueryDTO(b.id, b.secureId, b.title, p.name, b.description ) " +
                        "FROM Book b " +
                        "INNER JOIN Publisher p ON p.id = b.publisher.id " +
                        "JOIN b.authors ba " +
                        "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:publisherName,'%')) " +
                        "AND LOWER(b.title) LIKE LOWER(CONCAT('%',:bookTitle, '%')) " +
                        "AND LOWER(ba.name) LIKE LOWER(CONCAT('%',:authorName,'%'))")
        public Page<BookQueryDTO> findBookList(String bookTitle, String publisherName, String authorName,
                        Pageable pageable);

}
