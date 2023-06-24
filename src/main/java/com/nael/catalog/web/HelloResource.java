package com.nael.catalog.web;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nael.catalog.DTO.HelloMessageResponseDTO;

import com.nael.catalog.service.GreetingService;

@RestController // memberitahu spring bahawa kelas ini adalah controller
public class HelloResource {

    Logger log = LoggerFactory.getLogger(HelloResource.class);

    private GreetingService greetingService;// kita akan melakukan dependency injection pada greeting service

    @Autowired // kita memberitahu spring kalo ini adalah depedency injection
    public HelloResource(GreetingService greetingService) {
        super();
        this.greetingService = greetingService;
    }

    // get adalah salah satu jenis dari http method/ http verb
    // GET, POST, PUT, DELETE, OPTION, TRACE, HEAD, PATCH
    @GetMapping("/hello") // memberitahu url apa yang digunakan untuk mengakses fungsi ini
    public HelloMessageResponseDTO helloWorld() {
        log.trace("This is log TRACE");
        log.debug("This is log DEBUG");
        log.info("This is log INFO");
        log.warn("This is log WARN");
        log.error("This is log ERROR");
        HelloMessageResponseDTO dto = new HelloMessageResponseDTO();
        dto.setMessage(greetingService.sayGreeting());
        return dto;
    }

    @GetMapping("/helloOke")
    public ResponseEntity<HelloMessageResponseDTO> helloWorlds() {
        HelloMessageResponseDTO dto = new HelloMessageResponseDTO();
        dto.setMessage(greetingService.sayGreeting());
        return ResponseEntity.accepted().body(dto);
    }

    // TODO: Lanjut ke create resource baru

}
