package com.dmtryii.productcatalog.filter;

import com.dmtryii.productcatalog.exceptions.ErrorResponse;
import com.dmtryii.productcatalog.service.RateLimitingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RateLimitingFilter implements Filter {

    private final RateLimitingService rateLimitingService;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String clientIp = servletRequest.getRemoteAddr();
        Bucket bucket = rateLimitingService.resolveBucket(clientIp);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            respondWithRateLimitExceeded((HttpServletResponse) servletResponse);
        }
    }

    private void respondWithRateLimitExceeded(HttpServletResponse httpResponse) throws IOException {
        ErrorResponse errorResponse = buildErrorResponse();
        httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        httpResponse.setContentType("application/json");
        httpResponse.getWriter().write(convertToJson(errorResponse));
    }

    private ErrorResponse buildErrorResponse() {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .error("Rate Limit Exceeded")
                .message("Too many requests - Rate limit exceeded")
                .build();
    }

    private String convertToJson(ErrorResponse errorResponse) {
        try {
            return objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error converting ErrorResponse to JSON", e);
        }
    }
}
