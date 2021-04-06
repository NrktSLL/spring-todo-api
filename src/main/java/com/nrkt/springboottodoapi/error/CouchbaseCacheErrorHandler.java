package com.nrkt.springboottodoapi.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class CouchbaseCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error("Exception while getting key={} from cache={}", key, cache.getName(), exception);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.error("Exception while putting key={} value={} into cache={}", key, value, cache.getName(), exception);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error("Exception while evicting key={} from cache={}", key, cache.getName(), exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error("Exception while clearing elements from cache={}", cache.getName(), exception);
    }
}
