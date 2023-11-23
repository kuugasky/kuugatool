// package cn.kuugatool.extra.ssh;
//
// import ch.ethz.ssh2.ChannelCondition;
// import ch.ethz.ssh2.Connection;
// import ch.ethz.ssh2.Session;
//
// import java.io.IOException;
// import java.io.PrintWriter;
//
// /**
//  * RemoteExecuteCommand
//  *
//  * @author kuuga
//  * @since 2022/8/12 17:57
//  */
// public class RemoteExecuteCommand {
//
//     // 字符编码默认是utf-8
//     private static String DEFAULTCHART = "UTF-8";
//     private Connection conn;
//     private String ip;
//     private String userName;
//     private String userPwd;
//
//     public void run(String cmd) {                // 运行时直接调用的主方法，传入指令
//         String execute = this.execute(cmd);    // 可以将指令封装为一个枚举类以调用
//         System.out.println(execute);     // 如果本机重启大可不必拿返回值，反正你都自爆了
//     }
//
//     public RemoteExecuteCommand() {
//
//     }
//
//     public static void main(String[] args) {
//         RemoteExecuteCommand remoteExecuteCommand = new RemoteExecuteCommand();
//         Boolean login = remoteExecuteCommand.login();
//         System.out.println(login);
//         remoteExecuteCommand.run("54");
//         remoteExecuteCommand.run("ls");
//     }
//
//     public Boolean login() {          // 执行登录的方法，返回boolean
//         boolean flg = false;
//
//         try {
//             this.ip = "10.210.10.81";
//             this.userName = "testlog_agent";
//             this.userPwd = "KF3l3YODy1UGQUbk";
//             conn = new Connection(ip, 2222);
//             conn.connect();
//             flg = conn.authenticateWithPassword(userName, userPwd);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return flg;
//     }
//
//     public String execute(String cmd) {     // 执行命令的方法
//         String result = "ok";
//         try {
//             if (login()) {          // 如果登录成功
//                 System.out.println("登录成功:");
//                 Session session = conn.openSession();
//                 session.requestPTY("bash");
//                 session.startShell();
//                 PrintWriter out = new PrintWriter(session.getStdin());
//                 out.println(cmd);
//                 out.flush();
//                 out.println("exit");
//                 out.close();
//                 session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS,
//                         60000);
//                 System.out.println("exec has finished!");
//                 session.close();
//                 conn.close();
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return result;
//     }
//
// }
