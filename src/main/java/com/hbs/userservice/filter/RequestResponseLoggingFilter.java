package com.hbs.userservice.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (servletRequest instanceof HttpServletRequest request && servletResponse instanceof HttpServletResponse response) {
            if (request.getRequestURI().contains("/actuator")) return;

            log.info(this.createRequestLog(request));

            try {
                filterChain.doFilter(request, response);
            } finally {
                log.info(this.createResponseLog(response));
            }
        }
    }

    private String createRequestLog(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String requestRemoteAddr = request.getRemoteAddr();
        String requestLocation = request.getHeader("Location");

        return "[FILTER] Logging Request: " + requestMethod + " | URI: " + requestURI + " | IP: " + requestRemoteAddr
                + " | Location: " + requestLocation;
    }

    private String createResponseLog(HttpServletResponse response) {
        String responseStatus = String.valueOf(response.getStatus());
        String responseContentType = response.getContentType();

        return "[FILTER] Logging Response: " + responseStatus + " | Content Type: " + responseContentType;
    }
}
