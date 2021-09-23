package com.qmw.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (!string.startsWith("{") || !string.endsWith("}"))
                return false;
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
            if (!string.startsWith("[") || !string.endsWith("]"))
                return false;
            JSON.parseArray(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Map<String, Object>> toList(String s) {
        List<Map<String, Object>> list = new ArrayList<>();
        JSON.parseArray(s).forEach(e -> list.add(new HashMap<>(JSON.parseObject(e.toString()))));
        return list;
    }

}
