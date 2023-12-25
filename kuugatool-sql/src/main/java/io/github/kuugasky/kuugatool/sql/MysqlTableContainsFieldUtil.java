package io.github.kuugasky.kuugatool.sql;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbConnectParam;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbType;

import java.sql.*;
import java.util.List;

/**
 * MysqlTableContainsFieldUtil
 *
 * @author kuuga
 * @since 2022/7/8
 */
public class MysqlTableContainsFieldUtil {

    private final static String INFORMATION_SCHEMA = "kuuga_house_pre";
    private final static String MYSQL_DB_URL = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/" + INFORMATION_SCHEMA + "?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
    private final static String MYSQL_DB_USERNAME = "root";
    private final static String MYSQL_DB_PASSWORD = "kfang.com";
    private final static String FIELD_NAME = "FGARDEN_ID";

    public static void main(String[] args) throws SQLException {
        List<String> schemas = ListUtil.newArrayList();
        schemas.add("kuuga_complaint");
        schemas.add("kuuga_customer");
        schemas.add("kuuga_dts");
        schemas.add("kuuga_house");
        schemas.add("kuuga_house_pre");
        schemas.add("kuuga_monitor");
        schemas.add("kuuga_oms");
        schemas.add("kuuga_photographer");
        schemas.add("kuuga_report");
        schemas.add("dts_infra_dict");
        schemas.add("dts_infra_oa");
        schemas.add("tuofangbao_dts");
        schemas.add("tuofangbao_house_meta");
        schemas.add("tuofangbao_jms");
        schemas.add("tuofangbao_oms");
        schemas.add("tuokeben_customer");
        schemas.add("tuokeben_dts");
        schemas.add("tuokeben_house");
        schemas.add("tuokeben_jms");

        List<String> tableNames = ListUtil.newArrayList();

        Connection jdbcConnect = DbConnectionUtil.getConnect(DbType.MYSQL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD, MYSQL_DB_URL);

        for (String schema : schemas) {
            oneDbScanner(schema, tableNames);

            DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, MYSQL_DB_URL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD);
            // DbConnectionUtil.getAllSchemeInfo(dbConnectParam, schema, )

        }
        // CollectionUtil.forEach(tableNames, (i, tableName) -> System.out.printf("[%s] [%s]%n", i, tableName));

        // CollectionUtil.forEach(tableNames, (i, tableName) -> {
        //     System.out.printf("[%s] select count(1) from %s where FGARDEN_ID in('7791ab4b65f748518f7a9dff820db07a', 'a6c28ccee9c44cc08988e13149b7fa3c');%n", i, tableName);
        // });
    }

    private static void oneDbScanner(String schema, List<String> tableNames) throws SQLException {
        Connection conn = getJdbcConnect();

        if (conn == null) {
            throw new RuntimeException("获得数据库连接失败");
        }
        System.out.println("获得数据库连接...DONE");

        System.out.println("开始获得表结构信息...");
        PreparedStatement pstmtOfSchema;
        PreparedStatement pstmtOfTable;
        ResultSet rsOfSchema;
        ResultSet rsOfTable;

        // 第三步，创建PreparedStatement
        String sql = String.format("select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA='%s';", schema);
        pstmtOfSchema = conn.prepareStatement(sql);
        rsOfSchema = pstmtOfSchema.executeQuery();

        while (rsOfSchema.next()) {
            String tableName = rsOfSchema.getString("TABLE_NAME");
            String sqlOfTable = String.format("select * from information_schema.columns where TABLE_SCHEMA='%s' and table_name='%s' order by ordinal_position", schema, tableName);
            pstmtOfTable = conn.prepareStatement(sqlOfTable);
            rsOfTable = pstmtOfTable.executeQuery();
            while (rsOfTable.next()) {
                String columnName = rsOfTable.getString("COLUMN_NAME");
                if (FIELD_NAME.equals(columnName)) {
                    tableNames.add(schema + "." + tableName);
                }
            }
        }
    }

    private static Connection getJdbcConnect() {
        Connection conn = null;
        try {
            // 第一步，加载数据库驱动
            Class.forName(DbType.MYSQL.getDriver());
            // 第二步，取得数据库连接
            conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return conn;
    }


}
