package com.dmtryii.productcatalog.config;

import com.dmtryii.productcatalog.service.RateLimitingService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter(RateLimitingService rateLimitingService) {
        RateLimitingFilter filter = new RateLimitingFilter();
        filter.setRateLimitingService(rateLimitingService); // Inject RateLimitingService using the setter

        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*"); // Apply to all endpoints
        return registrationBean;
    }
}
