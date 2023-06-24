package com.nael.catalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nael.catalog.DTO.AuthorQueryDTO;
import com.nael.catalog.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // spring akan langsung membuatkan implementasi dari AuthorRepository karena
    // AuthorRepository sudah mengexttend JpaRepository
    // jadi ototmatis dibuatin implementasinya nggak kayak bookrespository yang
    // harus dibuatin dulu bookRepositoryImpl

    // sql -> select * from Author a where a.id= :authorId
    public Optional<Author> findById(Long id);

    public List<Author> findBySecureIdIn(List<String> authorSecureIdList);

    public Optional<Author> findBySecureId(String secureId);

    public Optional<Author> findByIdAndDeletedFalse(Long id);

    // sql -> select a from author a where a.name LIKE :authorName
    public List<Author> findByName(String authorName);

    @Query("SELECT new com.nael.catalog.DTO.AuthorQueryDTO(b.id, ba.name) "
            + "FROM Book b "
            + "JOIN b.authors ba "
            + "WHERE b.id IN :bookIdList")
    public List<AuthorQueryDTO> findAuthorByBookIdList(List<Long> bookIdList);

}
