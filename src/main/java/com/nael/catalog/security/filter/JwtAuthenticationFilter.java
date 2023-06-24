package com.nael.catalog.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.nael.catalog.security.model.AnonymousAuthentication;
import com.nael.catalog.security.model.JwtAuthenticationToken;
import com.nael.catalog.security.model.RawAccessJWTToken;
import com.nael.catalog.security.util.TokenExtractor;

import lombok.AllArgsConstructor;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenExtractor tokenExtractor;

    private final AuthenticationFailureHandler failureHandler;

    public JwtAuthenticationFilter(TokenExtractor tokenExtractor,
            RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationFailureHandler failureHandler) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenExtractor = tokenExtractor;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String tokenPayload = request.getHeader("Authorization");
        RawAccessJWTToken token = new RawAccessJWTToken(tokenExtractor.extract(tokenPayload));
        return this.getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override // jika autentikasi token berhasil
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // TODO Auto-generated method stub
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        chain.doFilter(request, response);
        // super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override // jika autentikasi token gagal
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // TODO Auto-generated method stub
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());
        // menerima paramter berupa objek turunan dari kelas Authentication
        // super.unsuccessfulAuthentication(request, response, failed);
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
