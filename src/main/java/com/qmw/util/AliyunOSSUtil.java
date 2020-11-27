package com.qmw.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
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

    private static String ACCESS_KEY_ID;
    private static String ACCESS_KEY_SECRET;
    private static String ENDPOINT; // 不带 http或https的那部分 例如:oss-cn-hangzhou.aliyuncs.com
    private static String BUCKET_NAME;

    public static void init(
            String accessKeyId,
            String accessKeySecret,
            String endpoint,
            String bucketName
    ) {
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        ENDPOINT = endpoint;
        BUCKET_NAME = bucketName;
    }

    // 上传单个文件
    public static String upload(File file, String... folder) {
        String fileName = UUID.randomUUID().toString();
        String fileType = FileUtil.getFileType(file);
        String fullName = (folder == null || folder.length == 0 ? "" : String.join("/", folder) + "/") + fileName + "." + fileType;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            ossClient.putObject(BUCKET_NAME, fullName, stream);
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
        return "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + fullName;
    }

    public static void delete(String url) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String objectName = url.replace("https://" + BUCKET_NAME + "." + ENDPOINT + "/", "");
        ossClient.deleteObject(BUCKET_NAME, objectName);
        ossClient.shutdown();
    }

}
