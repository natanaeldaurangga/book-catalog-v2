package com.nael.catalog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nael.catalog.DTO.BookCreateRequestDTO;
import com.nael.catalog.DTO.BookDetailResponseDTO;
import com.nael.catalog.service.BookService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RequestMapping("/book") // dengan ini para method tidak perlu lagi
// menambahkan /book/* didepannya
@Controller
public class BookController {

    private final BookService bookService;

    @GetMapping("/list")
    public String findBookList(Model model) {
        List<BookDetailResponseDTO> books = bookService.findBookListDetail();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/new")
    public String loadBookForm(Model model) {
        // BookCreateDTO dto = new BookCreateDTO();
        // model.addAttribute("bookCreateDTO", dto);// si objek untu data transfernya dikirim
        // model.addAttribute("description", "pek siahh");
        return "book/book-new";
    }

    @PostMapping("/new") // disamain ke url formnya
    // dikasih anotasi @ModelAttribute dengan parameternya nama objek yang sama-
    // kayak nama objek yang diberikan ke form post yang mengarah ke fungsi ini
    // supaya si springnya tahu kalo objek yang dimaksud itu ini lho si
    // BookCreateDTO, variabel dto juga diberikan anotasi valid
    public String addNewBook(@ModelAttribute("bookCreateDTO") @Valid BookCreateRequestDTO dto,
            BindingResult bindingResult, //
            Errors errors, // untuk mengirimkan error
            Model model) {
        // FIXME: Kenapa si error nya kosong, kan harunsya error
        // log.info(String.format("hasil %b", errors.hasErrors()));
        if (errors.hasErrors()) {
            // log.info("Line 2");
            model.addAttribute("bookCreateDTO", dto);// karena ini, barusan kita ngasihnya model bukan dto
            return "book/book-new";
        }
        // log.info("Line 3");
        bookService.createNewBook(dto);
        return "redirect:/book/list";// di redirect(kembali) ke /list
    }

    // TODO: Lanjut ke rest api

}
