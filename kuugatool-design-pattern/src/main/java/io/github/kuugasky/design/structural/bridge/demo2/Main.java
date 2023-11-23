package io.github.kuugasky.design.structural.bridge.demo2;

import io.github.kuugasky.design.structural.bridge.demo2.device.Device;
import io.github.kuugasky.design.structural.bridge.demo2.device.Radio;
import io.github.kuugasky.design.structural.bridge.demo2.device.Tv;
import io.github.kuugasky.design.structural.bridge.demo2.remote.AdvancedRemote;
import io.github.kuugasky.design.structural.bridge.demo2.remote.BasicRemote;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:40
 */
public class Main {
    public static void main(String[] args) {
        testDevice(new Tv());
        testDevice(new Radio());
    }

    public static void testDevice(Device device) {
        System.out.println("Tests with basic remote.");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }
}