package io.github.kuugasky.design.behavior.chain.demo2;

/**
 * RoleCheckMiddleware
 * <p>
 * 检查用户角色
 *
 * @author kuuga
 * @since 2023/6/5-06-05 13:39
 */
public class RoleCheckMiddleware extends Middleware {
    public boolean check(String email, String password) {
        if (email.equals("admin@example.com")) {
            System.out.println("Hello, admin!");
            return true;
        }
        System.out.println("Hello, user!");
        return checkNext(email, password);
    }
}
