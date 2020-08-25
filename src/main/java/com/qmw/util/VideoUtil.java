package com.qmw.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 视频解析工具类
 *
 * @author qmw
 * @since 1.06
 */
public class VideoUtil {

    public static Info getInfo(File file) {
        MultimediaInfo multimediaInfo;
        try {
            multimediaInfo = new Encoder().getInfo(file);
        } catch (EncoderException e) {
            e.printStackTrace();
            throw new RuntimeException("视频信息读取失败");
        }
        Info info = new Info();
        info.setDuration(multimediaInfo.getDuration());
        info.setFormat(multimediaInfo.getFormat());
        info.setHeight(multimediaInfo.getVideo().getSize().getHeight());
        info.setWidth(multimediaInfo.getVideo().getSize().getWidth());
        info.setSize(file.length());
        return info;
    }

    public static Info getInfo(MultipartFile multipartFile) {
        File file = null;
        try {
            // 创建临时文件
            file = File.createTempFile(UUID.randomUUID() + "-", "");
            multipartFile.transferTo(file);
            return getInfo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("视频信息读取失败");
        } finally {
            if (file != null && file.exists())
                file.delete(); // 删除文件
        }
    }

    public static class Info {

        private String format; // 格式
        private long duration; // 时长，毫秒
        private int width; // 宽度
        private int height; // 高度
        private long size; // 视频大小

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

    }

}
