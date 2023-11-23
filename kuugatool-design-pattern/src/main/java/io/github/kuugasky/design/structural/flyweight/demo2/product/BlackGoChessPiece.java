package io.github.kuugasky.design.structural.flyweight.demo2.product;

/**
 * WhiteIgoChessman
 * <p>
 * 享元具体类-黑色棋子类
 *
 * @author kuuga
 * @since 2023/6/6-06-06 09:30
 */
public class BlackGoChessPiece extends GoChessPiece {
    /**
     * @return 内部状态
     */
    @Override
    public String getColor() {
        return "黑色";
    }
}
