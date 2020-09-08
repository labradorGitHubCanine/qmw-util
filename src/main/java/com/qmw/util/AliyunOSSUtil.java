package com.qmw.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qmw.entity.AliyunConfig;
import com.qmw.exception.CustomException;

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
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    public static void init(AliyunConfig config) {
        if (StringUtil.isEmpty(config.getAccessKeyId()))
            throw new CustomException("请设置accessKeyId");
        if (StringUtil.isEmpty(config.getAccessKeySecret()))
            throw new CustomException("请设置accessKeySecret");
        if (StringUtil.isEmpty(config.getEndpoint())) // https://oss-cn-hangzhou.aliyuncs.com
            throw new CustomException("请设置endpoint");
        if (config.getEndpoint().startsWith(HTTP))
            config.setEndpoint(config.getEndpoint().substring(HTTP.length()));
        else if (config.getEndpoint().startsWith(HTTPS))
            config.setEndpoint(config.getEndpoint().substring(HTTPS.length()));
        if (StringUtil.isEmpty(config.getBucketName()))
            throw new CustomException("请设置bucketName");
        AliyunOSSUtil.config = config;
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
            throw new CustomException(e.getMessage());
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return HTTPS + config.getBucketName() + "." + config.getEndpoint() + "/" + fullName;
    }

    public static void delete(String url) {
        OSS ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        String objectName = url.replace(HTTPS + config.getBucketName() + "." + config.getEndpoint() + "/", "");
        ossClient.deleteObject(config.getBucketName(), objectName);
        ossClient.shutdown();
    }

}
