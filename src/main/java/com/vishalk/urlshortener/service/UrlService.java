package com.vishalk.urlshortener.service;

import com.vishalk.urlshortener.entity.UrlMapping;
import com.vishalk.urlshortener.repository.UrlRepository;
import com.vishalk.urlshortener.util.Base62;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class UrlService {

    private final UrlRepository repo;
    private final StringRedisTemplate redis;

    public UrlService(UrlRepository repo, StringRedisTemplate redis) {
        this.repo = repo;
        this.redis = redis;
    }

    public String shorten(String longUrl) {
        UrlMapping m = repo.findByLongUrl(longUrl).orElseGet(() -> {
            UrlMapping fresh = new UrlMapping();
            fresh.setLongUrl(longUrl);
            return repo.save(fresh);
        });
        return Base62.encode(m.getId() + 100000);
    }

    public String resolve(String code) {
        String cached = redis.opsForValue().get(code);
        if (cached != null) return cached;
        long id = Base62.decode(code) - 100000;
        String longUrl = repo.findById(id).orElseThrow().getLongUrl();
        redis.opsForValue().set(code, longUrl, Duration.ofHours(1));
        return longUrl;
    }
}
