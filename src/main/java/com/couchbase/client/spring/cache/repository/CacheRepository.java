package com.couchbase.client.spring.cache.repository;

import java.lang.reflect.Type;

public interface CacheRepository {
    <T> T upsertCache(String key, int expiry, T target);
    <T> T getCache(String key, Class<T> returnType, Type genericReturnType);
} 