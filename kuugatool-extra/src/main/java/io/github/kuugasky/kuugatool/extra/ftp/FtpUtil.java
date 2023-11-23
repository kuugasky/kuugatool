package io.github.kuugasky.kuugatool.extra.ftp;

import com.jcraft.jsch.*;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.enums.SortType;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * FtpUtil
 *
 * @author kuuga
 * @date 2020-08-23 11:38
 */
@Slf4j
public class FtpUtil {

    private static Session sshSession = null;
    private static final String DOUBLE_POINT = "..";
    private static final String POINT = ".";

    /**
     * 连接服务器SftpChannel
     *
     * @param host     ip
     * @param port     端口
     * @param username 用户
     * @param password 密码
     * @return Sftp连接
     */
    public static Session getSession(String host, int port, String username, String password) throws Exception {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            log.info("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            // 开启sshSession链接
            sshSession.connect();
            log.info("Session connected.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("连接服务器session异常。。。。。。。。");
        }
        return sshSession;
    }

    /**
     * 连接服务器SftpChannel
     *
     * @param host     ip
     * @param port     端口
     * @param username 用户
     * @param password 密码
     * @return Sftp连接
     * @throws Exception Exception
     */
    public static ChannelSftp connectSftp(String host, int port, String username, String password) throws Exception {
        ChannelSftp channelSftp;
        try {
            // 获取sshSession
            sshSession = getSession(host, port, username, password);
            // 获取sftp通道
            channelSftp = (ChannelSftp) sshSession.openChannel("sftp");
            log.info("Opening Channel.");
            // 开启
            channelSftp.connect();
            return channelSftp;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("连接服务器sftp channel异常。。。。。。。。");
        }
    }

    public static void download(ChannelSftp channelSftp, String ftpDirPath, String saveDirPath, String fileName) throws Exception {
        download(channelSftp, ftpDirPath, saveDirPath, fileName, fileName, false);
    }

    public static void download(ChannelSftp channelSftp, String ftpDirPath, String saveDirPath, String fileName, boolean cover) throws Exception {
        download(channelSftp, ftpDirPath, saveDirPath, fileName, fileName, cover);
    }

    public static void download(ChannelSftp channelSftp, String ftpDirPath, String saveDirPath, String sourceFileName, String newFileName) throws Exception {
        download(channelSftp, ftpDirPath, saveDirPath, sourceFileName, newFileName, false);
    }

    /**
     * ftp文件下载
     *
     * @param channelSftp    channelSftp
     * @param ftpDirPath     ftp文件目录路径
     * @param saveDirPath    本地保存目录路径
     * @param sourceFileName ftp文件名
     * @param newFileName    本地保存文件名
     * @param cover          是否覆盖本地文件
     * @throws Exception Exception
     */
    public static void download(ChannelSftp channelSftp, String ftpDirPath, String saveDirPath, String sourceFileName, String newFileName, boolean cover) throws Exception {
        if (StringUtil.endsWith(ftpDirPath, KuugaConstants.SLASH)) {
            saveDirPath = StringUtil.removeEnd(saveDirPath, KuugaConstants.SLASH);
        }
        if (!StringUtil.endsWith(saveDirPath, KuugaConstants.SLASH)) {
            saveDirPath += "/";
        }

        channelSftp.cd(ftpDirPath);
        File file = new File(saveDirPath);
        if (!file.exists()) {
            throw new RuntimeException("save directory is not exists");
        }
        if (!file.isDirectory()) {
            throw new RuntimeException("save path is not directory");
        }

        String saveFile = saveDirPath + newFileName;
        File file1 = new File(saveFile);
        if (file1.exists()) {
            if (cover) {
                boolean delete = file1.delete();
                log.info("[{}] is exists, delete it:{}.", saveFile, delete);
            } else {
                String[] split = newFileName.split("\\.");
                String newFilePath = saveDirPath + split[0] + "-" + DateUtil.format(DateFormat.yyyyMMddHHmmss, DateUtil.now()) + "." + split[1];
                file1 = new File(newFilePath);
            }
        }
        log.info("file is exists: [{}]", saveFile);
        try (FileOutputStream fos = new FileOutputStream(file1)) {
            channelSftp.get(sourceFileName, fos);
            log.info("[{}] create success", saveFile);
        } catch (Exception e) {
            log.error("download file exception: {}", e.getMessage());
            throw new Exception("download file error............");
        }
    }

    /**
     * 上传
     *
     * @param uploadFilePath 上传文件全路径
     * @param ftpDirPath     服务器保存目录路径
     * @param newFileName    新文件名
     * @throws Exception Exception
     */
    public static void uploadFile(ChannelSftp channelSftp, String uploadFilePath, String ftpDirPath, String newFileName) throws Exception {
        try (FileInputStream fis = new FileInputStream(new File(uploadFilePath))) {
            channelSftp.cd(ftpDirPath);
            channelSftp.put(fis, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Upload file error.");
        }
    }

    /**
     * sftp读取目录中的所有文件
     */
    public static List<String> listFileNames(ChannelSftp channelSftp, String ftpDirPath) {
        return listFileNames(channelSftp, ftpDirPath, SortType.ASC, false);
    }

    public static List<String> listFileNames(ChannelSftp channelSftp, String ftpDirPath, SortType dateSortType) {
        return listFileNames(channelSftp, ftpDirPath, dateSortType, false);
    }

    public static List<String> listFileNames(ChannelSftp channelSftp, String ftpDirPath, boolean justFile) {
        return listFileNames(channelSftp, ftpDirPath, SortType.ASC, justFile);
    }

    public static List<String> listFileNames(ChannelSftp channelSftp, String ftpDirPath, SortType dateSortType, boolean justFile) {
        List<FileEntry> fileEntries = listFiles(channelSftp, ftpDirPath, dateSortType, justFile);
        return ListUtil.optimize(fileEntries).stream().map(FileEntry::getName).collect(Collectors.toList());
    }

    /**
     * sftp读取目录中的所有文件
     */
    public static List<FileEntry> listFiles(ChannelSftp channelSftp, String ftpDirPath) {
        return listFiles(channelSftp, ftpDirPath, SortType.ASC, false);
    }

    public static List<FileEntry> listFiles(ChannelSftp channelSftp, String ftpDirPath, SortType dateSortType) {
        return listFiles(channelSftp, ftpDirPath, dateSortType, false);
    }

    /**
     * sftp读取目录中的文件信息
     *
     * @param channelSftp channelSftp
     * @param ftpDirPath  服务器目录地址
     * @param justFile    是否只读取文件格式，不包含目录
     * @return 文件信息集
     */
    public static List<FileEntry> listFiles(ChannelSftp channelSftp, String ftpDirPath, SortType dateSortType, boolean justFile) {
        List<FileEntry> fileEntries = ListUtil.newArrayList();
        try {
            channelSftp.cd(ftpDirPath);
            Vector<?> files = channelSftp.ls(ftpDirPath);
            ListUtil.optimize(files).forEach(obj -> {
                if (obj instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
                    if (!entry.getAttrs().isDir()) {
                        fileEntries.add(convertLsEntry(entry));
                    }
                    if (entry.getAttrs().isDir() && !justFile) {
                        if (!POINT.equals(entry.getFilename()) && !DOUBLE_POINT.equals(entry.getFilename())) {
                            fileEntries.add(convertLsEntry(entry));
                        }
                    }
                }
            });
        } catch (SftpException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        if (dateSortType == null || dateSortType == SortType.ASC) {
            fileEntries.sort((o1, o2) -> (int) (o1.getUpdateTimestamp() - o2.getUpdateTimestamp()));
        } else {
            fileEntries.sort((o1, o2) -> (int) (o2.getUpdateTimestamp() - o1.getUpdateTimestamp()));
        }
        return fileEntries;
    }

    private static FileEntry convertLsEntry(ChannelSftp.LsEntry entry) {
        FileEntry fileEntry = new FileEntry();
        fileEntry.setDir(entry.getAttrs().isDir());
        fileEntry.setUpdateTimestamp(entry.getAttrs().getMTime());
        fileEntry.setName(entry.getFilename());
        fileEntry.setSize(entry.getAttrs().getSize());
        fileEntry.setLongName(entry.getLongname());
        return fileEntry;
    }

    /**
     * 查找ftp服务器上是否存在某个文件
     */
    public static boolean fileIsExists(ChannelSftp channelSftp, String ftpFilePath) throws SftpException {
        String ftpDirPath = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/"));
        String fileName = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1);
        channelSftp.cd(ftpDirPath);
        AtomicBoolean fileExists = new AtomicBoolean(false);
        channelSftp.ls(ftpDirPath, lsEntry -> {
            if (lsEntry.getFilename().equals(fileName)) {
                fileExists.set(true);
                return 1;
            }
            return 0;
        });
        return fileExists.get();
    }

    /**
     * 查找ftp服务器上是否存在某个目录
     */
    public static boolean dirIsExists(ChannelSftp channelSftp, String ftpDirPath) {
        // try {
        //     channelSftp.cd(ftpDirPath);
        //     return true;
        // } catch (SftpException e) {
        //     e.printStackTrace();
        // }
        // return false;
        boolean isExist = false;
        try {
            SftpATTRS sftpAttrs = channelSftp.lstat(ftpDirPath);
            isExist = true;
            return sftpAttrs.isDir();
        } catch (Exception e) {
            String noSuchFile = "no such file";
            if (noSuchFile.equals(e.getMessage().toLowerCase())) {
                isExist = false;
            }
        }
        return isExist;

    }

    /**
     * 删除ftp服务器上是否存在某个文件
     */
    public static boolean deleteFile(ChannelSftp channelSftp, String ftpFilePath) {
        try {
            if (fileIsExists(channelSftp, ftpFilePath)) {
                channelSftp.rm(ftpFilePath);
                return true;
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭
     *
     * @throws Exception Exception
     */
    public static void close(ChannelSftp channelSftp) throws Exception {
        log.debug("close............");
        try {
            if (null != channelSftp) {
                channelSftp.disconnect();
            }
            if (sshSession != null) {
                sshSession.disconnect();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("close stream error.");
        }
    }

    /**
     * 执行命令
     *
     * @param session  session
     * @param commands commands
     */
    public static List<String> exec(Session session, String... commands) {
        List<String> result = ListUtil.newArrayList();
        if (ArrayUtil.hasItem(commands)) {
            for (String command : commands) {
                List<String> execResult = exec(session, command);
                result.addAll(execResult);
            }
        }
        return result;
    }

    /**
     * 执行命令
     *
     * @param session session
     * @param command command
     */
    public static List<String> exec(Session session, String command) {
        List<String> result = ListUtil.newArrayList();
        ChannelExec channelExec = null;
        try {
            log.info("命令脚本：" + command);
            Channel channel = session.openChannel("exec");
            channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);
            channelExec.connect();
            channelExec.setInputStream(null);
            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf;
            while ((buf = reader.readLine()) != null) {
                log.info("命令响应：" + buf);
                result.add(buf);
            }
            reader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != channelExec) {
                channelExec.disconnect();
            }
        }
        return result;
    }

}
