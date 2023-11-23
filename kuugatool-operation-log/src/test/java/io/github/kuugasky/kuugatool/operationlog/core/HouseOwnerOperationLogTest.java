package io.github.kuugasky.kuugatool.operationlog.core;

import io.github.kuugasky.kuugatool.core.string.SensitiveUtil;
import io.github.kuugasky.kuugatool.operationlog.core.enums.CustomerPhoneDistrictEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HouseOwnerRelationshipEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.YesOrNoEnum;
import io.github.kuugasky.kuugatool.operationlog.core.house.HouseOwnerOperationLog;
import lombok.Data;

/**
 * 房源业主保存Form
 *
 * @author pql
 * @since 2021-03-17
 */
@Data
public class HouseOwnerOperationLogTest {

    public static void main(String[] args) throws Exception {
        HouseOwnerOperationLog now = new HouseOwnerOperationLog();
        now.setName("秦枫");
        now.setPhoneDistrict(CustomerPhoneDistrictEnum.OTHER);
        now.setPhone("15919901234");
        now.setRelationship(HouseOwnerRelationshipEnum.KINSHIP);
        now.setMainPhone(YesOrNoEnum.YES);
        now.setId("1");
        HouseOwnerOperationLog history = new HouseOwnerOperationLog();
        history.setName("kuuga");
        history.setPhoneDistrict(CustomerPhoneDistrictEnum.HONGKONG);
        history.setPhone("13800138000");
        history.setRelationship(HouseOwnerRelationshipEnum.DRIVER);
        history.setMainPhone(YesOrNoEnum.NO);
        history.setId("2");
        String content = OperationLogUtil.getContent(now, history, HouseOwnerOperationLog.class);
        System.out.println(content);
        // 获取明文
        System.out.println(SensitiveUtil.acquirePlaintext(content));
        // 获取密文
        System.out.println(SensitiveUtil.acquireCiphertext(content));
    }

}
