package com.nael.catalog.security.provider;

import java.security.Key;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nael.catalog.security.model.JwtAuthenticationToken;
import com.nael.catalog.security.model.RawAccessJWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Key key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJWTToken token = (RawAccessJWTToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = token.parseClaims(key);
        String subject = jwsClaims.getBody().getSubject();
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        // TODO: Lanjut untuk materi terakhir di spring security
        UserDetails userDetails = new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                // TODO Auto-generated method stub
                return authorities;
            }

            @Override
            public String getPassword() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getUsername() {
                // TODO Auto-generated method stub
                return subject;
            }

            @Override
            public boolean isAccountNonExpired() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

        };

        return new JwtAuthenticationToken(userDetails, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return false;
    }

}
