package io.github.kuugasky.kuugatool.sql;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbConnectParam;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbType;
import io.github.kuugasky.kuugatool.sql.scheme.entity.FieldModel;
import io.github.kuugasky.kuugatool.sql.scheme.entity.TableInfo;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

class DbTableUtilTest {

    @Test
    void testQueryTableInfos() throws SQLException {
        String username = "root";
        String password = "kfang.com";
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/kuuga_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, username, password, url);

        List<TableInfo> tableInfos = DbTableUtil.queryTableInfos(dbConnectParam, "kuuga_house");
        ListUtil.optimize(tableInfos).forEach(tableInfo -> {
            List<FieldModel> fieldModels = tableInfo.getFieldModels();
            String collect = fieldModels.stream().map(FieldModel::getColumnName).collect(Collectors.joining("#"));
            System.out.println(tableInfo.getTableDesc() + ":" + collect);
        });
    }

    @Test
    void testTestQueryTableInfos() throws SQLException {
        String username = "root";
        String password = "kfang.com";
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/kuuga_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, url, username, password);

        TableInfo tableInfo = DbTableUtil.getTableInfo(dbConnectParam, "kuuga_house", "t_house_log");
        List<FieldModel> fieldModels = tableInfo.getFieldModels();
        String collect = fieldModels.stream().map(FieldModel::getColumnName).collect(Collectors.joining("#"));
        System.out.println(tableInfo.getTableDesc() + ":" + collect);
    }

    @Test
    void testQueryTableInfosByContainsField() throws SQLException {
        String username = "root";
        String password = "kfang.com";
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/kuuga_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, url, username, password);

        List<TableInfo> tableInfos = DbTableUtil.queryTableInfosByContainsField(dbConnectParam, "kuuga_house", "FROOM_ID");
        System.out.println(tableInfos.size());
    }

    @Test
    void testTestQueryTableInfosByContainsField() throws SQLException {
        String username = "root";
        String password = "kfang.com";
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/kuuga_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, url, username, password);

        List<TableInfo> tableInfos = DbTableUtil.queryTableInfosByContainsField(dbConnectParam, "kuuga_house", "froom_id", true);
        System.out.println(tableInfos.size());
        tableInfos.forEach(tableInfo -> System.out.println(StringUtil.formatString(tableInfo)));
    }

}