package com.nael.catalog.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nael.catalog.security.model.AccessJWTToken;
import com.nael.catalog.security.util.JWTTokenFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UsernamePasswordAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    private final JWTTokenFactory tokenFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO: lanjut untuk authentication handler
        // pada kelas ini akan ditangani, apa yang akan dilakukan oleh spring security
        // jika rest autentikasi tersebut berhasil
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AccessJWTToken token = tokenFactory.createAccessJWTToken(userDetails.getUsername(),
                userDetails.getAuthorities());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", token.getToken());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), resultMap);
        clearAuthenticationAttributes(request);
    }

    // membersihkan session
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null){
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
