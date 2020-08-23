package com.qmw.entity;

/**
 * 视频信息
 *
 * @author qmw
 * @since 1.06
 */
public class VideoInfo {

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

    @Override
    public String toString() {
        return "VideoInfo{" +
                "format='" + format + '\'' +
                ", duration=" + duration +
                ", width=" + width +
                ", height=" + height +
                ", size=" + size +
                '}';
    }

}
