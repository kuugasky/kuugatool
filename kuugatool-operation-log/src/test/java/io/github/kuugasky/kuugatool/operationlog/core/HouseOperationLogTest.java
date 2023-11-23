package io.github.kuugasky.kuugatool.operationlog.core;

import com.google.common.collect.Lists;
import io.github.kuugasky.kuugatool.core.string.SensitiveUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.operationlog.core.enums.*;
import io.github.kuugasky.kuugatool.operationlog.core.house.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * HouseOperationLogTest
 *
 * @author kuuga
 * @since 2023/3/10-03-10 15:05
 */
public class HouseOperationLogTest {

    public static void main(String[] args) throws Exception {
        HouseOperationLog history = initHistory();
        HouseOperationLog now = initNow();

        String content = OperationLogUtil.getContent(now, history, HouseOperationLog.class);
        System.out.println(content);
        System.out.println(StringUtil.repeatNormal());

        String contentPretty = OperationLogUtil.getContentPretty(content);
        System.out.println(contentPretty);

        System.out.println(StringUtil.repeatNormal());

        // 获取明文
        System.out.println(SensitiveUtil.acquirePlaintext(contentPretty));

        System.out.println(StringUtil.repeatNormal());

        // 获取密文
        System.out.println(SensitiveUtil.acquireCiphertext(contentPretty));
    }

    private static HouseOperationLog initHistory() {
        HouseOperationLog history = new HouseOperationLog();

        HouseBasicOperationLog house = new HouseBasicOperationLog();
        house.setHouseType(HouseTypeEnum.RENT);
        house.setBuildArea(new BigDecimal("1"));
        house.setBedroom(2);
        house.setHall(3);
        house.setKitchen(4);
        house.setBathroom(5);
        house.setOrientation(HouseOrientationEnum.EAST);
        house.setDecoration(HouseDecorationEnum.DELICATE);
        house.setLocationName("坐落A");
        house.setFloorName("楼层A");
        house.setRoomNo("房间A");
        house.setStructure(HouseStructureEnum.FLAT);
        house.setInsideArea(new BigDecimal("99"));
        house.setId("1");

        history.setHouse(house);

        HouseSellOperationLog sell = new HouseSellOperationLog();
        sell.setRoleProtect("");
        sell.setExtendType(HousePropertyEnum.PERSONAL);
        sell.setExtendPersonName("售拓房人A");
        sell.setSellType(HouseSellTypeEnum.PAID_IN);
        sell.setSellPrice(new BigDecimal("1000"));
        sell.setId("2");

        history.setSell(sell);

        HouseRentOperationLog rent = new HouseRentOperationLog();
        rent.setRoleProtect("");
        rent.setExtendType(HousePropertyEnum.PERSONAL);
        rent.setExtendPersonName("租拓房人A");
        rent.setRentType(HouseRentTypeEnum.MONTHLY_RENT);
        rent.setRentPrice(new BigDecimal("2222"));
        rent.setDeposit(new BigDecimal("500"));
        rent.setTransferfee(new BigDecimal("2000"));
        rent.setRentMethod(HouseLeaseWayEnum.WHOLE_RENT);
        rent.setPaymentMethod(HousePaymentMethodEnum.BET_ONE_PAY_ONE);
        rent.setHouseFacilitiesList(Lists.newArrayList());
        rent.setId("3");

        history.setRent(rent);
        HouseMaintainOperationLog maintain = new HouseMaintainOperationLog();
        maintain.setPropertyStatus(HousePropertyStatusEnum.MORTGAGE);
        maintain.setYearLimit(HouseYearLimitEnum.LESS_THAN_2_YEARS);
        maintain.setUniqueState(YesOrNoEnum.YES);
        maintain.setMortgageArrears(new BigDecimal("0"));
        maintain.setMortgageBank("");
        maintain.setLivingStatus(HouseLivingStatusEnum.SELF);
        maintain.setLeaseTermTime(new Date());
        maintain.setCommonProperty(YesOrNoEnum.YES);
        maintain.setPrimaryDegree(HouseOccupancyStatusEnum.OCCUPIED);
        maintain.setMiddleDegree(HouseOccupancyStatusEnum.OCCUPIED);
        maintain.setId("4");

        history.setMaintain(maintain);
        ArrayList<HouseOwnerOperationLog> ownerList = Lists.newArrayList();
        HouseOwnerOperationLog first = new HouseOwnerOperationLog();
        first.setName("业主A");
        first.setPhoneDistrict(CustomerPhoneDistrictEnum.MAINLAND);
        first.setPhone("15919901111");
        first.setRelationship(HouseOwnerRelationshipEnum.PROPERTY_OWNER);
        first.setMainPhone(YesOrNoEnum.YES);
        first.setId("11");

        ownerList.add(first);

        HouseOwnerOperationLog two = new HouseOwnerOperationLog();
        two.setName("业主B");
        two.setPhoneDistrict(CustomerPhoneDistrictEnum.MAINLAND);
        two.setPhone("15919902222");
        two.setRelationship(HouseOwnerRelationshipEnum.PROPERTY_OWNER);
        two.setMainPhone(YesOrNoEnum.YES);
        two.setId("6");

        ownerList.add(two);
        history.setOwnerList(ownerList);
        history.setId("7");
        return history;
    }

    private static HouseOperationLog initNow() {
        HouseOperationLog now = new HouseOperationLog();

        HouseBasicOperationLog house = new HouseBasicOperationLog();
        house.setHouseType(HouseTypeEnum.RENT);
        house.setBuildArea(new BigDecimal("0"));
        house.setBedroom(0);
        house.setHall(0);
        house.setKitchen(0);
        house.setBathroom(0);
        house.setOrientation(HouseOrientationEnum.EAST);
        house.setDecoration(HouseDecorationEnum.DELICATE);
        house.setLocationName("");
        house.setFloorName("");
        house.setRoomNo("");
        house.setStructure(HouseStructureEnum.FLAT);
        house.setInsideArea(new BigDecimal("0"));
        house.setId("");

        now.setHouse(house);

        HouseSellOperationLog sell = new HouseSellOperationLog();
        sell.setRoleProtect("");
        sell.setExtendType(HousePropertyEnum.PERSONAL);
        sell.setExtendPersonName("");
        sell.setSellType(HouseSellTypeEnum.PAID_IN);
        sell.setSellPrice(new BigDecimal("0"));
        sell.setId("");

        now.setSell(sell);

        HouseRentOperationLog rent = new HouseRentOperationLog();
        rent.setRoleProtect("");
        rent.setExtendType(HousePropertyEnum.PERSONAL);
        rent.setExtendPersonName("");
        rent.setRentType(HouseRentTypeEnum.MONTHLY_RENT);
        rent.setRentPrice(new BigDecimal("0"));
        rent.setDeposit(new BigDecimal("0"));
        rent.setTransferfee(new BigDecimal("0"));
        rent.setRentMethod(HouseLeaseWayEnum.WHOLE_RENT);
        rent.setPaymentMethod(HousePaymentMethodEnum.BET_ONE_PAY_ONE);
        rent.setHouseFacilitiesList(Lists.newArrayList());
        rent.setId("");

        now.setRent(rent);
        HouseMaintainOperationLog maintain = new HouseMaintainOperationLog();
        maintain.setPropertyStatus(HousePropertyStatusEnum.MORTGAGE);
        maintain.setYearLimit(HouseYearLimitEnum.LESS_THAN_2_YEARS);
        maintain.setUniqueState(YesOrNoEnum.YES);
        maintain.setMortgageArrears(new BigDecimal("0"));
        maintain.setMortgageBank("");
        maintain.setLivingStatus(HouseLivingStatusEnum.SELF);
        maintain.setLeaseTermTime(new Date());
        maintain.setCommonProperty(YesOrNoEnum.YES);
        maintain.setPrimaryDegree(HouseOccupancyStatusEnum.OCCUPIED);
        maintain.setMiddleDegree(HouseOccupancyStatusEnum.OCCUPIED);
        maintain.setId("1");

        now.setMaintain(maintain);
        ArrayList<HouseOwnerOperationLog> ownerList = Lists.newArrayList();
        HouseOwnerOperationLog first = new HouseOwnerOperationLog();
        first.setName("业主C");
        first.setPhoneDistrict(CustomerPhoneDistrictEnum.MAINLAND);
        first.setPhone("15919903333");
        first.setRelationship(HouseOwnerRelationshipEnum.FRIEND);
        first.setMainPhone(YesOrNoEnum.YES);
        first.setId("11");

        ownerList.add(first);
        HouseOwnerOperationLog two = new HouseOwnerOperationLog();
        two.setName("");
        two.setPhoneDistrict(CustomerPhoneDistrictEnum.MAINLAND);
        two.setPhone("");
        two.setRelationship(HouseOwnerRelationshipEnum.PROPERTY_OWNER);
        two.setMainPhone(YesOrNoEnum.YES);
        two.setId("");

        ownerList.add(two);
        now.setOwnerList(ownerList);
        now.setId("");
        return now;
    }

}
