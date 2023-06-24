package com.nael.catalog.config;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nael.catalog.domain.Author;
import com.nael.catalog.domain.Book;
import com.nael.catalog.repository.BookRepository;
import com.nael.catalog.repository.impl.BookRepositoryImpl;
import com.nael.catalog.security.util.JWTTokenFactory;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// @ComponentScan(basePackages = { "com.nael" }) // spring container akan meng scan semua kelas yang ada di peckage itu
@Configuration // dengan memberikan anotasi ini, kita akan memberitahu spring container bahwa
               // ini adalah javabase configuration
public class AppConfig {

    @Bean
    public Key key() {
        byte[] keyBytes = Decoders.BASE64.decode("jrthpPJ49R34234ljsoidufsndli79sdfosid7fs9d8sfd");
        // isinya terserah tapi jangan terlalu pendek, minimal 32 karakter lebih lah
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // TODO: Lanjut untuk JWT Authentication

    @Bean
    public JWTTokenFactory jwtTokenFactory(Key key) {
        return new JWTTokenFactory(key);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
