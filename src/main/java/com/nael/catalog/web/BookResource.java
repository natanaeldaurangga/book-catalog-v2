package com.nael.catalog.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nael.catalog.DTO.BookCreateRequestDTO;
import com.nael.catalog.DTO.BookDetailResponseDTO;
import com.nael.catalog.DTO.BookListResponseDTO;
import com.nael.catalog.DTO.BookUpdateRequestDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;
import com.nael.catalog.service.BookService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // dengan menggunakan anotasi ini kita tidak perlu lagi mengimpor Logger
@AllArgsConstructor // dengan menggunakan AllArgsContructor kita tidak perlu lagi membuat
                    // constructor
@RestController
public class BookResource {

    private final BookService bookService;

    // TODO: lanjut bagian AOP
    @GetMapping("/v1/book/{bookId}")
    public ResponseEntity<BookDetailResponseDTO> findBookDetail(@PathVariable("bookId") String id) {
        StopWatch stopWatch = new StopWatch();
        log.info("start findBookDetail: " + id);
        stopWatch.start();
        BookDetailResponseDTO result = bookService.findBookDetailById(id);
        log.info("finish findBookDetail. execution time = {}", stopWatch.getTotalTimeMillis());
        return ResponseEntity.ok(result);
    }

    // save book
    @PostMapping("/v1/book")
    public ResponseEntity<Void> createNewBook(@RequestBody BookCreateRequestDTO dto) {
        bookService.createNewBook(dto);
        return ResponseEntity.created(URI.create("/book")).build();
    }

    // get book list -> natanael daurang
    // 1. judul buku
    // 2. nama penerbit // table publisher
    // 3. nama penulis // table author
    // maka kita akan melakukan join antara tabel book dan tabel publisher
    @GetMapping("/v1/book")
    public ResponseEntity<List<BookDetailResponseDTO>> findBookList() {
        return ResponseEntity.ok().body(bookService.findBookListDetail());
    }

    @GetMapping("/v2/book")
    public ResponseEntity<ResultPageResponseDTO<BookListResponseDTO>> findBookList(
            @RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = true, defaultValue = "title") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "publisherName", required = false, defaultValue = "") String publisherName,
            @RequestParam(name = "authorName", required = false, defaultValue = "") String authorName,
            @RequestParam(name = "bookTitle", required = false, defaultValue = "") String bookTitle) {
        return ResponseEntity.ok()
                .body(bookService.findBookList(page, limit, sortBy, direction, publisherName, bookTitle, authorName));
    }

    @PutMapping("/v1/book/{bookId}")
    public ResponseEntity<Void> updateBook(@PathVariable("bookId") String bookId,
            @RequestBody BookUpdateRequestDTO dto) {
        bookService.updateBook(bookId, dto);
        return ResponseEntity.ok().build();
    }

    // DELETE /book
    @DeleteMapping("/v1/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
