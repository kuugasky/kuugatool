// package cn.kuugatool.extra.verifycode;
//
// import cn.kuugatool.core.string.StringUtil;
// import lombok.extern.slf4j.Slf4j;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import static cn.kuugatool.extra.verifycode.VerifyCodeBuilder.VERIFY_CODE_SESSION_TEMPORARY;
//
// /**
//  * VerifyCodeUtil
//  * 可用，但是建议使用kuugatool-captcha组件
//  *
//  * @author kuuga
//  * @since 2020-12-25 下午5:59
//  */
// @Slf4j
// @Deprecated
// public class VerifyCodeUtil {
//
//     /**
//      * 获得字符串图形验证码
//      *
//      * @param request  request
//      * @param response response
//      */
//     public static void codeImageGet(HttpServletRequest request, HttpServletResponse response) {
//         VerifyCodeBuilder.create(request, response);
//     }
//
//     /**
//      * 获得字符串图形验证码
//      *
//      * @param request             request
//      * @param response            response
//      * @param customCodeImgHeight 自定义字符串验证码图像高度，不填则使用默认
//      * @param customCodeImgWidth  自定义字符串验证码图像宽度，不填则使用默认
//      */
//     public static void codeImageGet(HttpServletRequest request, HttpServletResponse response, Integer customCodeImgWidth, Integer customCodeImgHeight) {
//         try {
//             VerifyCodeBuilder.create(request, response, customCodeImgWidth, customCodeImgHeight);
//         } catch (Exception ex) {
//             log.error("doPost error.", ex);
//         }
//     }
//
//     /**
//      * 校验字符图形验证码
//      *
//      * @param code    code
//      * @param request request
//      * @return boolean
//      */
//     public static boolean codeImageValidate(String code, HttpServletRequest request) {
//         String rightCode = (String) request.getSession().getAttribute(VERIFY_CODE_SESSION_TEMPORARY);
//
//         if (StringUtil.containsEmpty(rightCode, code)) {
//             return false;
//         }
//         if (rightCode.equalsIgnoreCase(code)) {
//             request.getSession().removeAttribute(VERIFY_CODE_SESSION_TEMPORARY);
//             return true;
//         }
//         return false;
//     }
//
// }
