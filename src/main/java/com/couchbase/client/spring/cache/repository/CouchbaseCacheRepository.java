package com.couchbase.client.spring.cache.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.spring.cache.util.JsonUtils;
import com.tmoncorp.api.dealinfo.connector.CouchBaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-cache.xml",
        })
@Component
public class CouchbaseCacheRepository implements CacheRepository {


    private Bucket bucket;
    private CouchBaseConnector couchBaseConnector;
    private String environment;

    @Autowired
    @Qualifier(value = "cacheBucketConnector")
    protected void setBucket(CouchBaseConnector couchBaseConnector) {
        this.couchBaseConnector = couchBaseConnector;
        this.bucket = couchBaseConnector.getBucket();
        this.environment = couchBaseConnector.getEnvironment();
    }


    @Override
    public <T> T upsertCache(String key, int expiry, T target) {
        JsonUtils jsonUtils = new JsonUtils();
        String targetJson = JsonUtils.objectToJson(target);

        if (targetJson.startsWith("[")) {
            bucket.upsert(JsonArrayDocument.create(key, expiry, JsonArray.create().fromJson(targetJson)));
        } else {
            bucket.upsert(JsonDocument.create(key, expiry, JsonObject.create().fromJson(targetJson)));
        }
        return target;

    }

    @Override
    public <T> T getCache(String key, Class<T> returnType, Type genericReturnType) {
        RawJsonDocument doc = bucket.get(key, RawJsonDocument.class);

        try {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            return (T) JsonUtils.jsonToGenericObject(doc.content(), returnType, parameterizedType.getActualTypeArguments());
        } catch (ClassCastException e) {
            return JsonUtils.jsonToObject(doc.content(), returnType);
        }
    }
}