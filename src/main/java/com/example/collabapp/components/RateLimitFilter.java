package com.example.collabapp.components;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RateLimitFilter extends OncePerRequestFilter {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket(){
        return Bucket.builder()
                .addLimit(Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1))))
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip,k->createBucket());

        if(bucket.tryConsume(1)){
            filterChain.doFilter(request,response);
        }else{
            log.warn("Rate limit exceeded for IP: {}",ip);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"status\":429,\"message\":\"Too many requests. Please slow down.\"}"
            );
        }
    }
}
