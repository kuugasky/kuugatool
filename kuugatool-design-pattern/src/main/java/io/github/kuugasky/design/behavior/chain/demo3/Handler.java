package io.github.kuugasky.design.behavior.chain.demo3;

/**
 * Handler
 * <p>
 * 责任链模式抽象类
 *
 * @author kuuga
 * @since 2023/6/8-06-08 09:40
 */
public abstract class Handler {
    /**
     * 嵌套自身
     */
    private Handler next;

    /**
     * 构建中间件对象链。
     * <p>
     * 将需要执行的链节点进行连接。
     */
    public static Handler link(Handler first, Handler... chain) {
        // 组装责任链节点
        Handler head = first;
        for (Handler nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    /**
     * 子类将通过具体检查实现此方法。
     */
    public abstract boolean handelRequest(String level, String name);

    /**
     * 运行检查链中的下一个对象，或者如果我们在链中的最后一个对象中，则结束遍历。
     */
    protected boolean checkNext(String level, String name) {
        // 检查是否有下一个链节点，没则结束，有则继续往下执行
        if (next == null) {
            return true;
        }
        return next.handelRequest(level, name);
    }

}
