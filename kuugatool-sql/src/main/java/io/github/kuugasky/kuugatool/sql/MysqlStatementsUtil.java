package io.github.kuugasky.kuugatool.sql;

import com.alibaba.druid.DbType;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

/**
 * MysqlStatementsUtil
 *
 * @author kuuga
 * @since 2022-06-07
 */
public final class MysqlStatementsUtil {

    private static final DbType MYSQL = DbType.mysql;

    /**
     * 是有有效的sql语句
     *
     * @param sql sql
     * @return boolean
     */
    public static boolean isValid(String sql) {
        return SqlStatementsUtil.isValid(sql, MYSQL);
    }

    /**
     * sql格式化
     *
     * @param sql sql
     * @return sql
     */
    public static String sqlFormat(String sql) {
        return SqlStatementsUtil.toSqlString(sql, MYSQL, true);
    }

    /**
     * sql格式化并转换为大写关键字
     *
     * @param sql sql
     * @return sql
     */
    public static String sqlFormatUpperCase(String sql) {
        return SqlStatementsUtil.toSqlString(sql, MYSQL, true, true);
    }

    /**
     * sql转换为大写关键字
     *
     * @param sql sql
     * @return sql
     */
    public static String sqlUpperCase(String sql) {
        return SqlStatementsUtil.toSqlString(sql, MYSQL, false, true);
    }

    /**
     * sql简化
     *
     * @param sql sql
     * @return sql
     */
    public static String sqlSimplify(String sql) {
        return sqlSimplify(sql, false);
    }

    /**
     * sql简化并移除单引号
     *
     * @param sql             sql
     * @param removeBackQuote 移除单引号
     * @return sql
     */
    public static String sqlSimplify(String sql, boolean removeBackQuote) {
        String sqlString = SqlStatementsUtil.toSqlString(sql, MYSQL, false, true);
        if (removeBackQuote) {
            return sqlString.replaceAll(KuugaConstants.BACKQUOTE, StringUtil.EMPTY);
        }
        return sqlString;
    }

}
