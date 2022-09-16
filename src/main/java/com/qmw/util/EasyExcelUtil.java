package com.qmw.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.UUID;

public class EasyExcelUtil {

    @SneakyThrows
    public static void save(Collection<?> list, Class<?> clazz, String name, HttpServletResponse response) {
        // 表头样式
        WriteCellStyle headStyle = new WriteCellStyle();
        headStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        headStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE1.getIndex());

        // 内容样式
        WriteCellStyle contentStyle = new WriteCellStyle();
        contentStyle.setWrapped(true);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        ExcelWriterBuilder builder;
        if (response != null) { // 下载文件
            name = StringUtil.ifEmpty(name, UUID.randomUUID().toString()) + ".xlsx";
            response.setContentType("application/x-download");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, StandardCharsets.UTF_8.name()));
            builder = EasyExcel.write(response.getOutputStream(), clazz);
        } else { // 保存本地
            builder = EasyExcel.write(name, clazz);
        }
        builder.registerWriteHandler(new HorizontalCellStyleStrategy(headStyle, contentStyle))
                .sheet("sheet1")
                .doWrite(list);
    }

    public static void save(Collection<?> list, Class<?> clazz, String name) {
        save(list, clazz, name, null);
    }

    public static void download(Collection<?> list, Class<?> clazz, String name, HttpServletResponse response) {
        save(list, clazz, name, response);
    }

    public static void download(Collection<?> list, Class<?> clazz, HttpServletResponse response) {
        save(list, clazz, null, response);
    }

}
