package com.qmw.util;

public class OSUtil {

    /**
     * 判断是否linux系统
     *
     * @return boolean
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    /**
     * 判断是否wi ndows系统
     *
     * @return boolean
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

}
