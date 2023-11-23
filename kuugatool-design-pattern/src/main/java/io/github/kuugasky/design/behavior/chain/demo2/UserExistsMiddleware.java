package io.github.kuugasky.design.behavior.chain.demo2;

/**
 * UserExistsMiddleware
 * <p>
 * 检查用户登录信息
 *
 * @author kuuga
 * @since 2023/6/5-06-05 13:39
 */
public class UserExistsMiddleware extends Middleware {
    private final Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(email, password);
    }
}
