package com.qmw.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * excel工具
 * 2020-08-01
 *
 * @author qmw
 * @since 1.00
 */
public class ExcelUtil {

    /**
     * 将生成的excel返回给web前端
     *
     * @param list     list
     * @param response response
     * @param fileName 文件名，可以为空
     */
    public static void download(List<? extends Map<String, Object>> list, HttpServletResponse response, String fileName) {
        try {
            List<List<String>> head = new ArrayList<>(); // 表头
            List<List<Object>> data = new ArrayList<>(); // 内容
            if (list != null && !list.isEmpty()) {
                list.get(0).keySet().forEach(i -> head.add(Collections.singletonList(i)));
                list.forEach(i -> {
                    List<Object> row = new ArrayList<>();
                    i.values().forEach(j -> {
                        if (j == null)
                            row.add("");
                        else if (j instanceof BigDecimal)
                            row.add(((BigDecimal) j).toPlainString());
                        else
                            row.add(j.toString());
                    });
                    data.add(row);
                });
            }
            fileName = URLEncoder.encode(StringUtil.ifEmptyThen(fileName, UUID.randomUUID().toString()) + ".xls", StandardCharsets.UTF_8.name());
            response.setContentType("application/x-download");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 允许设置Content-Disposition
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            EasyExcel.write(response.getOutputStream()).sheet("sheet1").head(head).doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件写入失败！");
        }
    }

    /**
     * 将生成的excel返回给web前端
     *
     * @param list     list
     * @param response response
     */
    public static void download(List<? extends Map<String, Object>> list, HttpServletResponse response) {
        download(list, response, "");
    }

    /**
     * 将excel解析为List
     *
     * @param stream        文件流
     * @param headRowNumber 表头开始的行数
     * @return List
     */
    public static List<Map<String, String>> readAsList(InputStream stream, int headRowNumber) {
        Objects.requireNonNull(stream);
        DataListener listener = new DataListener();
        EasyExcel.read(stream, listener).sheet().headRowNumber(headRowNumber).doRead();
        return listener.getList();
    }

    public static List<Map<String, String>> readAsList(InputStream stream) {
        return readAsList(stream, 1);
    }

    private static class DataListener extends AnalysisEventListener<Map<Integer, String>> {

        private final List<Map<String, String>> list = new ArrayList<>();
        private Map<Integer, String> headMap;

        public List<Map<String, String>> getList() {
            return this.list;
        }

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            Map<String, String> map = new HashMap<>();
            data.forEach((key, value) -> {
                if (value != null)
                    map.put(headMap.get(key), value.trim());
            });
            if (!map.isEmpty())
                this.list.add(map);
        }

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            this.headMap = headMap;
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
        }

    }

}
