package com.qmw.util;

/**
 * 操作系统相关工具类
 *
 * @author qmw
 * @since 1.00
 */
public class OSUtil {

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

}
