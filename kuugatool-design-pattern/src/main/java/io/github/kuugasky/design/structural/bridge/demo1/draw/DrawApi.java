package io.github.kuugasky.design.structural.bridge.demo1.draw;

/**
 * BridgePattern
 * <p>
 * 桥接接口-画图API
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:23
 */
public interface DrawApi {

    /**
     * 画圆形
     *
     * @param radius 半径
     * @param x      横向坐标点
     * @param y      纵向坐标点
     */
    void drawCircle(int radius, int x, int y);

}
