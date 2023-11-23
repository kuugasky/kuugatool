package io.github.kuugasky.design.structural.adapter.demo1.aproduct;

import io.github.kuugasky.design.structural.adapter.demo1.MediaAdapter;
import io.github.kuugasky.design.structural.adapter.demo1.bproduct.AdvancedMediaPlayer;

/**
 * AudioPlayer-A类产品
 * <p>
 * 音乐播放器，实现了播放接口<br>
 * 正常情况下{@link AudioPlayer}只能播放mp3，但是现在要让这个类也能播放mp4和vlc，同时该类又不能再去实现高级媒体播放器接口{@link AdvancedMediaPlayer}<br>
 * 那么就增加一个适配器{@link MediaAdapter}，在play方法中通过适配器来适配播放其他格式的文件。<br>
 * 注意：适配器{@link MediaAdapter}也跟{@link AudioPlayer}一样实现了MediaPlayer接口，且在内部引入了{@link AdvancedMediaPlayer}来创建不同的高级播放器，重写了{@link AudioPlayer#play(String, String)}方法。
 *
 * @author kuuga
 * @since 2023/6/8-06-08 13:35
 */
public class AudioPlayer implements MediaPlayer {

    @Override
    public void play(String audioType, String fileName) {
        // 播放 mp3 音乐文件的内置支持
        if (MP3.equalsIgnoreCase(audioType)) {
            System.out.println("Playing mp3 file. Name: " + fileName);
        }
        // mediaAdapter 提供了播放其他文件格式的支持
        else if (VLC.equalsIgnoreCase(audioType) || MP4.equalsIgnoreCase(audioType)) {
            MediaAdapter mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }

}
