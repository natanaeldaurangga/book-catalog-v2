package com.nael.catalog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nael.catalog.DTO.UserDetailResponseDTO;

public interface AppUserService extends UserDetailsService {

    public UserDetailResponseDTO findUserDetail();

}
