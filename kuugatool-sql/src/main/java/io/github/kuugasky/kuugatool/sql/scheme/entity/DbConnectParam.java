package io.github.kuugasky.kuugatool.sql.scheme.entity;

/**
 * DB链接参数
 *
 * @param dbType   数据库类型
 * @param username 用户名
 * @param password 密码
 * @param url      链接地址
 * @author kuuga
 */
public record DbConnectParam(DbType dbType, String url, String username, String password) {

    /**
     * @param dbType   数据库类型
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param url      数据库连接
     */
    public DbConnectParam {
    }

    @Override
    public String toString() {
        return String.format("数据库连接{dbType='%s', username='%s', password='%s', url='%s'}",
                dbType, username, password, url);
    }

}
