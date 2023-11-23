package io.github.kuugasky.kuugatool.extra.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.enums.SortType;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FtpUtilTest {

    @Test
    public void getConnect() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("10.152.2.216", 22, "huangzeyuan", "kuuga");
        System.out.println(StringUtil.formatString(connect));
        FtpUtil.close(connect);
    }

    @Test
    public void download() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("10.152.2.216", 22, "root", "6h3&.3NR46lN");
        String ftpPath = "/data/www/kuuga/";
        String savePath = "/Users/kuuga/Downloads/";
        FtpUtil.download(connect, ftpPath, savePath, "arthas-boot.jar", true);
        FtpUtil.close(connect);
    }

    @Test
    public void download1() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("10.152.2.216", 22, "root", "6h3&.3NR46lN");
        String ftpPath = "/data/www/kuuga/";
        String savePath = "/Users/kuuga0410/Downloads/";
        FtpUtil.download(connect, ftpPath, savePath, "arthas-boot.jar");
        FtpUtil.close(connect);
    }

    @Test
    public void uploadFile() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("10.152.2.216", 22, "root", "6h3&.3NR46lN");
        String ftpPath = "/data/www/kuuga/";
        String uploadFilePath = "/Users/kuuga0410/Downloads/打印机连接教程.png";
        try {
            FtpUtil.uploadFile(connect, uploadFilePath, ftpPath, "打印机连接教程.png");
            FtpUtil.download(connect, ftpPath, "/Users/kuuga0410/Downloads/", "打印机连接教程.png", "打印机连接教程111.png");
            FtpUtil.close(connect);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void close() {
    }

    @Test
    public void main() {
    }

    @Test
    public void exec() throws Exception {
        Session session = FtpUtil.getSession("106.53.231.216", 22, "root", "kuuga0410@19900513");
        String ftpFileName = "cd /data/www\nls";
        FtpUtil.exec(session, ftpFileName);
    }

    @Test
    public void exec2() throws Exception {
        Session session = FtpUtil.getSession("106.53.231.216", 22, "root", "kuuga0410@19900513");
        FtpUtil.exec(session, "cd /data/www", "ls");
    }

    @Test
    public void fileIsExists() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("106.53.231.216", 22, "root", "kuuga0410@19900513");
        String ftpFileName = "/data/www/file/other/Kuuga.txt";
        System.out.println(FtpUtil.fileIsExists(connect, ftpFileName));
        FtpUtil.close(connect);
    }

    @Test
    public void dirIsExists() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("106.53.231.216", 22, "root", "kuuga0410@19900513");
        String ftpDir = "/data/www/file/other";
        System.out.println(FtpUtil.dirIsExists(connect, ftpDir));
        FtpUtil.close(connect);
    }

    @Test
    public void deleteFile() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("106.53.231.216", 22, "root", "kuuga0410@19900513");
        String ftpFileName = "/data/www/file/other/Kuuga.txt";
        System.out.println(FtpUtil.deleteFile(connect, ftpFileName));
        FtpUtil.close(connect);
    }

    @Test
    public void listFiles() throws Exception {
        ChannelSftp connect = FtpUtil.connectSftp("106.53.231.216", 22, "root", "kuuga0410@19900513");
        List<FileEntry> fileEntries = FtpUtil.listFiles(connect, "/data/www/file/other/", SortType.ASC);

        ListUtil.optimize(fileEntries).forEach(fileEntry -> {
            String name = fileEntry.getName();
            String updateTimeStr = fileEntry.getUpdateTimeStr();
            String sizeStr = fileEntry.getSizeStr();
            String longName = fileEntry.getLongName();
            String format = String.format("name:%s,size:%s,updateTime:%s,longName:%s",
                    name, sizeStr, updateTimeStr, longName);
            System.out.println(format);
        });
    }

    @Test
    void log() throws Exception {
        Session session = FtpUtil.getSession("10.210.10.81", 2222, "testlog_agent", "KF3l3YODy1UGQUbk");
        FtpUtil.exec(session, "54");
    }

    @Test
    void log1() throws Exception {
        Session session = FtpUtil.getSession("106.53.231.216", 22, "root", "kuuga0410@19900513");
        FtpUtil.exec(session, "ls");
        FtpUtil.exec(session, "pwd");
        FtpUtil.exec(session, "tail -f /log/kuuga/blog/normal/normal.2022-08-15.log");
    }

}