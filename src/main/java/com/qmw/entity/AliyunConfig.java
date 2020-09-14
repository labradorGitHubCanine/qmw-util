package com.qmw.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 阿里云配置类
 *
 * @author qmw
 * @since 1.06
 */
@Data
@Accessors(chain = true)
public class AliyunConfig {

    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucketName;
    private String signName;

}
