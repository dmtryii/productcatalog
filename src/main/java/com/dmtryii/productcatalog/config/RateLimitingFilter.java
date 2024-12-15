package com.dmtryii.productcatalog.config;

import com.dmtryii.productcatalog.service.RateLimitingService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;

@Setter
public class RateLimitingFilter implements Filter {

    private RateLimitingService rateLimitingService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String clientIp = servletRequest.getRemoteAddr();
        Bucket bucket = rateLimitingService.resolveBucket(clientIp);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (servletResponse instanceof HttpServletResponse) {
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.setStatus(429); // Too Many Requests
                httpResponse.getWriter().write("Too many requests - Rate limit exceeded");
            } else {
                servletResponse.getWriter().write("Too many requests - Rate limit exceeded");
            }
        }
    }
}
