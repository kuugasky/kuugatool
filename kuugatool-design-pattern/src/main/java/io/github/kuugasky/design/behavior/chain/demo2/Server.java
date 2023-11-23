package io.github.kuugasky.design.behavior.chain.demo2;

import java.util.HashMap;
import java.util.Map;

/**
 * Server
 * <p>
 * 授权目标
 *
 * @author kuuga
 * @since 2023/6/5-06-05 13:40
 */
public class Server {

    private final Map<String, String> users = new HashMap<>();
    private Middleware middleware;

    /**
     * 客户端将一个对象链传递给服务器。这提高了灵活性，并使测试服务器类变得更容易。
     */
    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    /**
     * 服务器从客户端获取电子邮件和密码，并将授权请求发送到链。
     */
    public boolean logIn(String email, String password) {
        if (middleware.check(email, password)) {
            System.out.println("授权已成功!");

            // 在这里为授权用户做一些有用的事情。

            return true;
        }
        return false;
    }

    public void register(String email, String password) {
        users.put(email, password);
    }

    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password);
    }

}