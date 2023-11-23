package io.github.kuugasky.design.structural.flyweight.demo2;

import io.github.kuugasky.design.structural.flyweight.demo2.product.BlackGoChessPiece;
import io.github.kuugasky.design.structural.flyweight.demo2.product.GoChessPiece;
import io.github.kuugasky.design.structural.flyweight.demo2.product.WhiteGoChessPiece;

import java.util.Hashtable;

/**
 * IgoChessmanFactory
 * <p>
 * 围棋棋子工厂类：享元工厂类，使用单例模式进行设计
 *
 * @author kuuga
 * @since 2023/6/6-06-06 09:32
 */
public final class GoChessPieceFlyweightFactory {

    /**
     * 饿汉式单例模式-享元池
     */
    private static final GoChessPieceFlyweightFactory INSTANCE = new GoChessPieceFlyweightFactory();
    /**
     * 使用Hashtable来存储享元对象，充当享元池
     */
    private final Hashtable<String, Object> flyweightPool = new Hashtable<>();

    private GoChessPieceFlyweightFactory() {
        GoChessPiece black, white;
        black = new BlackGoChessPiece();
        flyweightPool.put("黑", black);
        white = new WhiteGoChessPiece();
        flyweightPool.put("白", white);
    }

    /**
     * 返回享元工厂类的唯一实例
     *
     * @return IgoChessmanFactory
     */
    public static GoChessPieceFlyweightFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 通过key来获取存储在Hashtable中的享元对象
     *
     * @param color String
     * @return IgoChessman
     */
    public GoChessPiece getGoChessPiece(String color) {
        return (GoChessPiece) flyweightPool.get(color);
    }
}
