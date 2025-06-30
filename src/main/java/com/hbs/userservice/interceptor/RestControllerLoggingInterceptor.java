package com.hbs.userservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class RestControllerLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String requestURL = request.getRequestURL().toString();

        log.info("[INTERCEPTOR] Logging request: Request URI: {}, Request Method: {}, Request URL: {}",
                requestURI, requestMethod, requestURL);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (handler instanceof HandlerMethod method) {
            String controller = method.getBeanType().getSimpleName();
            String methodName = method.getMethod().getName();
            String userAgent = request.getHeader("User-Agent");

            log.info("[INTERCEPTOR] Logging response: User-Agent: {}, Method-Name: {}, Controller: {}",
                    userAgent, methodName, controller);
        }
    }
}
