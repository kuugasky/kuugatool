package io.github.kuugasky.design.structural.bridge.demo2.remote;

import io.github.kuugasky.design.structural.bridge.demo2.device.Device;

/**
 * AdvancedRemote
 * <p>
 * 高级远程控制器-桥接类，继承类BasicRemote，同时又新增类mute方法
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:40
 */
public class AdvancedRemote extends BasicRemote {
    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}
