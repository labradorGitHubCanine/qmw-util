package com.qmw.util;

import com.alibaba.fastjson.JSON;

/**
 * JSON相关工具类
 *
 * @author qmw
 * @since 1.08
 */
public class JSONUtil {

    /**
     * 判断string是否符合JSONObject格式
     *
     * @param string string
     * @return true是false否
     */
    public static boolean isValidObject(String string) {
        try {
            JSON.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断string是否符合JSONArray格式
     *
     * @param string string
     * @return true是false否
     */
    public static boolean isValidArray(String string) {
        try {
            JSON.parseArray(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
