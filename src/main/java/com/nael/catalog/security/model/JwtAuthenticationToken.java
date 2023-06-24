package com.nael.catalog.security.model;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private RawAccessJWTToken rawAccessJWTToken;

    private UserDetails userDetails;

    // 1. jika token sudah diverifikasi
    public JwtAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userDetails = userDetails;
        super.setAuthenticated(true);
    }

    // 2. untuk token yang belum diverifikasi
    public JwtAuthenticationToken(RawAccessJWTToken rawAccessJWTToken) {
        super(null);
        this.rawAccessJWTToken = rawAccessJWTToken;
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        return this.rawAccessJWTToken;
    }

    @Override
    public Object getPrincipal() {
        // TODO Auto-generated method stub
        return this.userDetails;
    }

    @Override
    public void eraseCredentials() {
        // TODO Auto-generated method stub
        super.eraseCredentials();
        this.rawAccessJWTToken = null;
    }

}
