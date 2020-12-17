package com.qmw.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qmw.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
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
    public static String upload(MultipartFile file, String path) {
        String fileName = UUID.randomUUID().toString();
        String fileType = FileUtil.getFileType(Objects.requireNonNull(file.getOriginalFilename()));
        String fullName = StringUtil.ifEmptyThen(path, "") + fileName + "." + fileType;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        InputStream stream = null;
        try {
            stream = file.getInputStream();
            ossClient.putObject(BUCKET_NAME, fullName, stream);
            ossClient.shutdown();
        } catch (IOException e) {
            throw new CustomException("文件解析失败");
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

    public static String upload(MultipartFile file) {
        return upload(file, "");
    }

    public static void delete(String url) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String objectName = url.replace("https://" + BUCKET_NAME + "." + ENDPOINT + "/", "");
        ossClient.deleteObject(BUCKET_NAME, objectName);
        ossClient.shutdown();
    }

}
