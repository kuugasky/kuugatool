package io.github.kuugasky.design.structural.adapter.demo1.bproduct;

/**
 * VlcPlayer
 * <p>
 * 高级媒体播放器-VLC-B类产品
 *
 * @author kuuga
 * @since 2023/6/8-06-08 13:32
 */
public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file. Name: " + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        // 什么也不做
    }

}
