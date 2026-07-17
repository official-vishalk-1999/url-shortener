package com.vishalk.urlshortener.repository;

import com.vishalk.urlshortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByLongUrl(String longUrl);
}
