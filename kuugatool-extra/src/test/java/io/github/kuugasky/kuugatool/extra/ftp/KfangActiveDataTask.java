package io.github.kuugasky.kuugatool.extra.ftp;

import com.jcraft.jsch.ChannelSftp;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaCharConstants;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.enums.SortType;
import io.github.kuugasky.kuugatool.core.exception.IORuntimeException;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * 看房盘客活动数据任务
 *
 * @author kuuga
 * @since 2021/7/15
 */
@Slf4j
public class KfangActiveDataTask {

    private static final String ftpDirPath = "/data/www/kuuga/downloads";
    private static final String saveDirPath = "/Users/kuuga/fsdownload";

    public static void main(String[] args) throws Exception {
        ChannelSftp channelSftp = FtpUtil.connectSftp("10.210.10.81", 2222, "testlog_agent", "KF3l3YODy1UGQUbk");
        downloadActiveData(channelSftp);
        FtpUtil.close(channelSftp);
    }

    public static void downloadActiveData(ChannelSftp channelSftp) throws Exception {
        FtpUtil.exec(channelSftp.getSession(), "172.24.16.49");
        List<String> pwd = FtpUtil.exec(channelSftp.getSession(), "pwd");
        ListUtil.optimize(pwd).forEach(System.out::println);
        // log.info("1.检查当天活动文件excel是否已生成.");
        // String activeDataFileName = getActiveDataFileNameIfExist(channelSftp);
        // if (StringUtil.isEmpty(activeDataFileName)) {
        //     log.info("1-1.当天活动文件excel未生成，生成中...");
        //     FtpUtil.exec(channelSftp.getSession(), "curl -X POST http://127.0.0.1:13210/report/house/extendActivityExport");
        //
        //     log.info("1-2.睡眠10s等待文件生成");
        //     for (int i = 1; i < 11; i++) {
        //         TimeUnit.SECONDS.sleep(1);
        //         log.info("Please wait for [{}] ...", i);
        //     }
        // }
        //
        // activeDataFileName = getActiveDataFileNameIfExist(channelSftp);
        //
        // File locationFile = new File(String.format("%s/%s", saveDirPath, activeDataFileName));
        // log.info("2.文件匹配成功：{}", locationFile.getPath());
        //
        // if (!locationFile.exists()) {
        //     log.info("3.文件开始下载...");
        //     FtpUtil.download(channelSftp, ftpDirPath, saveDirPath, activeDataFileName, activeDataFileName);
        // }
        // log.info("4.打开本地文件目录");
        // exec(String.format("open %s", saveDirPath));
    }

    private static String getActiveDataFileNameIfExist(ChannelSftp channelSftp) {
        List<String> list = FtpUtil.listFileNames(channelSftp, ftpDirPath, SortType.DESC, true);

        String targetFileName = ListUtil.optimize(list).stream()
                .filter(fileName -> fileName.startsWith("拓房活动数据表" + DateUtil.format(DateFormat.yyyyMMdd, DateUtil.now())))
                .findFirst().orElse(null);
        if (StringUtil.isEmpty(targetFileName)) {
            log.warn("目标文件匹配不到.");
            return null;
        }
        return targetFileName;
    }

    /**
     * 执行命令<br>
     * 命令带参数时参数可作为其中一个参数，也可以将命令和参数组合为一个字符串传入
     *
     * @param cmds 命令
     * @return {@link Process}
     */
    public static Process exec(String... cmds) {
        if (ArrayUtil.isEmpty(cmds)) {
            throw new NullPointerException("Command is empty !");
        }

        // 单条命令的情况
        if (1 == cmds.length) {
            final String cmd = cmds[0];
            if (StringUtil.isEmpty(cmd)) {
                throw new NullPointerException("Command is empty !");
            }
            cmds = cmd.split(String.valueOf(KuugaCharConstants.SPACE));
        }

        Process process;
        try {
            process = new ProcessBuilder(cmds).redirectErrorStream(true).start();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return process;
    }

}
