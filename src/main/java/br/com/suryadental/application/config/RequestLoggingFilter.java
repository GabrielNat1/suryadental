package br.com.suryadental.application.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(RequestLoggingFilter.class);

    // ANSI colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            int status = response.getStatus();

            String color;
            String level;

            if (status >= 500) {
                color = RED;
                level = "ERROR";
            } else if (status >= 400) {
                color = YELLOW;
                level = "WARN";
            } else {
                color = GREEN;
                level = "INFO";
            }

            String method = request.getMethod();
            String uri = request.getRequestURI();

            String message = String.format(
                    "%s[%s]%s %s%s %s-> %d (%d ms)%s",
                    BLUE,
                    level,
                    RESET,
                    CYAN,
                    method,
                    uri,
                    status,
                    duration,
                    RESET
            );

            logger.info("{}[HTTP] {} {} -> {} ({} ms){}",
                    color,
                    method,
                    uri,
                    status,
                    duration,
                    RESET
            );
        }
    }
}

