package com.vishalk.urlshortener.controller;

import com.vishalk.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Map;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) { this.service = service; }

    @PostMapping("/shorten")
    public Map<String, String> shorten(@RequestBody Map<String, String> body) {
        return Map.of("shortCode", service.shorten(body.get("url")));
    }

    @GetMapping("/{code:[a-zA-Z0-9]+}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        return ResponseEntity.status(302)
                .location(URI.create(service.resolve(code))).build();
    }
}
