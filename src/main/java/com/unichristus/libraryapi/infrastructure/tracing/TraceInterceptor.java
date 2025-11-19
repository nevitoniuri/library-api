package com.unichristus.libraryapi.infrastructure.tracing;

import com.unichristus.libraryapi.infrastructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class TraceInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String uri = request.getRequestURI();
        if (uri.contains("/swagger") || uri.contains("/v3/api-docs")) {
            return true;
        }
        startTime.set(System.currentTimeMillis());
        String method = request.getMethod();
        String token = getToken(request);
        String userEmail = getAuthenticatedUserEmail(token);
        String params = getParams(request);

        log.debug("Request => {} {} | User: {} | Params: {} | Token: {}",
                method,
                uri,
                userEmail,
                params,
                token
        );
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        String uri = request.getRequestURI();
        if (uri.contains("/swagger") || uri.contains("/v3/api-docs")) {
            return;
        }
        long elapsed = System.currentTimeMillis() - startTime.get();
        startTime.remove();
        String requestBody = getRequestBody(request);
        String responseBody = getResponseBody(response);
        log.debug("Response => Status: {} | Time: {} ms | RequestBody: {} | ResponseBody: {}",
                response.getStatus(),
                elapsed,
                requestBody,
                responseBody
        );
    }

    private String getAuthenticatedUserEmail(String token) {
        try {
            String email = jwtService.extractEmail(token);
            return email != null ? email : "anonymous";
        } catch (Exception e) {
            log.debug("Failed to extract email from token: {}", e.getMessage());
            return "anonymous";
        }
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

    private String getParams(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return queryString != null ? queryString : "none";
    }

    private String getRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            return extractBody(wrapper.getContentAsByteArray());
        }
        return "empty";
    }

    private String getResponseBody(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper wrapper) {
            return extractBody(wrapper.getContentAsByteArray());
        }
        return "empty";
    }

    private String extractBody(byte[] content) {
        if (content != null && content.length > 0) {
            String body = new String(content, StandardCharsets.UTF_8);
            return maskPassword(body);
        }
        return "empty";
    }

    private String maskPassword(String body) {
        if (body == null || body.isEmpty()) {
            return body;
        }
        return body.replaceAll("(\"password\"\\s*:\\s*\")([^\"]+)(\")", "$1***$3");
    }
}