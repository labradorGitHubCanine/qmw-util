package com.qmw.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.qmw.entity.CellInfo;
import com.qmw.exception.CustomException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

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
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            ExcelWriter writer = EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(new HorizontalCellStyleStrategy(headStyle, new WriteCellStyle()))
                    .build();

            if (map != null) {
                int a = 0;
                for (Map.Entry<String, List<? extends Map<String, ?>>> e : map.entrySet()) {
                    String sheetName = e.getKey();
                    List<? extends Map<String, ?>> list = e.getValue();
                    List<List<String>> head = new ArrayList<>(); // 表头
                    List<List<String>> data = new ArrayList<>(); // 内容
                    if (list != null && !list.isEmpty()) {
                        // 取所有表头的并集
                        LinkedHashSet<String> set = new LinkedHashSet<>();
                        list.forEach(i -> set.addAll(i.keySet()));
                        set.forEach(i -> head.add(Collections.singletonList(i))); // 单级表头用单元素数组
                        set.clear(); // 释放内存
                        // 写入内容
                        list.forEach(i -> {
                            List<String> row = new ArrayList<>();
                            head.forEach(j -> {
                                Object value = i.get(j.get(0));
                                if (value instanceof Number) // 防止变成科学计数
                                    value = new BigDecimal(value.toString()).toPlainString();
                                row.add(value == null ? "" : value.toString());
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
     * @param stream     文件流
     * @param headRowNum 表头从第几行开始
     * @return List
     */
    public static List<LinkedHashMap<String, String>> readAsList(InputStream stream, int headRowNum) {
        Objects.requireNonNull(stream);
        DataListener listener = new DataListener();
        EasyExcel.read(stream, listener).sheet().headRowNumber(headRowNum).doRead();
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

    public static Map<String, List<List<CellInfo>>> readAsMatrix(MultipartFile file) {
        Workbook workbook;
        String type = FileUtil.getFileType(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            switch (type) {
                case "xls":
                    workbook = new HSSFWorkbook(file.getInputStream());
                    break;
                case "xlsx":
                    workbook = new XSSFWorkbook(file.getInputStream());
                    break;
                default:
                    throw new CustomException("不支持的文件类型:" + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("文件解析失败");
        }
        Map<String, List<List<CellInfo>>> map = new LinkedHashMap<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            // 先明确它是一个最大n*n的excel，因为其中有些没内容的cell会=null, n的下标从0开始
            int x = 0, y = sheet.getLastRowNum();
            for (int j = 0; j <= y; j++) {
                Row row = sheet.getRow(j);
                if (row != null)
                    x = Math.max(x, row.getLastCellNum() - 1);
            }
            List<List<CellInfo>> list = new ArrayList<>();
            for (int j = 0; j <= y; j++) {
                Row row = sheet.getRow(j);
                List<CellInfo> rowList = new ArrayList<>();
                for (int k = 0; k <= x; k++) {
                    CellInfo cellInfo = new CellInfo();
                    if (row != null) {
                        Cell cell = row.getCell(k);
                        cellInfo.setValue(getCellValue(cell));
                        cellInfo.setHeight(row.getHeightInPoints());
                    } else {
                        cellInfo.setHeight(sheet.getDefaultRowHeightInPoints());
                    }
                    cellInfo.setWidth(sheet.getColumnWidthInPixels(k));
                    rowList.add(cellInfo);
                }
                list.add(rowList);
            }
            // 设置合并行合并列
            for (CellRangeAddress address : sheet.getMergedRegions()) {
                int firstRow = address.getFirstRow();
                int lastRow = address.getLastRow();
                int firstColumn = address.getFirstColumn();
                int lastColumn = address.getLastColumn();
                for (int j = 0; j < list.size(); j++) {
                    for (int k = 0; k < list.get(j).size(); k++) {
                        if (j >= firstRow && j <= lastRow && k >= firstColumn && k <= lastColumn) {
                            CellInfo info = list.get(j).get(k);
                            if (j == firstRow && k == firstColumn)
                                info.setRowspan(lastRow - firstRow + 1).setColspan(lastColumn - firstColumn + 1);
                            else
                                info.setExists(false);
                        }
                    }
                }
            }
            map.put(sheet.getSheetName(), list);
        }
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
                return BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros().toPlainString();
            default:
                return "";
        }
    }

}
