package com.qmw.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.qmw.util.StringUtil.LINE_SEPARATOR;

/**
 * 请求参数打印工具类
 */
public class ParamsPrinter {

    public static void print(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        List<String> paramNames = Collections.list(request.getParameterNames());
        List<String> headerNames = Collections.list(request.getHeaderNames());

        Collections.sort(paramNames);
        Collections.sort(headerNames);
        StringBuilder builder = new StringBuilder(); // 拼接成字符串一次性打印出，防止多个请求的同时打印造成混杂

        // 打印参数
        builder.append("[").append(method).append("]").append("[").append(uri).append("]params: ").append(paramNames.isEmpty() ? "no param" : "").append(LINE_SEPARATOR);
        for (String name : paramNames) {
            String[] value = request.getParameterValues(name);
            builder.append("\t").append(name).append(": ").append(value.length == 1 ? value[0] : Arrays.toString(value)).append(LINE_SEPARATOR);
        }
        // 打印请求头
        builder.append("[").append(method).append("]").append("[").append(uri).append("]headers: ").append(headerNames.isEmpty() ? "no header" : "").append(LINE_SEPARATOR);
        for (String name : headerNames) {
            List<String> value = Collections.list(request.getHeaders(name));
            builder.append("\t").append(name).append(": ").append(value.size() == 1 ? value.get(0) : value).append(LINE_SEPARATOR);
        }
        System.out.println(builder);
    }

}
