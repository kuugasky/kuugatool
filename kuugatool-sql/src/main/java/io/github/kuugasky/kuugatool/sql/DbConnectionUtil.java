package io.github.kuugasky.kuugatool.sql;

import io.github.kuugasky.kuugatool.sql.scheme.entity.DbConnectParam;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbType;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * DB连接工具类
 *
 * @author kuuga
 */
@Slf4j
public class DbConnectionUtil {

    /**
     * 获得Jdbc连接
     *
     * @param dbConnectParam dbConnectParam
     * @return connection
     */
    public static Connection getConnect(DbConnectParam dbConnectParam) {
        DbType dbType = dbConnectParam.dbType();
        String url = dbConnectParam.url();
        String username = dbConnectParam.username();
        String password = dbConnectParam.password();
        return getConnect(dbType, username, password, url);
    }

    /**
     * 获得Jdbc连接
     *
     * @param dbType   dbType
     * @param username username
     * @param password password
     * @param url      url
     * @return connection
     */
    public static Connection getConnect(DbType dbType, String username, String password, String url) {
        Connection conn = null;
        try {
            // 第一步，加载数据库驱动
            Class.forName(dbType.getDriver());
            // 第二步，取得数据库连接
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return conn;
    }

    /**
     * 关闭Jdbc语句
     *
     * @param preparedStatement 事先准备好的声明
     * @param resultSet         结果集
     */
    public static void closeJdbcStatement(PreparedStatement preparedStatement, ResultSet resultSet) {
        // 遵循从里到外关闭的原则
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 关闭连接
     *
     * @param connection connection
     */
    public static void closeConnect(Connection connection) {
        // 必须关闭Connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
    }

}
