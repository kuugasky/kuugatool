package io.github.kuugasky.kuugatool.sql;

import com.alibaba.druid.DbType;
import org.junit.jupiter.api.Test;

public class SqlStatementsUtilTest {

    String sql = """
            select `house`.`FCITY_CODE`                                                                                           AS `城市CODE`,
                    `garden`.`FNAME`                                                                                               AS `楼盘名`,
                    `zone`.`FNAME`                                                                                                 AS `区域`,
            concat(`areazone`.`FNAME`)                                                                                     AS `商圈`,
                    `kuuga_house`.`selectHouseTypeByHouseId`(`house`.`FID`)                                                        AS `房源状态`,
                    `house`.`FROOM_NUMBER`                                                                                         AS `房号编码`,
                    `dts_infra_oa`.`selectWarZoneOrgName`(`person`.`FEXTEND_PERSON_ORG_ID`)                                        AS `拓房人战区`,
            extendPersonOrg.FREGION_ORG_NAME,
                    `dts_infra_oa`.`selectWarTeamOrgName`(`person`.`FEXTEND_PERSON_ORG_ID`)                                        AS `拓房人战队`,
            extendPersonOrg.FAREA_ORG_NAME,
                    `person`.`FEXTEND_PERSON_ORG_NAME`                                                                             AS `拓房人门店`,
                    `person`.`FEXTEND_PERSON_NAME`                                                                                 AS `拓房人姓名`,
                    `house`.`FCREATE_TIME`                                                                                         AS `拓房时间`,
                    (case `entrust`.`FSELL_ENTRUST_TYPE`
            when 'SELF_SIGN' then '我司'
            when 'NOT_SELF_SIGN' then '他司'
                    else '-' end)                                                                                             AS `委卖类型`,
                    if((`entrust`.`FSELL_ENTRUST_TYPE` in ('SELF_SIGN', 'NOT_SELF_SIGN')),
                    `dts_infra_oa`.`selectWarZoneOrgName`(`entrust`.`FCREATE_OPERATOR_ORG_ID`),
                    '-')                                                                                                        AS `委卖上传人战区`,
            enstrustPersonOrg.FREGION_ORG_NAME,
                    if((`entrust`.`FSELL_ENTRUST_TYPE` in ('SELF_SIGN', 'NOT_SELF_SIGN')),
                    `dts_infra_oa`.`selectWarZoneOrgName`(`entrust`.`FCREATE_OPERATOR_ORG_ID`),
                    '-')                                                                                                        AS `委卖上传人战队`,
            enstrustPersonOrg.FAREA_ORG_NAME,
                    if((`entrust`.`FSELL_ENTRUST_TYPE` in ('SELF_SIGN', 'NOT_SELF_SIGN')), `entrust`.`FCREATE_OPERATOR_ORG_NAME`,
                    '-')                                                                                                        AS `委卖上传人门店`,
                    if((`entrust`.`FSELL_ENTRUST_TYPE` in ('SELF_SIGN', 'NOT_SELF_SIGN')), `entrust`.`FCREATE_OPERATOR_NAME`,
                    '-')                                                                                                        AS `委卖上传人`,
                    if((`entrust`.`FSELL_ENTRUST_TYPE` in ('SELF_SIGN', 'NOT_SELF_SIGN')), `entrust`.`FCREATE_TIME`,
                    '-')                                                                                                        AS `委卖上传时间`,
                    if((`entrust`.`FSELL_ENTRUST_TYPE` in ('SELF_SIGN', 'NOT_SELF_SIGN')),
            cast(`entrust`.`FENTRUST_END_TIME` as date),
                  '-')                                                                                                        AS `委卖有效期`,
                    if((`entrust`.`FSELL_ENTRUST_TYPE` in ('SELF_SIGN', 'NOT_SELF_SIGN')),
                    (to_days(`entrust`.`FENTRUST_END_TIME`) - to_days(now())),
                    '-')                                                                                                        AS `委卖到期天数`
            from (((((((`kuuga_house`.`t_house_sell` `sell`
                    left join `kuuga_house`.`t_house` `house` on ((`house`.`FID` = `sell`.`FHOUSE_ID`))) left join `kuuga_house`.`t_house_extend_person` `person` on ((
                                                                                                                                                                              (`person`.`FHOUSE_ID` = `sell`.`FHOUSE_ID`) and
                (`person`.`FENABLED_STATUS` = 'ENABLED'))))
            left join `kuuga_dts`.`t_dts_dict_garden` `garden` on ((`garden`.`FID` = `house`.`FGARDEN_ID`))) left join `kuuga_house`.`t_house_entrust` `entrust` on ((
                                                                                                                                                                             (`house`.`FID` = `entrust`.`FHOUSE_ID`) and
                (`entrust`.`FENABLED_STATUS` = 'ENABLED'))))
            left join `dts_infra_dict`.`t_zone` `zone` on ((convert(`zone`.`FID` using utf8mb4) = `house`.`FREGION_ID`))) left join `dts_infra_dict`.`t_garden_businessarea` `businessarea` on ((
                                                                                                                                                                                                convert(`businessarea`.`FGARDEN_ID` using utf8mb4) = `house`.`FGARDEN_ID`)))
            left join `dts_infra_dict`.`t_zone` `areazone` on ((`areazone`.`FID` = `businessarea`.`FBUSINESSAREA_ID`)))
            left join dts_infra_oa.t_report_store_org_detail extendPersonOrg on extendPersonOrg.FID = person.FEXTEND_PERSON_ORG_ID
            left join dts_infra_oa.t_report_store_org_detail enstrustPersonOrg on enstrustPersonOrg.FID = entrust.FCREATE_OPERATOR_ORG_ID
            where ((`house`.`FCITY_CODE` = 'SHENZHEN') and (`sell`.`FENABLED_STATUS` = 'ENABLED') and
                    (`sell`.`FTRADE_STATUS` = 'NO') and (`house`.`FENABLED_STATUS` = 'ENABLED')
            -- and (date_format(`entrust`.`FCREATE_TIME`, '%Y-%m-%d %H:%i:%s') between (select date_format((curdate() + interval -(1) day), '%Y-%m-%d %H:%i:%s')) and (select (curdate() - interval 1 second)))
                    );
            """;

    @Test
    public void isValid() {
        System.out.println(SqlStatementsUtil.isValid(sql, DbType.oracle));
    }

    @Test
    public void toSqlString() {
        System.out.println(SqlStatementsUtil.toSqlString(sql, DbType.oracle));
    }

    @Test
    public void testToSqlString() {
        System.out.println(SqlStatementsUtil.toSqlString(sql, DbType.oracle, true));
    }

    @Test
    public void testToSqlString1() {
        System.out.println(SqlStatementsUtil.toSqlString(sql, DbType.oracle, true, true));
    }

    @Test
    public void testToSqlString2() {
        System.out.println(SqlStatementsUtil.toSqlString(sql, DbType.oracle, false, true, true));
    }

    @Test
    public void testToSqlString3() {
        System.out.println(SqlStatementsUtil.toSqlString(sql, DbType.oracle, true, true, true, true));
    }

}