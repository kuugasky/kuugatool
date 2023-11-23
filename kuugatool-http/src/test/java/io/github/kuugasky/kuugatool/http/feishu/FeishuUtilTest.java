package io.github.kuugasky.kuugatool.http.feishu;

import io.github.kuugasky.kuugatool.core.date.DateUtil;
import org.junit.jupiter.api.Test;

class FeishuUtilTest {

    @Test
    void main() {
        final String accessToken = "09fd1454-e379-4f7e-9aae-e56fbfb653f3";
        final String secret = "83mNmgovwINGa8lX7RLWPf";
        String serviceWarningTemplate = "系统：%s\n地址：%s\n功能：%s\n时间：%s\n预警：%s";
        String context = String.format(serviceWarningTemplate,
                "房客宝", "127.0.0.1", "服务异常",
                DateUtil.formatDateTime(DateUtil.now()), "异常信息");
        String title = String.format("[%s][盘客]系统预警通知 ", "DEV");

        String text = String.format("❗❗❗<span style='color:red;'>️%s</span>\n\n%s", title, context);
        // sendText(accessToken, secret, "生产服务异常-test\nservice-agent-house服务不可用");
        FeishuUtil.sendText(accessToken, secret, text);
    }


}