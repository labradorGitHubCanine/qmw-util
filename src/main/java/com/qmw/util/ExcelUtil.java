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
import org.apache.poi.ss.usermodel.DateUtil;
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
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
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
     * @param stream     文件流
     * @param headRowNum 表头从第几行开始
     * @return Map
     */
    public static LinkedHashMap<String, List<LinkedHashMap<String, String>>> readAsMap(InputStream stream, int headRowNum) {
        Objects.requireNonNull(stream);
        DataListener listener = new DataListener();
        EasyExcel.read(stream, listener).headRowNumber(headRowNum).doReadAll();
        return listener.result();
    }

    public static LinkedHashMap<String, List<LinkedHashMap<String, String>>> readAsMap(InputStream stream) {
        return readAsMap(stream, 1);
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
//            headMap.forEach((index, value) -> {
//                map.put(value, StringUtil.ifEmptyThen(data.get(index), ""));
//            });
            data.forEach((key, value) -> map.put(headMap.get(key), StringUtil.ifEmptyThen(value, "")));
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

    /**
     * 将某一行复制并插入到下一行
     *
     * @param sheet Sheet对象
     * @param row   行号，从0开始
     * @param size  复制多少次
     */
    public static void copyRow(Sheet sheet, int row, int size) {
        if (row < 0)
            throw new CustomException("row必须大于等于0");
        if (size <= 0 || sheet.getLastRowNum() < row)
            return;
        // 预存行高
        List<Short> rowHeights = new ArrayList<>();
        short defaultHeight = sheet.getDefaultRowHeight();
        for (int i = row; i <= sheet.getLastRowNum(); i++) {
            Row sheetRow = sheet.getRow(i);
            rowHeights.add(sheetRow == null ? defaultHeight : sheetRow.getHeight());
        }
        for (int i = 0; i < size; i++)
            rowHeights.add(1, rowHeights.get(0));

        // 移动
        if (sheet.getLastRowNum() > row) // 如果要复制的行就是最后一行，则不需要移动，直接向下复制即可
            sheet.shiftRows(row + 1, sheet.getLastRowNum(), size);
        // 插入预存的行高
        for (int i = row; i <= sheet.getLastRowNum(); i++) {
            Row sheetRow = sheet.getRow(i);
            if (sheetRow == null)
                sheetRow = sheet.createRow(i);
            sheetRow.setHeight(rowHeights.get(i - row));
        }
        // 复制样式和文字
        for (int i = 0; i < sheet.getRow(row).getLastCellNum(); i++) {
            Cell cell1 = sheet.getRow(row).getCell(i);
            if (cell1 == null) continue;
            for (int j = 1; j <= size; j++) {
                Row row1 = sheet.getRow(row + j);
                if (row1 == null)
                    row1 = sheet.createRow(row + j);
                Cell cell2 = row1.createCell(i);
                cell2.setCellStyle(cell1.getCellStyle());
                cell2.setCellValue(getCellValue(cell1));
            }
        }
    }

    /**
     * 解析单元格内容
     *
     * @param cell 要解析的单元格对象
     * @return 单元格中的内容
     */
    public static String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        // 判断是否日期格式
        if (cell.getCellTypeEnum() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            long date = cell.getDateCellValue().getTime();
            if ((date + DateUtil.DAY_MILLISECONDS / 3) % DateUtil.DAY_MILLISECONDS == 0) // 是0点则转化为yyyy-MM-dd
                return new Date(date).toString();
            else // 不是0点则转化为yyyy-MM-dd HH:mm:ss
                return new Timestamp(date).toString();
        } else {
            // 非日期格式直接解析为string
            return new DataFormatter().formatCellValue(cell).trim();
        }
    }

}
