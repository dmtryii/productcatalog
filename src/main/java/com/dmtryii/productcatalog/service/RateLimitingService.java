package com.dmtryii.productcatalog.service;

import com.dmtryii.productcatalog.config.RateLimitingConfig;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private final RateLimitingConfig rateLimitingConfig;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public RateLimitingService(RateLimitingConfig rateLimitingConfig) {
        this.rateLimitingConfig = rateLimitingConfig;
    }

    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, this::newBucket);
    }

    private Bucket newBucket(String key) {
        Bandwidth limit = Bandwidth.classic(rateLimitingConfig.getRequestsPerMinute(),
                Refill.greedy(rateLimitingConfig.getRefillAmount(),
                        rateLimitingConfig.getRefillDuration()));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
