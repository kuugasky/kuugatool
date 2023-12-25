package io.github.kuugasky.kuugatool.json.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * IM通知消息
 *
 * @author kuuga
 * @since 2021-10-20
 */
@Data
public class ImNotifyMessage implements Serializable {

    private Long msgId;

    private String fromUserId;

    private String fromPhone;

    private long fromTimeStamp;

    private String typeVersion;

    /**
     * TEXT（纯文本 ）、IMAGE（图片）、AUDIO（音频）、HOUSE（房源）
     */
    private String type;

    /**
     * SALE（二手房）、RENT（租房）、NEWHOUSE（新房）、SHARE（共享办公）、 BUILDING（大厦-商办专属）、NETWORK（网点-商办专属）、SHOP(商铺-商办专属)、FACTORY(厂房-商办专属)
     */
    private String houseType;

    private String content;

    private String toUserId;

    private String toUserIsOnline;

    /**
     * ONLINE - 客户端前台/后台运行中
     * PUSH_ONLINE - 进程被kill或因网络问题掉线
     * OFFLINE - 客户端主动退出登录或者客户端自上一次登录起7天之内未登录过
     */
    private String toUserOnlineStatus;

    /**
     * USED（二手）、RENT（租赁）、NEW_HOUSE（新房）、COMMUNITY（小区）、KUUGA_SHOP（经纪人店铺）、OTHER（其他）
     */
    private String businessType;

    private String houseId;

    private String houseShortId;

    private String houseExpandId;

    private String gardenId;

    private String gardenName;

    /**
     * APP、PC、H5、WAP、MPWEIXIN、MPBAIDU、MPALIPAY、MPTOUTIAO、SYSTEM
     */
    private String source;

    private String propertyUseName;

    private String propertyUse;

    /**
     * KFANG（看房）、QFANG（Q房）、LIANJIA（链家）、ZHONGYUAN（中原）、KE（贝壳）、COMMERCIAL（商办）、SPIDER（采集）
     */
    private String houseSource;

    private String jumpLink;

    private String startEntrance;

    private String apartment;

    private List<String> builtupArea;

    private String toName;

    private String toOrgId;

    private String toOrgName;

    private JpushParam jpushParam;

    private HouseMsgContent lastSendHouseMsgContent;

    private String brokerPhone;

    @Data
    public static class JpushParam {

        private String fromUserId;

        private String fromName;

        private String fromPhone;

        private String fromPhoto;
    }


    /**
     * IM消息类型枚举
     */
    @AllArgsConstructor
    @SuppressWarnings("unused")
    public enum ImTypeEnum {
        /**
         *
         */
        TEXT("纯文本"),
        IMAGE("图片"),
        AUDIO("音频"),
        // VIDEO("视频"),
        HOUSE("房源"),
        ;

        @Getter
        private final String desc;
    }

    /**
     * IM消息业务类型枚举
     */
    @AllArgsConstructor
    @SuppressWarnings("unused")
    public enum ImBusinessTypeEnum {
        /**
         *
         */
        USED("二手"),
        RENT("租赁"),
        NEW_HOUSE("新房"),
        COMMUNITY("小区"),
        KUUGA_SHOP("经纪人店铺"),
        OTHER("其他"),
        ;

        @Getter
        private final String desc;

    }

    @Data
    public static class HouseMsgContent {
        private String apartment;

        private String areaName;

        private List<String> builtupArea;

        private String cityCode;

        private String coverImgUrl;

        private String direction;

        private String extra;

        private String gardenId;

        private String gardenName;

        private String houseExpandId;

        private String houseId;

        private String houseShortId;

        private String houseSource;

        private String houseStatus;

        private String houseType;

        private String jumpLink;

        private String price;

        private String priceType;

        private String propertyUseName;

        private String propertyUse;

        private String regionName;

        private String title;

        private String unitPrice;
    }
}