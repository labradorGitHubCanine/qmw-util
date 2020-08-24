package com.qmw.util;

import com.qmw.entity.VideoInfo;
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

    public static VideoInfo getInfo(File file) {
        MultimediaInfo multimediaInfo;
        try {
            multimediaInfo = new Encoder().getInfo(file);
        } catch (EncoderException e) {
            e.printStackTrace();
            throw new RuntimeException("视频信息读取失败");
        }
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setDuration(multimediaInfo.getDuration());
        videoInfo.setFormat(multimediaInfo.getFormat());
        videoInfo.setHeight(multimediaInfo.getVideo().getSize().getHeight());
        videoInfo.setWidth(multimediaInfo.getVideo().getSize().getWidth());
        videoInfo.setSize(file.length());
        return videoInfo;
    }

    public static VideoInfo getInfo(MultipartFile multipartFile) {
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID() + "-", "");
            multipartFile.transferTo(file);
            return getInfo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("视频信息读取失败");
        } finally {
            if (file != null && file.exists())
                file.delete();
        }
    }

}
