package io.github.kuugasky.design.structural.bridge.demo2.remote;

/**
 * Remote
 * <p>
 * 所有远程控制器的通用接口
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:39
 */
public interface Remote {
    void power();

    void volumeDown();

    void volumeUp();

    void channelDown();

    void channelUp();
}
