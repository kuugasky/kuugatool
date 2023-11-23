package io.github.kuugasky.design.structural.adapter.demo1.bproduct;

/**
 * Mp4Player
 * <p>
 * 高级媒体播放器-MP4-B类产品
 *
 * @author kuuga
 * @since 2023/6/8-06-08 13:33
 */
public class Mp4Player implements AdvancedMediaPlayer {

    @Override
    public void playVlc(String fileName) {
        // 什么也不做
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file. Name: " + fileName);
    }
}

