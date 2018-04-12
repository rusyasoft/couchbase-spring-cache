package com.tmoncorp.api.dealinfo.connector;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.tmoncorp.api.dealinfo.constants.CommonConstants;
import com.tmoncorp.api.dealinfo.util.ValidateCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CouchBaseConnector implements DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouchBaseConnector.class);

    private final Bucket bucket;
    private final String environment;

    public CouchBaseConnector() {
        this.bucket = null;
        this.environment = null;
    }

    public CouchBaseConnector(String serverNodeList, String bucketName, String bucketPassword, String environment) {
        this.environment = environment;
        CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment.builder().build();
        this.bucket = this.connect(serverNodeList, bucketName, bucketPassword, couchbaseEnvironment);
    }

    public CouchBaseConnector(String serverNodeList, String bucketName, String bucketPassword, int keyValueNodeCount, long keyValueTimeout, String environment) {
        this.environment = environment;

        if (ValidateCheckUtil.isUpperThanZero(keyValueTimeout) == false) {
            keyValueTimeout = CommonConstants.COUCHBASE_DEFAULT_READ_TIMEOUT;
        }

        CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment
                .builder()
                .kvEndpoints(keyValueNodeCount)
                .kvTimeout(keyValueTimeout)
                .build();

        this.bucket = this.connect(serverNodeList, bucketName, bucketPassword, couchbaseEnvironment);
    }

    public CouchBaseConnector(String serverNodeList, String bucketName, String bucketPassword, int keyValueNodeCount, int n1qlNodeCount, String environment) {
        this.environment = environment;

        CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment
                .builder()
                .kvEndpoints(keyValueNodeCount)
                .queryEndpoints(n1qlNodeCount)
                .build();

        this.bucket = this.connect(serverNodeList, bucketName, bucketPassword, couchbaseEnvironment);
    }

    public Bucket getBucket() {
        return bucket;
    }

    public AsyncBucket getAsyncBucket() {
        return bucket.async();
    }

    public String getEnvironment() {
        return environment;
    }

    public void disconnect() {
        try {
            if (Objects.nonNull(bucket)) {
                bucket.close();
            }

            LOGGER.info("CouchBase connection closed successfully.");
        } catch (Exception e) {
            LOGGER.error("ERROR WHILE CONNECTION CLOSE:", e);
        }
    }

    @Override
    public void destroy() {
        disconnect();
    }

    private Bucket connect(String serverNodeList, String bucketName, String bucketPassword, CouchbaseEnvironment couchbaseEnvironment) {
        try {
            Cluster cluster = CouchbaseCluster.create(couchbaseEnvironment, serverNodeList.split(","));

            return cluster.openBucket(bucketName, bucketPassword, CommonConstants.COUCHBASE_DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOGGER.error("CONNECTION NOT ESTABLISHED:", e);

            return null;
        }
    }

} 