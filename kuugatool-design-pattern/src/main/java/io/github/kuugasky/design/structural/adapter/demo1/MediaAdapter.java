package io.github.kuugasky.design.structural.adapter.demo1;

import io.github.kuugasky.design.structural.adapter.demo1.aproduct.MediaPlayer;
import io.github.kuugasky.design.structural.adapter.demo1.bproduct.AdvancedMediaPlayer;
import io.github.kuugasky.design.structural.adapter.demo1.bproduct.Mp4Player;
import io.github.kuugasky.design.structural.adapter.demo1.bproduct.VlcPlayer;

/**
 * MediaAdapter
 * <p>
 * 适配器-B类产品适配器（实现A类产品接口，并用B类产品来实现A类产品接口方法逻辑）
 *
 * @author kuuga
 * @since 2023/6/8-06-08 13:33
 */
public class MediaAdapter implements MediaPlayer {

    private AdvancedMediaPlayer advancedMusicPlayer;

    public MediaAdapter(String audioType) {
        if (VLC.equalsIgnoreCase(audioType)) {
            advancedMusicPlayer = new VlcPlayer();
        } else if (MP4.equalsIgnoreCase(audioType)) {
            advancedMusicPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if (VLC.equalsIgnoreCase(audioType)) {
            advancedMusicPlayer.playVlc(fileName);
        } else if (MP4.equalsIgnoreCase(audioType)) {
            advancedMusicPlayer.playMp4(fileName);
        }
    }

}
