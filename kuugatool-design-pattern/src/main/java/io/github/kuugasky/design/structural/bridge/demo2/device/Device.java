package io.github.kuugasky.design.structural.bridge.demo2.device;

/**
 * Device
 * <p>
 * 所有设备的通用接口
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:37
 */
public interface Device {
    boolean isEnabled();

    void enable();

    void disable();

    int getVolume();

    void setVolume(int percent);

    int getChannel();

    void setChannel(int channel);

    void printStatus();
}
