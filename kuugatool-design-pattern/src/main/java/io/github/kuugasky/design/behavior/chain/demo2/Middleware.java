package io.github.kuugasky.design.behavior.chain.demo2;

/**
 * Middleware
 * <p>
 * 基础验证接口
 *
 * @author kuuga
 * @since 2023/6/5-06-05 13:21
 */
public abstract class Middleware {

    private Middleware next;

    /**
     * 构建中间件对象链。
     * <p>
     * 将需要执行的链节点进行连接。
     */
    public static Middleware link(Middleware first, Middleware... chain) {
        // 组装责任链节点
        Middleware head = first;
        for (Middleware nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    /**
     * 子类将通过具体检查实现此方法。
     */
    public abstract boolean check(String email, String password);

    /**
     * 运行检查链中的下一个对象，或者如果我们在链中的最后一个对象中，则结束遍历。
     */
    protected boolean checkNext(String email, String password) {
        // 检查是否有下一个链节点，没则结束，有则继续往下执行
        if (next == null) {
            return true;
        }
        return next.check(email, password);
    }

}
