package io.github.kuugasky.kuugatool.core.file;

import io.github.kuugasky.kuugatool.core.exception.IORuntimeException;
import io.github.kuugasky.kuugatool.core.io.IoUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件类型判断工具类
 *
 * <p>此工具根据文件的前几位bytes猜测文件类型，对于文本、zip判断不准确，对于视频、图片类型判断准确</p>
 *
 * <p>需要注意的是，xlsx、docx等Office2007格式，全部识别为zip，因为新版采用了OpenXML格式，这些格式本质上是XML文件打包为zip</p>
 *
 * @author Looly
 */
public class FileTypeUtil {

    public static final String XLS = "xls";
    public static final String DOC = "doc";
    public static final String MSI = "msi";
    public static final String ZIP = "zip";
    public static final String DOCX = "docx";
    public static final String XLSX = "xlsx";
    public static final String PPTX = "pptx";
    public static final String JAR = "jar";
    public static final String WAR = "war";

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private FileTypeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final Map<String, String> FILE_TYPE_MAP;

    static {
        FILE_TYPE_MAP = new ConcurrentHashMap<>();

        // JPEG (jpg)
        FILE_TYPE_MAP.put("ffd8ff", "jpg");
        // PNG (png)
        FILE_TYPE_MAP.put("89504e47", "png");
        // GIF (gif)
        FILE_TYPE_MAP.put("4749463837", "gif");
        // GIF (gif)
        FILE_TYPE_MAP.put("4749463839", "gif");
        // TIFF (tif)
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        // 16色位图(bmp)
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp");
        // 24位位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp");
        // 256色位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp");
        // CAD (dwg)
        FILE_TYPE_MAP.put("41433130313500000000", "dwg");
        // Rich Text Format (rtf)
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");
        // Photoshop (psd)
        FILE_TYPE_MAP.put("38425053000100000000", "psd");
        // Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");
        // MS Access (mdb)
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");
        // ps
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        // Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("255044462d312e", "pdf");
        // rmvb/rm相同
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb");
        // flv与f4v相同
        FILE_TYPE_MAP.put("464c5601050000000900", "flv");
        // mp4
        FILE_TYPE_MAP.put("00000020667479706", "mp4");
        // mp4
        FILE_TYPE_MAP.put("00000018667479706D70", "mp4");
        // mp3
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        // mpg
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg");
        // wmv与asf相同
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv");
        // Wave (wav)
        FILE_TYPE_MAP.put("52494646e27807005741", "wav");
        // avi
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        // MIDI (mid)
        FILE_TYPE_MAP.put("4d546864000000060001", "mid");
        // WinRAR
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504B03040a0000000000", "jar");
        FILE_TYPE_MAP.put("504B0304140008000800", "jar");
        // MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10", "xls");
        // zip
        FILE_TYPE_MAP.put("504B0304", "zip");
        // 可执行文件
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");
        // jsp文件
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");
        // MF文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");
        // java文件
        FILE_TYPE_MAP.put("7061636b61676520636e", "java");
        // bat文件
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");
        // gz文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");
        // bat文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");
        // bat文件
        FILE_TYPE_MAP.put("49545346030000006000", "chm");
        // bat文件
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");
        // torrent
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        // Quicktime (mov)
        FILE_TYPE_MAP.put("6D6F6F76", "mov");
        // WordPerfect (wpd)
        FILE_TYPE_MAP.put("FF575043", "wpd");
        // Outlook Express (dbx)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx");
        // Outlook (pst)
        FILE_TYPE_MAP.put("2142444E", "pst");
        // Quicken (qdf)
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf");
        // Windows Password (pwl)
        FILE_TYPE_MAP.put("E3828596", "pwl");
        // Real Audio (ram)
        FILE_TYPE_MAP.put("2E7261FD", "ram");
    }

    /**
     * 增加文件类型映射<br>
     * 如果已经存在将覆盖之前的映射
     *
     * @param fileStreamHexHead 文件流头部Hex信息
     * @param extName           文件扩展名
     * @return 之前已经存在的文件扩展名
     */
    public static String putFileType(String fileStreamHexHead, String extName) {
        return FILE_TYPE_MAP.put(fileStreamHexHead.toLowerCase(), extName);
    }

    /**
     * 移除文件类型映射
     *
     * @param fileStreamHexHead 文件流头部Hex信息
     * @return 移除的文件扩展名
     */
    public static String removeFileType(String fileStreamHexHead) {
        return FILE_TYPE_MAP.remove(fileStreamHexHead.toLowerCase());
    }

    /**
     * 根据文件流的头部信息获得文件类型
     *
     * @param fileStreamHexHead 文件流头部16进制字符串
     * @return 文件类型，未找到为<code>null</code>
     */
    public static String getType(String fileStreamHexHead) {
        for (Entry<String, String> fileTypeEntry : FILE_TYPE_MAP.entrySet()) {
            String key = fileTypeEntry.getKey();
            if (fileStreamHexHead.toLowerCase().startsWith(key.toLowerCase())) {
                return fileTypeEntry.getValue();
            }
        }
        return null;
    }

    /**
     * 根据文件流的头部信息获得文件类型
     *
     * @param in {@link InputStream}
     * @return 类型，文件的扩展名，未找到为<code>null</code>
     * @throws IORuntimeException 读取流引起的异常
     */
    public static String getType(InputStream in) throws IORuntimeException {
        return getType(IoUtil.readHex28Lower(in));
    }

    /**
     * 根据文件流的头部信息获得文件类型
     *
     * <pre>
     *     1、无法识别类型默认按照扩展名识别
     *     2、xls、doc、msi头信息无法区分，按照扩展名区分
     *     3、zip可能为docx、xlsx、pptx、jar、war头信息无法区分，按照扩展名区分
     * </pre>
     *
     * @param file 文件 {@link File}
     * @return 类型，文件的扩展名，未找到为<code>null</code>
     * @throws IORuntimeException 读取文件引起的异常
     */
    public static String getType(File file) throws IORuntimeException {
        String typeName;
        FileInputStream in = null;
        try {
            in = IoUtil.toInputStream(file);
            typeName = getType(in);
        } finally {
            IoUtil.close(in);
        }

        if (null == typeName) {
            // 未成功识别类型，扩展名辅助识别
            typeName = FilenameUtil.getExtension(file);
        } else if (XLS.equals(typeName)) {
            // xls、doc、msi的头一样，使用扩展名辅助判断
            final String extName = FilenameUtil.getExtension(file);
            if (DOC.equalsIgnoreCase(extName)) {
                typeName = DOC;
            } else if (MSI.equalsIgnoreCase(extName)) {
                typeName = MSI;
            }
        } else if (ZIP.equals(typeName)) {
            // zip可能为docx、xlsx、pptx、jar、war等格式，扩展名辅助判断
            final String extName = FilenameUtil.getExtension(file);
            if (DOCX.equalsIgnoreCase(extName)) {
                typeName = DOCX;
            } else if (XLSX.equalsIgnoreCase(extName)) {
                typeName = XLSX;
            } else if (PPTX.equalsIgnoreCase(extName)) {
                typeName = PPTX;
            } else if (JAR.equalsIgnoreCase(extName)) {
                typeName = JAR;
            } else if (WAR.equalsIgnoreCase(extName)) {
                typeName = WAR;
            }
        }
        return typeName;
    }

    /**
     * 通过路径获得文件类型
     *
     * @param path 路径，绝对路径或相对ClassPath的路径
     * @return 类型
     * @throws IORuntimeException 读取文件引起的异常
     */
    public static String getTypeByPath(String path) throws IORuntimeException {
        return getType(FileUtil.createFile(path));
    }

}
