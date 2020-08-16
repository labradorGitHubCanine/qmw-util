package com.qmw.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ip工具
 *
 * @author qmw
 * @since 1.00
 */
public class IPUtil {

    /**
     * 获取请求的ip地址
     *
     * @param request request
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isUnknown(ip)) ip = request.getHeader("Proxy-Client-IP");
        if (isUnknown(ip)) ip = request.getHeader("WL-Proxy-Client-IP");
        if (isUnknown(ip)) ip = request.getHeader("HTTP_CLIENT_IP");
        if (isUnknown(ip)) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (isUnknown(ip)) ip = request.getRemoteAddr();
        return ip;
    }

    private static boolean isUnknown(String ip) {
        return ip == null || ip.trim().isEmpty() || ip.trim().equalsIgnoreCase("unknown");
    }

}
