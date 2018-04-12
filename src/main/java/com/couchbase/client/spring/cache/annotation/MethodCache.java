package com.couchbase.client.spring.cache.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodCache {
    int expiry() default 10;
} 