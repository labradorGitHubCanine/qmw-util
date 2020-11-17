package com.qmw.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.qmw.exception.CustomException;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
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
     * 将一个map写入到excel中并返回给前端
     *
     * @param map      map的key是sheetName，value是内容
     * @param response response
     * @param fileName 文件名
     */
    public static void download(Map<String, List<? extends Map<String, ?>>> map, HttpServletResponse response, String fileName) {
        try {
            WriteCellStyle headStyle = new WriteCellStyle(); // 重写头部样式
            headStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex()); // 白色背景
            headStyle.setWrapped(false); // 不自动换行
            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12); // 12号字
            headStyle.setWriteFont(headFont);

            fileName = URLEncoder.encode(StringUtil.ifEmptyThen(fileName, UUID.randomUUID().toString()) + ".xlsx", StandardCharsets.UTF_8.name());
            response.setContentType("application/x-download");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 允许设置Content-Disposition
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            ExcelWriter writer = EasyExcel.write("C:\\Users\\12334\\Desktop\\" + fileName)
            ExcelWriter writer = EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(new HorizontalCellStyleStrategy(headStyle, new WriteCellStyle())) // 注册自定义样式
//                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动列宽
                    .build();

            if (map != null) {
                int a = 0;
                for (Map.Entry<String, List<? extends Map<String, ?>>> e : map.entrySet()) {
                    String sheetName = e.getKey();
                    List<? extends Map<String, ?>> list = e.getValue();
                    List<List<String>> head = new ArrayList<>(); // 表头
                    List<List<Object>> data = new ArrayList<>(); // 内容
                    if (list != null && !list.isEmpty()) {
                        // 取所有表头的并集
                        LinkedHashSet<String> set = new LinkedHashSet<>();
                        list.forEach(i -> set.addAll(i.keySet()));
                        set.forEach(i -> head.add(Collections.singletonList(i))); // 单级表头用单元素数组
                        set.clear(); // 释放内存
                        // 写入内容
                        list.forEach(i -> {
                            List<Object> row = new ArrayList<>();
                            head.forEach(j -> {
                                Object value = i.get(j.get(0));
                                if (value instanceof Number) // 防止变成科学计数
                                    value = new BigDecimal(value.toString()).toPlainString();
                                else if (value instanceof Date) // 不转换会报错
                                    value = value.toString();
                                else if (value instanceof Timestamp) // 不转换会报错
                                    value = value.toString();
                                row.add(value);
                            });
                            data.add(row);
                        });
                    }
                    // 写入sheet中
                    WriteSheet writeSheet = EasyExcel.writerSheet(a, sheetName).head(head).build();
                    writer.write(data, writeSheet);
                    a++;
                }
            }
            writer.finish();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("文件写入失败！");
        }
    }

    public static void download(Map<String, List<? extends Map<String, ?>>> map, HttpServletResponse response) {
        download(map, response, null);
    }

    public static void download(List<? extends Map<String, ?>> list, HttpServletResponse response, String fileName) {
        download(new HashMap<String, List<? extends Map<String, ?>>>() {{
            put("sheet1", list);
        }}, response, fileName);
    }

    public static void download(List<? extends Map<String, ?>> list, HttpServletResponse response) {
        download(new HashMap<String, List<? extends Map<String, ?>>>() {{
            put("sheet1", list);
        }}, response, null);
    }

    /**
     * 读取第一个sheet，并放入一个list中返回
     *
     * @param stream        文件流
     * @param headRowNumber 表头从第几行开始
     * @return List
     */
    public static List<LinkedHashMap<String, String>> readAsList(InputStream stream, int headRowNumber) {
        Objects.requireNonNull(stream);
        DataListener listener = new DataListener();
        EasyExcel.read(stream, listener).sheet().headRowNumber(headRowNumber).doRead();
        return listener.result().values().iterator().next();
    }

    public static List<LinkedHashMap<String, String>> readAsList(InputStream stream) {
        return readAsList(stream, 1);
    }

    /**
     * 读取所有的sheet，并放入一个map中返回，map的key为sheetName
     *
     * @param stream 文件流
     * @return Map
     */
    public static LinkedHashMap<String, List<LinkedHashMap<String, String>>> readAsMap(InputStream stream) {
        Objects.requireNonNull(stream);
        DataListener listener = new DataListener();
        EasyExcel.read(stream, listener).doReadAll();
        return listener.result();
    }

    private static class DataListener extends AnalysisEventListener<Map<Integer, String>> {

        private final LinkedHashMap<String, List<LinkedHashMap<String, String>>> map = new LinkedHashMap<>(); // sheetName - list
        private final List<LinkedHashMap<String, String>> list = new ArrayList<>(); // list
        private Map<Integer, String> headMap;

        public LinkedHashMap<String, List<LinkedHashMap<String, String>>> result() {
            return this.map;
        }

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
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

        // 每读完一个sheet都会执行此方法
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            map.put(context.readSheetHolder().getSheetName(), new ArrayList<>(list));
            list.clear();
        }
    }

}
