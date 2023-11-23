// package cn.kuugatool.extra.download.demo;
//
// import cn.kuugatool.core.string.StringUtil;
// import com.kfang.common.agent.constants.SysConstants;
// import com.kfang.common.agent.logger.AgentLogModule;
// import com.kfang.web.agent.controller.WebBaseController;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import kfang.agent.feature.saas.logger.util.LogUtil;
// import kfang.infra.api.JsonCommonCodeEnum;
// import kfang.infra.feature.aliyun.bean.request.AliyunImageUploadRequest;
// import kfang.infra.feature.aliyun.bean.request.AliyunUploadRequest;
// import kfang.infra.feature.aliyun.bean.response.AliyunUploadResult;
// import kfang.infra.feature.aliyun.core.AliyunOss;
// import kfang.infra.feature.aliyun.enums.ImageTypeEnum;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
//
// import javax.annotation.Resource;
//
// /**
//  * 上传服务Controller
//  *
//  * @author kuuga
//  * @since 2018-12-13 10:14
//  */
// @Lazy
// @Slf4j
// @RestController
// @RequestMapping("/security/upload/")
// @Api(value = "uploadServer", tags = "公共服务-上传服务")
// public class UploadController extends WebBaseController {
//
//     private static final long ONE_MB = 1024L;
//
//     @Resource
//     private AliyunOss aliyunOss;
//
//     @PostMapping(value = "audio")
//     @ApiOperation(value = "上传音频")
//     public String audio(@RequestParam("file") MultipartFile multipartFile) {
//         try {
//             AliyunUploadRequest uploadRequest = new AliyunUploadRequest(multipartFile);
//             uploadRequest.setFileSizeLimit(SysConstants.TEN * ONE_MB);
//
//             return successInfo(aliyunOss.audioUpload(uploadRequest));
//         } catch (Exception e) {
//             LogUtil.error(log, AgentLogModule.OSS_AUDIO, "上传音频文件异常",
//                     "上传音频文件【" + multipartFile.getOriginalFilename() + "】异常:" + e.getMessage(), e);
//
//             final String message = e.getMessage();
//             return StringUtil.hasText(message) ? withCustomMessage(message, JsonCommonCodeEnum.E0011) : wrong(JsonCommonCodeEnum.E0011);
//         }
//     }
//
//     @PostMapping(value = "file")
//     @ApiOperation(value = "上传文件")
//     public String file(@RequestParam("file") MultipartFile multipartFile) {
//         try {
//             AliyunUploadRequest uploadRequest = new AliyunUploadRequest(multipartFile);
//             uploadRequest.setFileSizeLimit(SysConstants.TEN * ONE_MB);
//
//             return successInfo(aliyunOss.fileUpload(uploadRequest));
//         } catch (Exception e) {
//             LogUtil.error(log, AgentLogModule.OSS_FILE, "上传无水印文件异常",
//                     "上传无水印文件【" + multipartFile.getOriginalFilename() + "】异常:" + e.getMessage(), e);
//             final String message = e.getMessage();
//             return StringUtil.hasText(message) ? withCustomMessage(message, JsonCommonCodeEnum.E0011) : wrong(JsonCommonCodeEnum.E0011);
//         }
//     }
//
//     @PostMapping(value = "image")
//     @ApiOperation(value = "上传图片")
//     public String image(@RequestParam("file") MultipartFile multipartFile, @RequestParam("imageType") ImageTypeEnum imageTypeEnum) {
//         try {
//             LogUtil.info(log, AgentLogModule.OSS_IMAGE, "上传图片开始", String.format("上传图片名称【%s】", multipartFile.getOriginalFilename()));
//
//             AliyunImageUploadRequest uploadRequest = new AliyunImageUploadRequest(multipartFile);
//             uploadRequest.setFileSizeLimit(SysConstants.TEN * ONE_MB);
//             uploadRequest.setImageType(imageTypeEnum);
//             AliyunUploadResult aliyunUploadResult = aliyunOss.imageUpload(uploadRequest);
//
//             LogUtil.info(log, AgentLogModule.OSS_IMAGE, "上传图片结束", String.format("上传图片名称【%s】", multipartFile.getOriginalFilename()));
//             return successInfo(aliyunUploadResult);
//         } catch (Exception e) {
//             final String message = e.getMessage();
//             LogUtil.error(log, AgentLogModule.OSS_IMAGE, "上传图片异常",
//                     String.format("上传图片名称【%s】,异常信息:%s", multipartFile.getOriginalFilename(), message), e);
//             return StringUtil.hasText(message) ? withCustomMessage(message, JsonCommonCodeEnum.E0011) : wrong(JsonCommonCodeEnum.E0011);
//         }
//     }
//
// }