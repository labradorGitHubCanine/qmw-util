package com.qmw.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qmw.entity.AliyunConfig;
import com.qmw.exception.CustomException;

import java.util.List;
import java.util.Map;

/**
 * 阿里云短信工具类
 *
 * @author qmw
 * @since 1.06
 */
public class AliyunSMSUtil {

    private static AliyunConfig config;

    public static void init(AliyunConfig config) {
        if (StringUtil.isEmpty(config.getAccessKeyId()))
            throw new CustomException("请设置accessKeyId");
        if (StringUtil.isEmpty(config.getAccessKeySecret()))
            throw new CustomException("请设置accessKeySecret");
        if (StringUtil.isEmpty(config.getSignName()))
            throw new CustomException("请设置signName");
        AliyunSMSUtil.config = config;
    }

    public static Result send(List<String> phoneNumbers, String templateCode, Map<String, Object> params) {
        DefaultProfile profile = DefaultProfile.getProfile("default", config.getAccessKeyId(), config.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", String.join(",", phoneNumbers));
        request.putQueryParameter("SignName", config.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(params));
        try {
            CommonResponse response = client.getCommonResponse(request);
            return JSON.parseObject(response.getData(), Result.class);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }

    // 请求参数类
    public static class Params {

    }

    // 阿里云短信结果类
    public static class Result {

        private String Message;
        private String RequestId;
        private String BizId;
        private String Code;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String requestId) {
            RequestId = requestId;
        }

        public String getBizId() {
            return BizId;
        }

        public void setBizId(String bizId) {
            BizId = bizId;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

    }

}
