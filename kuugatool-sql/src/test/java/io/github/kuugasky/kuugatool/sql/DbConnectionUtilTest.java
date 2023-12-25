package io.github.kuugasky.kuugatool.sql;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbType;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DbConnectionUtilTest {

    private final static String INFORMATION_SCHEMA = "kuuga_house_pre";
    private final static String MYSQL_DB_URL = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/" + INFORMATION_SCHEMA + "?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
    private final static String MYSQL_DB_USERNAME = "root";
    private final static String MYSQL_DB_PASSWORD = "kfang.com";

    @Test
    void connect() {
        // 获取连接
        Connection jdbcConnect = DbConnectionUtil.getConnect(DbType.MYSQL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD, MYSQL_DB_URL);
        System.out.println(StringUtil.formatString(jdbcConnect));
        // 关闭连接
        DbConnectionUtil.closeConnect(jdbcConnect);

        System.out.println(StringUtil.formatString(jdbcConnect));
    }

}