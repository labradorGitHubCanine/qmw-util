package com.qmw.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Objects;

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
        int power = Math.min(new Double(Math.log(size) / Math.log(1024)).intValue(), UNIT.length - 1); // 判断size是1024的几次方，但这个次方数不能大于UNIT的length-1
        String num = BigDecimal.valueOf(size / Math.pow(1024, power)).setScale(scale, BigDecimal.ROUND_FLOOR).toPlainString(); // 除掉1024 n次方的部分
        return num + UNIT[power]; // 拼接
    }

    public static String formatFileSize(long size) {
        return formatFileSize(size, 1);
    }

    public static String getFileType(File file) {
        return getFileType(Objects.requireNonNull(file).getName());
    }

    public static String getFileType(String name) {
        if (name.contains("."))
            return name.substring(name.lastIndexOf(".") + 1);
        return "";
    }

}
