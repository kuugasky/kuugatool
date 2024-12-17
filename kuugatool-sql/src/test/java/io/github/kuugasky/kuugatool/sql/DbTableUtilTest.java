package io.github.kuugasky.kuugatool.sql;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbConnectParam;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbType;
import io.github.kuugasky.kuugatool.sql.scheme.entity.FieldModel;
import io.github.kuugasky.kuugatool.sql.scheme.entity.TableInfo;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

class DbTableUtilTest {

    @Test
    void testQueryTableInfos() throws SQLException {
        // String username = "root";
        // String password = "kuuga.com";
        // String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/kuuga_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";

        // DruidDataSource.class
        // 采用jasypt-spring-boot-starter加密，断点DruidDataSource.class的this.jdbcUrl = this.jdbcUrl.trim();
        // 通过jdbcUrl条件来获取对应的明文帐号和密码

        // String username = "ENC(CeWK0BSMzp+k56dScSvPJA==)";
        // String password = "ENC(mM2j6WzQM4l7AM4ZIP35p2AcTsrb3GdjkN7EbipXGCE=)";
        String username = "kuugasky";
        String password = "kuugaskylcf13871673157zq";
        String url = "jdbc:mysql://grp-8dob6c9n.sql.tencentcdb.com:3306/jim?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true&useSSL=true";

        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, username, password, url);
        Connection connection = DbConnectionUtil.getConnect(dbConnectParam);
        System.out.println(connection);

        // DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, username, password, url);
        //
        List<TableInfo> tableInfos = DbTableUtil.queryTableInfos(dbConnectParam, "jim");
        ListUtil.optimize(tableInfos).forEach(tableInfo -> {
            List<FieldModel> fieldModels = tableInfo.getFieldModels();
            // String collect = fieldModels.stream().map(FieldModel::getColumnName).collect(Collectors.joining("#"));
            // String collect = tableInfo.getTableDesc() + " : " + tableInfo.getTableName();
            // System.out.println(tableInfo.getTableDesc() + ":" + collect);
            System.out.printf("%s[%s] ", tableInfo.getTableName(), StringUtil.removeAllSpace(tableInfo.getTableDesc()));
            System.out.printf("字段：%s%n",
                    fieldModels.stream().map(fieldModel ->
                                    "%s(%s)".formatted(StringUtil.removeAllSpace(fieldModel.getColumnName()),
                                            StringUtil.removeAllSpace(fieldModel.getColumnComment())))
                            .collect(Collectors.joining("、"))
            );
        });
    }

    public static void main(String[] args) {
        System.out.printf("[%s]", StringUtil.removeAllSpace("题库视频对应表\n" +
                "以产品为主,视频提供基础数据"));
    }

    @Test
    void testTestQueryTableInfos() throws SQLException {
        String username = "root";
        String password = "kuuga.com";
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
        String password = "kuuga.com";
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/kuuga_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, url, username, password);

        List<TableInfo> tableInfos = DbTableUtil.queryTableInfosByContainsField(dbConnectParam, "kuuga_house", "FROOM_ID");
        System.out.println(tableInfos.size());
    }

    @Test
    void testTestQueryTableInfosByContainsField() throws SQLException {
        String username = "root";
        String password = "kuuga.com";
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/kuuga_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, url, username, password);

        List<TableInfo> tableInfos = DbTableUtil.queryTableInfosByContainsField(dbConnectParam, "kuuga_house", "froom_id", true);
        System.out.println(tableInfos.size());
        tableInfos.forEach(tableInfo -> System.out.println(StringUtil.formatString(tableInfo)));
    }

}