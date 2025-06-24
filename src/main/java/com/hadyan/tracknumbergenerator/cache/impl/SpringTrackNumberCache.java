package com.hadyan.tracknumbergenerator.cache.impl;

import com.hadyan.tracknumbergenerator.cache.ITrackNumberCache;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringTrackNumberCache implements ITrackNumberCache {
    private static final String CACHE_NAME = "used-tracking-numbers";
    private final CacheManager cacheManager;

    @Override
    public void put(String key, String value) {
        cacheManager.getCache(CACHE_NAME).put(key, value);
    }

    @Override
    public String get(String key) {
        try {
            var cache = cacheManager.getCache(CACHE_NAME);
            return cache.get(key, String.class);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void delete(String key) {
        cacheManager.getCache(CACHE_NAME).evict(key);
    }

    @Override
    public boolean exists(String key) {
        try {
            var cache = cacheManager.getCache(CACHE_NAME);
            return cache.get(key, String.class) != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
