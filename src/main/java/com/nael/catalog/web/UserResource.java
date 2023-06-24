package com.nael.catalog.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nael.catalog.DTO.UserDetailResponseDTO;
import com.nael.catalog.domain.AppUser;
import com.nael.catalog.service.AppUserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserResource {

    private final AppUserService appUserService;

    @GetMapping("/v1/user")
    public ResponseEntity<UserDetailResponseDTO> findUserDetail() {
        return ResponseEntity.ok().body(appUserService.findUserDetail());
    };

}
