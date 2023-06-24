package com.nael.catalog.security.util;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher {

    private OrRequestMatcher skipMatcher;
    private OrRequestMatcher processingMatcher;

    public SkipPathRequestMatcher(List<String> pathToSkip, List<String> processingPath) {
        List<RequestMatcher> m = pathToSkip.stream().map(path -> new AntPathRequestMatcher(path))
                .collect(Collectors.toList()); // mengupbah path menjadi Requestmatcher 1 per 1
        skipMatcher = new OrRequestMatcher(m);

        List<RequestMatcher> p = processingPath.stream().map(path -> new AntPathRequestMatcher(path))
                .collect(Collectors.toList());

        processingMatcher = new OrRequestMatcher(p);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (skipMatcher.matches(request)) {
            return false;
        }

        return processingMatcher.matches(request) ? true : false;
    }

}
