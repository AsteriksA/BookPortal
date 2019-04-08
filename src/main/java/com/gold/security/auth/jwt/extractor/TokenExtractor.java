package com.gold.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
