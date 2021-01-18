package com.qmw.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qmw.exception.CustomException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.HashMap;

/**
 * 阿里云短信工具类
 *
 * @author qmw
 * @since 1.06
 */
public class AliyunSMSUtil {

    private static String ACCESS_KEY_ID;
    private static String ACCESS_KEY_SECRET;
    private static String SIGN_NAME; // 短信签名：例如【顺丰】

    // 初始化参数，可在项目启动时调用一次
    public static void init(String accessKeyId, String accessKeySecret, String signName) {
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        SIGN_NAME = signName;
    }

    public static Result send(Iterable<String> phoneNumbers, String templateCode, Params params) {
        if (ACCESS_KEY_ID == null || ACCESS_KEY_SECRET == null || SIGN_NAME == null)
            throw new CustomException("请先调用init方法进行初始化");

        DefaultProfile profile = DefaultProfile.getProfile("default", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", String.join(",", phoneNumbers));
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("TemplateCode", templateCode);
        if (params != null)
            request.putQueryParameter("TemplateParam", params.toString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            return JSON.parseObject(response.getData(), Result.class);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result().setMessage(e.getMessage());
        }
    }

    public static Result send(Iterable<String> phoneNumbers, String templateCode) {
        return send(phoneNumbers, templateCode, null);
    }

    public static Result send(String phoneNumber, String templateCode, Params params) {
        return send(Collections.singletonList(phoneNumber), templateCode, params);
    }

    public static Result send(String phoneNumber, String templateCode) {
        return send(Collections.singletonList(phoneNumber), templateCode, null);
    }

    // 请求参数类
    public static class Params {

        private HashMap<String, Object> map;

        public Params put(String key, Object value) {
            if (map == null)
                map = new HashMap<>();
            map.put(key, value);
            return this;
        }

        public HashMap<String, Object> getMap() {
            return map == null ? new HashMap<>() : map;
        }

        @Override
        public String toString() {
            if (map == null)
                map = new HashMap<>();
            return JSON.toJSONString(map);
        }

    }

    // 阿里云短信结果类
    @Data
    @Accessors(chain = true)
    public static class Result {

        private String Message;
        private String RequestId;
        private String BizId;
        private String Code;

    }

}
