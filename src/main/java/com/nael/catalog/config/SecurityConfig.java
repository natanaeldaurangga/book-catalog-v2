package com.nael.catalog.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nael.catalog.security.filter.JwtAuthenticationFilter;
import com.nael.catalog.security.filter.UsernamePasswordAuthenticationFilter;
import com.nael.catalog.security.provider.JwtAuthenticationProvider;
import com.nael.catalog.security.provider.UsernamePasswordAuthenticationProvider;
import com.nael.catalog.security.util.SkipPathRequestMatcher;
import com.nael.catalog.security.util.TokenExtractor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String V1_URL = "/v1/**";
    private static final String V2_URL = "/v2/**";

    // @Autowired
    // private AppUserService appUserService;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired // anotasi yang akan melakukan instansiasi (new Class()) gituhh
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private AuthenticationManager authenticationManager; // ini salah , bikin
    // error

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenExtractor tokenExtractor;

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(appUserService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    // NOTE: fungsi ini berguna untuk menentukan path/url mana saja yang memerlukan
    // jwt untuk diakses dan mana yang tidak
    protected JwtAuthenticationFilter buildJwtAuthFilter(List<String> pathToSkip, List<String> patternList) {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathToSkip, patternList);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(tokenExtractor, matcher, failureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    protected UsernamePasswordAuthenticationFilter buildUsernamePasswordAuthFilter(String loginEntryPoint)
            throws Exception {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter(
                loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // list path mana saja yang bisa diskip (alias tidak perlu autentikasi JWT)
        List<String> permitAllEndPointList = Arrays.asList(
                "/v1/login");

        // list path mana saja yang memerlukan autentikasi jwt
        List<String> authenticatedEndPointList = Arrays.asList(
                V1_URL, V2_URL);

        http.authorizeRequests().antMatchers(V1_URL, V2_URL).authenticated()
                .and()
                .csrf().disable() // csrf didisable karena ini adalah aplikasi rest
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(buildUsernamePasswordAuthFilter("/v1/login"),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtAuthFilter(permitAllEndPointList, authenticatedEndPointList),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // TODO Auto-generated method stub
        return super.authenticationManagerBean();
    }

}
