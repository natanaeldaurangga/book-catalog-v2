package com.nael.catalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // memberitahu spring container kalau ini bean, juga sekaligus memberitahukan
            // kepada dispatcher servlet kalau ini adalah kelas controller
public class HomeController {

    // @RequestMapping(value = "/home", method = RequestMethod.GET) // url dengan
    // alamat /home dan request method get akan
    // ditangani oleh method home ini
    @GetMapping("/home") // langsung pake get
    public String home(Model model) {
        // ketika saya melakukan return "home", method ini akan memberitahukan kepada
        // view resolver kalau di harus merender sebuah komponen view yang bernama home
        model.addAttribute("name", "Nael");// kayak array asociation yang dikirim ke view sebagai data di laravel tea
                                           // geuningan.
        model.addAttribute("age", "21");
        return "home";
        // TODO: Lanjut ke looping untuk thymeleaf
    }

}
