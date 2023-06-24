package com.nael.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nael.catalog.domain.AppUser;

public interface AppUserRespository extends JpaRepository<AppUser, Long> {

    public Optional<AppUser> findByUsername(String username);

}
