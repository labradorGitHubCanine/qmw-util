package com.qmw.entity;

public class AliyunConfig {

    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucketName;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public AliyunConfig setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public AliyunConfig setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public AliyunConfig setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public AliyunConfig setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

}
