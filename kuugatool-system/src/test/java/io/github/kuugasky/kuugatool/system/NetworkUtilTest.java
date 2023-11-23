package io.github.kuugasky.kuugatool.system;

import org.junit.jupiter.api.Test;

import javax.management.MalformedObjectNameException;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

public class NetworkUtilTest {

    @Test
    public void getLocalIpAddr() {
        System.out.printf("本地ip地址:%s%n", NetworkUtil.getLocalIpAddr());
    }

    @Test
    public void getLocalNetworkCardIp() {
        // 外网IP
        System.out.println(NetworkUtil.getLocalNetworkCardIp());
        System.out.println(NetworkUtil.getLocalNetworkCardIP());
    }

    @Test
    public void test() throws SocketException {
        // 获得本机的所有网络接口
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();
            // 获得与该网络接口绑定的 IP 地址，一般只有一个
            Enumeration<InetAddress> addresses = nif.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress inetAddress = addresses.nextElement();
                if (inetAddress instanceof Inet4Address) { // 只关心 IPv4 地址
                    System.out.println("网卡接口名称：" + nif.getName());
                    System.out.println("网卡接口显示名称：" + nif.getDisplayName());
                    System.out.println("网卡父接口名称：" + nif.getParent());
                    System.out.println("网卡子接口名称：" + nif.getSubInterfaces());
                    System.out.println("网卡硬件地址：" + Arrays.toString(nif.getHardwareAddress()));
                    System.out.println("网卡下标：" + nif.getIndex());
                    System.out.println("网卡Inet地址：" + nif.getInetAddresses());
                    System.out.println("网卡接口地址：" + nif.getInterfaceAddresses());
                    System.out.println("网卡MTU名称：" + nif.getMTU());
                    System.out.println("获得主机地址：" + inetAddress.getHostAddress());
                    System.out.println("获得主机名：" + inetAddress.getHostName());
                    System.out.println("Inet地址：" + Arrays.toString(inetAddress.getAddress()));
                    System.out.println("获得主机地址：" + inetAddress.getHostAddress());
                    System.out.println("获取规范主机名：" + inetAddress.getCanonicalHostName());
                    System.out.println();
                }
            }
        }
    }

    @Test
    public void normalizeHostAddress() throws UnknownHostException {
        // 获取本地主机
        InetAddress ia = InetAddress.getLocalHost();
        System.out.println(NetworkUtil.normalizeHostAddress(ia));
        System.out.println(NetworkUtil.getLocalIpAddr());
    }

    @Test
    public void printNetwork() throws SocketException {
        // write your code here
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            System.out.println("设备在操作系统中显示的名称: " + networkInterface.getDisplayName());
            System.out.println("网络设备在操作系统中的名称: " + networkInterface.getName());
            System.out.println("网络接口是否已经开启并正常工作: " + networkInterface.isUp());
            System.out.println("网络接口的最大传输单元(MTU): " + networkInterface.getMTU());
            System.out.println("网络接口是是否是环回接口: " + networkInterface.isLoopback());
            Enumeration<InetAddress> inetAddressesList = networkInterface.getInetAddresses();
            System.out.print("网络接口的硬件地址(MAC地址): ");
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            if (hardwareAddress != null && hardwareAddress.length > 0) {
                System.out.println(bytesToHexMac(hardwareAddress));
            } else {
                System.out.println(Arrays.toString(networkInterface.getHardwareAddress()));
            }
            while (inetAddressesList.hasMoreElements()) {
                InetAddress address = inetAddressesList.nextElement();
                System.out.println("主机地址: " + address.getHostAddress());
            }
            System.out.println("=============分隔=============");
        }
    }

    public static String bytesToHexMac(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            buf.append(String.format("%02x", bytes[i] & 0xff));
            if (i != bytes.length - 1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }

    @Test
    public void getLocalPort() throws MalformedObjectNameException {
        System.out.println(NetworkUtil.getLocalPort());
    }

    @Test
    public void getIpByHost() throws UnknownHostException {
        System.out.println(NetworkUtil.getIpByHost("www.baidu.com"));
        System.out.println(NetworkUtil.getIpByHost("http://www.baidu.com"));
        System.out.println(NetworkUtil.getIpByHost("httpsx://www.baidu.com"));
        System.out.println(NetworkUtil.getIpByHost("kfang.com"));
        System.out.println(NetworkUtil.getIpByHost("https://Kuuga.top"));
        System.out.println(NetworkUtil.getIpByHost("https://hyug2a.top"));
    }

}