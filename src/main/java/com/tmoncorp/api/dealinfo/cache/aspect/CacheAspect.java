package com.tmoncorp.api.dealinfo.cache.aspect;


import com.tmoncorp.api.dealinfo.cache.annotation.MethodCache;
import com.tmoncorp.api.dealinfo.cache.repository.CacheRepository;
import com.tmoncorp.api.dealinfo.cache.util.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Aspect
public class CacheAspect {
    protected static Logger logger = (Logger) LoggerFactory.getLogger(CacheAspect.class);

    @Autowired(required = false)
    private ServletContext servletContext;

    @Autowired
    @Qualifier("couchbaseCacheRepository")
    CacheRepository cacheRepository;

    @Around("@annotation(com.tmoncorp.api.dealinfo.cache.annotation.MethodCache)")
    public Object anotherMethod(final ProceedingJoinPoint jointPoint) throws Throwable {

        System.out.println("-------- inside anotherMethod ---------");
        try {

            Method method = getDeclaredMethod(jointPoint);

            MethodCache methodCacheAnnotation = method.getAnnotation(MethodCache.class);

            int expiry = methodCacheAnnotation.expiry();
            String cacheKey = convertCacheKey(jointPoint);

            try {
                return cacheRepository.getCache(cacheKey, method.getReturnType(), method.getGenericReturnType());
            } catch (NullPointerException e) {
                System.out.println("--- saving into cache --");
                return cacheRepository.upsertCache(convertCacheKey(jointPoint), expiry, jointPoint.proceed());
            }

            // checking the couchbase
            // or
            // saving in the couchbase
//            System.out.println("---- Before joinPoint.proceed()  cacheKey = " + cacheKey);
//            int intres = (int)jointPoint.proceed();
//            System.out.println("---- After joinPoint.proceed() expiry = " + expiry + " res = " + intres);
//            return null;

        } catch (Exception e) {
            logger.error("[Cache Annotation Error] " + e.getMessage(), e);
            return jointPoint.proceed();
        }

    }

    private Method getDeclaredMethod(ProceedingJoinPoint jointPoint) {
        MethodSignature signature = (MethodSignature) jointPoint.getSignature();
        signature.getClass();
        Method method = signature.getMethod();

        if (method.getDeclaringClass().isInterface()) {
            try {
                method = jointPoint.getTarget().getClass().getDeclaredMethod(jointPoint.getSignature().getName(),
                        method.getParameterTypes());
            } catch (SecurityException exception) {
                throw new RuntimeException();
            } catch (NoSuchMethodException exception) {
                throw new RuntimeException();
            }
        }

        return method;
    }

    private String convertCacheKey(ProceedingJoinPoint joinPoint) {
        //Optional.ofNullable(servletContext);
//
//        String cacheKey = Optional.ofNullable(servletContext)
//                .map(servletContext -> servletContext.getServletContextName())
//                .orElseGet(String::new);
        // TODO: calling function based cache key, but required to distinguish based on servlet
        String cacheKey = "todoServeltContext" //servletContext.getContextPath()
                + "_"
                + joinPoint.getSignature().getDeclaringType().toString().replaceAll(" ", "_")
                + joinPoint.getSignature().getName()
                + "("
                + Stream.of(joinPoint.getArgs())
                .map(JsonUtils::objectToJson)
                .collect(Collectors.joining(","))
                + ")";

        return cacheKey;
    }






} 