package com.couchbase.client.spring.cache.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

public class JsonUtils {

    //private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Object jsonToGenericObject(String jsonString, Class genericClazz, Type... parameterClasses) {
        try {
            Class[] classes = Arrays.stream(parameterClasses)
                    .map(type -> (Class)type)
                    .toArray(Class[]::new);

            return objectMapper.readValue(jsonString, objectMapper.getTypeFactory()
                    .constructParametricType(genericClazz, classes));
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public static <T> T jsonToObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
} 