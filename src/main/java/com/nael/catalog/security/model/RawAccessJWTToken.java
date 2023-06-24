package com.nael.catalog.security.model;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class RawAccessJWTToken implements Token {

    private String token; // akan menampung jwt yang diinputkan oleh client

    public RawAccessJWTToken(String token) {
        this.token = token;
    }

    // TODO: buat fungsi yang akan memparsing token jwt, diparsing menjadi claims
    public Jws<Claims> parseClaims(Key signingKey) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(this.token);
    }

    @Override
    public String getToken() {
        return this.token;
    }

}
