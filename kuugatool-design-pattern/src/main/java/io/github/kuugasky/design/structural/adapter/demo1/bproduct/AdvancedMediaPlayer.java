package io.github.kuugasky.design.structural.adapter.demo1.bproduct;

/**
 * AdvancedMediaPlayer
 * <p>
 * 高级媒体播放器-B类产品接口
 *
 * @author kuuga
 * @since 2023/6/8-06-08 13:26
 */
public interface AdvancedMediaPlayer {

    /**
     * 播放vlc格式文件
     *
     * @param fileName 文件名
     */
    void playVlc(String fileName);

    /**
     * 播放mp4格式文件
     *
     * @param fileName 文件名
     */
    void playMp4(String fileName);

}
