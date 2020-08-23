package com.qmw.util;

import com.qmw.entity.VideoInfo;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
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
        File file;
        try {
            file = File.createTempFile(
                    UUID.randomUUID().toString(),
                    FileUtil.getFileType(Objects.requireNonNull(multipartFile.getOriginalFilename()))
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建临时文件失败");
        }
        VideoInfo videoInfo = getInfo(file);
//        if (file.exists())
//            file.delete();
        return videoInfo;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\12334\\Desktop\\test.mp4");
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
        System.out.println(getInfo(multipartFile));
    }

}
