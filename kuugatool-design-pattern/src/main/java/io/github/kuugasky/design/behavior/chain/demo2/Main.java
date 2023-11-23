package io.github.kuugasky.design.behavior.chain.demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/5-06-05 13:40
 */
public class Main {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // 所有检查节点都是链接的。客户端可以使用相同的组件构建各种链。
        Middleware middleware = Middleware.link(
                // 检查请求数量限制
                new ThrottlingMiddleware(2),
                // 检查用户登录信息
                new UserExistsMiddleware(server),
                // 检查用户角色
                new RoleCheckMiddleware()
        );

        // 服务器从客户端代码中获取一个链。
        // 将客户端定制好的校验责任链传递给server
        server.setMiddleware(middleware);
    }

    public static void main(String[] args) throws IOException {
        init();

        boolean success;
        do {
            System.out.print("Enter email: ");
            // 获取输入邮箱
            String email = READER.readLine();
            System.out.print("Input password: ");
            // 获取输入密码
            String password = READER.readLine();
            // 登陆操作
            success = server.logIn(email, password);
        } while (!success);
    }

}
