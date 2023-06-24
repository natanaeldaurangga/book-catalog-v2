package com.nael.catalog.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginRequestDTO implements Serializable {
    
    private String username;

    private String password;

}
