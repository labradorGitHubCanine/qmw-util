package com.qmw.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qmw.entity.AliyunConfig;

import java.io.*;
import java.util.UUID;

/**
 * 阿里云OSS工具类
 *
 * @author qmw
 * @since 1.06
 */
public class AliyunOSSUtil {

    private static AliyunConfig config;

    public static void init(String accessKeyId, String accessKeySecret, String endpoint, String bucketName) {
        config = new AliyunConfig()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint(endpoint)
                .setBucketName(bucketName);
    }

    // 上传单个文件
    public static String upload(File file, String... folder) {
        String fileName = UUID.randomUUID().toString();
        String fileType = FileUtil.getFileType(file);
        String fullName = (folder == null || folder.length == 0 ? "" : String.join("/", folder) + "/") + fileName + "." + fileType;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            ossClient.putObject(config.getBucketName(), fullName, stream);
            ossClient.shutdown();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "https://" + config.getBucketName() + "." + config.getEndpoint().replace("https://", "") + "/" + fullName;
    }

    public static void delete(String url) {
        OSS ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        String objectName = url.replace("https://" + config.getBucketName() + "." + config.getEndpoint().replace("https://", "") + "/", "");
        ossClient.deleteObject(config.getBucketName(), objectName);
        ossClient.shutdown();
    }

}
