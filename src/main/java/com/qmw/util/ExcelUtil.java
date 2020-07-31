package com.qmw.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qmw.exception.CheckFailedException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * excel工具
 */
public class ExcelUtil {

    /**
     * 将生成的excel返回给web前端
     *
     * @param list     list
     * @param response response
     * @param fileName 文件名，可以为空
     */
    public static void download(List<LinkedHashMap<String, Object>> list, HttpServletResponse response, String fileName) {
        if (StringUtil.isEmpty(fileName))
            fileName = UUID.randomUUID().toString();
        try {
            fileName = URLEncoder.encode(fileName + ".xlsx", StandardCharsets.UTF_8.name());
            List<List<String>> head = new ArrayList<>(); // 表头
            List<List<Object>> data = new ArrayList<>(); // 内容
            if (list != null && !list.isEmpty()) {
                list.get(0).keySet().forEach(i -> head.add(Collections.singletonList(i)));
                list.forEach(i -> {
                    List<Object> l = new ArrayList<>();
                    i.values().forEach(j -> {
                        if (j == null)
                            l.add("");
                        else if (j instanceof BigDecimal)
                            l.add(((BigDecimal) j).toPlainString());
                        else
                            l.add(j.toString());
                    });
                    data.add(l);
                });
            }
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            EasyExcel.write(response.getOutputStream()).head(head).sheet("sheet1").doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件写入失败！");
        }
    }

    /**
     * 将excel解析为List
     *
     * @param file file
     * @return List
     */
    public static List<Map<String, String>> read(MultipartFile file) {
        if (file == null)
            throw new CheckFailedException("请选择文件！");
        DataListener listener = new DataListener();
        try {
            EasyExcel.read(file.getInputStream(), listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CheckFailedException("文件读取失败！");
        }
        return listener.getList();
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
