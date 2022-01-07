package com.qmw.util;

import com.qmw.exception.CustomException;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoInfo;
import lombok.Data;
import lombok.experimental.Accessors;
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
            throw new CustomException("视频信息读取失败");
        }
        if (multimediaInfo == null)
            throw new CustomException("视频信息读取失败");
        Info info = new Info()
                .setDuration(multimediaInfo.getDuration())
                .setFormat(multimediaInfo.getFormat())
                .setSize(file.length());

        VideoInfo videoInfo = multimediaInfo.getVideo();
        if (videoInfo != null) {
            info.setHeight(videoInfo.getSize().getHeight());
            info.setWidth(videoInfo.getSize().getWidth());
        }
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
            throw new CustomException("视频信息读取失败");
        } finally {
            if (file != null && file.exists())
                file.delete(); // 删除文件
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Info {

        private String format; // 格式
        private long duration; // 时长，
        private int width; // 宽度
        private int height; // 高度
        private long size; // 视频大小

    }

}
