package io.github.kuugasky.design.structural.adapter.demo1;

import io.github.kuugasky.design.structural.adapter.demo1.aproduct.AudioPlayer;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/8-06-08 13:35
 */
public class Main {

    public static void main(String[] args) {
        // 想要让 AudioPlayer 播放其他格式的音频文件。
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }

}
