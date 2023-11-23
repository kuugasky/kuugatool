package io.github.kuugasky.design.behavior.chain.demo2;

/**
 * ThrottlingMiddleware
 * <p>
 * 检查请求数量限制
 *
 * @author kuuga
 * @since 2023/6/5-06-05 13:38
 */
public class ThrottlingMiddleware extends Middleware {
    /**
     * 每分钟请求数
     */
    private final int requestPerMinute;
    private int request;
    private long currentTime;

    public ThrottlingMiddleware(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * 请不要将checkNext()调用插入此方法的开头和结尾。
     * <p>
     * 这比所有中间件对象的简单循环更具灵活性。例如，链的元素可以通过在所有其他检查后运行其检查来更改检查顺序。
     */
    public boolean check(String email, String password) {
        if (System.currentTimeMillis() > currentTime + 60_000) {
            request = 0;
            currentTime = System.currentTimeMillis();
        }

        request++;

        if (request > requestPerMinute) {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop();
        }
        return checkNext(email, password);
    }
}
