package com.dmtryii.productcatalog.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class RateLimitingConfig {

    @Getter
    @Value("${rate.limiting.requests.per.minute}")
    private int requestsPerMinute;

    @Value("${rate.limiting.refill.duration}")
    private String refillDuration;

    @Getter
    @Value("${rate.limiting.refill.amount}")
    private int refillAmount;

    public Duration getRefillDuration() {
        return Duration.parse("PT" + refillDuration.toUpperCase());
    }
}
