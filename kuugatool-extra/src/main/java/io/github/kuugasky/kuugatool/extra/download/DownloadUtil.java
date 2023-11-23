// package cn.kuugatool.extra.download;
//
// import cn.kuugatool.core.collection.ListUtil;
// import cn.kuugatool.core.file.FilenameUtil;
// import cn.kuugatool.core.string.StringUtil;
// import cn.kuugatool.extra.zip.ZipUtil;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.http.HttpResponse;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.util.EntityUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.OutputStream;
// import java.net.URLEncoder;
// import java.nio.charset.StandardCharsets;
// import java.util.List;
// import java.util.UUID;
// import java.util.zip.ZipOutputStream;
//
// /**
//  * 下载工具类
//  *
//  * @author yoara
//  */
// public final class DownloadUtil {
//
//     private static final String MSIE = "MSIE";
//     private static final Logger LOGGER = LoggerFactory.getLogger(DownloadUtil.class);
//
//     private DownloadUtil() {
//
//     }
//
//     public static byte[] downloadAttachment(String uri)
//             throws IOException {
//         HttpResponse httpResponse = doGetHttpResponse(uri);
//         return EntityUtils.toByteArray(httpResponse.getEntity());
//     }
//
//     /**
//      * Download the attachment<br>
//      * do not forget close the OutputStream after use the method
//      *
//      * @param request  request
//      * @param response response
//      * @param uri      uri
//      * @param filename filename
//      * @throws IOException IOException
//      */
//     public static void downloadAttachment(HttpServletRequest request,
//                                           HttpServletResponse response,
//                                           String uri,
//                                           String filename) throws IOException {
//
//         if (StringUtils.isBlank(filename)) {
//             filename = UUID.randomUUID() + "." + getFileExtension(uri);
//         }
//
//         //First step: get data by the uri
//         HttpResponse httpResponse = doGetHttpResponse(uri);
//
//         InputStream stream = httpResponse.getEntity().getContent();
//
//         downloadAttachment(request, response, stream, filename);
//     }
//
//     private static HttpResponse doGetHttpResponse(String uri) throws IOException {
//         HttpClient client = HttpClientBuilder.create().build();
//         HttpGet get = new HttpGet(uri);
//         return client.execute(get);
//     }
//
//     /**
//      * 附件下载
//      *
//      * @param request  请求
//      * @param response 响应
//      * @param stream   文件输入流
//      * @param filename 文件名
//      */
//     public static void downloadAttachment(HttpServletRequest request,
//                                           HttpServletResponse response,
//                                           InputStream stream,
//                                           String filename) {
//         // Remove blank in the filename, but do not use regex(\s) for some reason
//         filename = filename.replace(" ", StringUtil.EMPTY);
//         filename = filename.replace(",", "_");
//         filename = filename.replace("，", "_");
//         filename = filename.replace("~", "_");
//
//         //多次转码chrome下载下来是乱码
//         // get client agent info，and encode the filename.(only in IE、FF、Chrome)
//         //String agent = request.getHeader("USER-AGENT");
//         // IE
//         //if (agent != null && agent.toUpperCase().contains(MSIE)) {
//         //    filename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
//         //} else {    // Other
//         //    filename = new String(filename.getBytes(), StandardCharsets.ISO_8859_1);
//         //}
//
//         response.reset();
//         response.setHeader("Connection", "close");
//         response.setHeader("Content-Type", "application/octet-stream");
//         response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
//
//         byte[] buffer = new byte[8192];
//         int bytesRead;
//
//         try (OutputStream ops = response.getOutputStream();
//              InputStream inputStream = stream) {
//
//             while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//                 ops.write(buffer, 0, bytesRead);
//             }
//
//             ops.flush();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//
//     /**
//      * 获取图片名
//      *
//      * @param fileFullName 文件全名（含后缀）
//      * @return 文件名
//      */
//     private static String getFileExtension(String fileFullName) {
//         if (StringUtils.isBlank(fileFullName)) {
//             return StringUtil.EMPTY;
//         }
//         if (fileFullName.contains("?")) {
//             fileFullName = fileFullName.split("\\?")[0];
//         }
//         if (fileFullName.contains("!")) {
//             fileFullName = fileFullName.split("!")[0];
//         }
//         return FilenameUtil.getExtension(fileFullName);
//     }
//
//     /**
//      * 图片批量压缩下载
//      *
//      * @param response response
//      * @param urls     图片urls
//      * @param zipName  压缩文件名
//      * @throws IOException IOException
//      */
//     public static void zipImages(HttpServletResponse response, List<String> urls, String zipName) throws IOException {
//         if (ListUtil.isEmpty(urls) || StringUtil.isEmpty(zipName)) {
//             return;
//         }
//
//         zipName = StringUtil.hasText(zipName) ? zipName : UUID.randomUUID().toString() + ".zip";
//         response.setContentType("APPLICATION/OCTET-STREAM");
//         response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(zipName, StandardCharsets.UTF_8));
//         try (ZipOutputStream out = new ZipOutputStream(response.getOutputStream())) {
//             for (String url : ListUtil.optimize(urls)) {
//                 if (isInvalidUrl(url)) {
//                     break;
//                 }
//                 ZipUtil.zipUrl(url, out);
//                 response.flushBuffer();
//             }
//         } catch (Exception e) {
//             LOGGER.error("图片批量压缩下载异常：", e);
//             e.printStackTrace();
//             throw e;
//         }
//     }
//
//     private static boolean isInvalidUrl(String url) {
//         if (StringUtil.isEmpty(url)) {
//             return true;
//         }
//         return !url.startsWith("http://") && !url.startsWith("https://");
//     }
//
// }
