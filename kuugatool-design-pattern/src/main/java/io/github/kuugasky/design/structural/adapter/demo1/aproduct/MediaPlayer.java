package io.github.kuugasky.design.structural.adapter.demo1.aproduct;

/**
 * MediaPlayer
 * <p>
 * 媒体播放器-A类产品接口
 *
 * @author kuuga
 * @since 2023/6/8-06-08 13:25
 */
public interface MediaPlayer {

    String VLC = "vlc";
    String MP3 = "mp3";
    String MP4 = "mp4";

    /**
     * 播放
     *
     * @param audioType 音频类型
     * @param fileName  文件名
     */
    void play(String audioType, String fileName);
}
