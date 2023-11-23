// package cn.kuugatool.extra.download.demo;
//
// import cn.kuugatool.core.collection.ListUtil;
// import cn.kuugatool.core.file.FilenameUtil;
// import cn.kuugatool.core.object.ObjectUtil;
// import cn.kuugatool.core.string.IdUtil;
// import cn.kuugatool.core.string.StringUtil;
// import cn.kuugatool.extra.zip.ZipUtil;
// import com.kfang.common.agent.form.url.UrlForm;
// import com.kfang.common.agent.form.url.UrlListForm;
// import com.kfang.common.agent.form.url.UrlZipForm;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import kfang.infra.feature.aliyun.core.AliyunOss;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import javax.annotation.Resource;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.List;
// import java.util.stream.Collectors;
//
// /**
//  * 下载服务Controller
//  *
//  * @author kuuga
//  * @since 2018-12-13 10:14
//  */
// @Lazy
// @RestController
// @RequestMapping("/security/download/")
// @Api(value = "downloadServer", tags = "公共服务-下载服务")
// public class DownloadController extends AliyunBaseController {
//
//     @Resource
//     private AliyunOss aliyunOss;
//
//     private final Logger logger = LoggerFactory.getLogger(DownloadController.class);
//
//     @PostMapping(value = "file")
//     @ApiOperation(value = "文件下载")
//     public void file(HttpServletRequest request, HttpServletResponse response, @RequestBody UrlForm urlForm) throws IOException {
//         final String url = urlForm.getUrl();
//
//         if (isInvalidUrl(url)) {
//             return;
//         }
//         String urlName = FilenameUtil.getName(url, "\\?");
//         urlName = urlName.substring(0, urlName.lastIndexOf("-"));
//
//         aliyunOss.download(request, response, url, urlName);
//     }
//
//     @PostMapping(value = "batchImage")
//     @ApiOperation(value = "图片批量下载(非打包压缩)")
//     public void batchImage(HttpServletRequest request, HttpServletResponse response, @RequestBody UrlListForm urlListForm) {
//         if (null == urlListForm) {
//             return;
//         }
//         ListUtil.optimize(urlListForm.getUrls()).stream().filter(url -> !isInvalidUrl(url.getUrl())).forEach(url -> {
//             try {
//                 aliyunOss.download(request, response, url.getUrl(), url.getFileName());
//             } catch (IOException e) {
//                 logger.error("原图批量下载异常：", e);
//                 e.printStackTrace();
//             }
//         });
//     }
//
//     @PostMapping(value = "zipImage")
//     @ApiOperation(value = "图片批量打包下载(非原图)")
//     public void zipImage(HttpServletResponse response, @RequestBody UrlZipForm urlZipForm) {
//         if (ObjectUtil.isNull(urlZipForm)) {
//             return;
//         }
//
//         String downloadFileName = urlZipForm.getZipName();
//         String zipName = StringUtil.hasText(downloadFileName) ? downloadFileName : IdUtil.simpleUUID() + ".zip";
//
//         List<String> distanceUrls = ListUtil.distinct(urlZipForm.getUrls()).stream()
//                 .filter(url -> !isInvalidUrl(url))
//                 .collect(Collectors.toList());
//
//         ZipUtil.doZip(zipName, distanceUrls, response);
//     }
//
// }
