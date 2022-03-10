package com.qmw.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 文件相关工具类
 *
 * @author qmw
 * @since 1.00
 */
public class FileUtil {

    private final static String[] UNIT = {"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};

    /**
     * 将long类型的文件大小转换为带单位的文件大小
     *
     * @param size  文件大小
     * @param scale 保留几位小数
     * @return 带单位的文件大小
     */
    public static String formatFileSize(long size, int scale) {
        if (size == 0)
            return "0";
        int power = Math.min(new Double(Math.log(size) / Math.log(1024)).intValue(), UNIT.length - 1); // 判断size是1024的几次方，但这个次方数不能大于UNIT的length-1
        String num = BigDecimal.valueOf(size / Math.pow(1024, power)).setScale(scale, RoundingMode.FLOOR).toPlainString(); // 除掉1024 n次方的部分
        return num + UNIT[power]; // 拼接
    }

    public static String formatFileSize(long size) {
        return formatFileSize(size, 0);
    }

    public static String getFileType(File file) {
        return getFileType(Objects.requireNonNull(file).getName());
    }

    public static String getFileType(String name) {
        if (name != null && name.contains("."))
            return name.substring(name.lastIndexOf(".") + 1);
        return "";
    }

    public static String getFileType(MultipartFile file) {
        return getFileType(file.getOriginalFilename());
    }

//    /**
//     * 远程下载文件到本地
//     * create 2020-12-18
//     *
//     * @param remoteUrl     网络文件链接
//     * @param localFilePath 本地保存路径
//     */
//    public static File remoteDownload(String remoteUrl, String localFilePath) {
//        URL urlfile;
//        HttpURLConnection httpUrl;
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        File file = new File(localFilePath);
//        try {
//            urlfile = new URL(remoteUrl);
//            httpUrl = (HttpURLConnection) urlfile.openConnection();
//            httpUrl.connect();
//            bis = new BufferedInputStream(httpUrl.getInputStream());
//            bos = new BufferedOutputStream(new FileOutputStream(file));
//            int len = 2048;
//            byte[] b = new byte[len];
//            while ((len = bis.read(b)) != -1)
//                bos.write(b, 0, len);
//            bos.flush();
//            bis.close();
//            httpUrl.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CustomException("远程文件下载失败");
//        } finally {
//            try {
//                if (bis != null)
//                    bis.close();
//                if (bos != null)
//                    bos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return file;
//    }

}
