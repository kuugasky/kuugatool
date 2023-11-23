package io.github.kuugasky.kuugatool.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import io.github.kuugasky.kuugatool.sql.exception.SqlParseException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * SqlStatementsUtil
 *
 * @author kuuga
 * @since 2022-06-07
 */
@Slf4j
public final class SqlStatementsUtil {

    // SQL语法校验 ================================================================================================================================================================

    /**
     * 检测是否有效的sql语句
     *
     * @param sql sql
     * @return boolean
     */
    public static boolean isValid(String sql, DbType dbType) {
        try {
            queryStatementList(sql, dbType);
            return true;
        } catch (Exception e) {
            log.error("sql parse error:{}", e.getMessage(), e);
        }
        return false;
    }

    // SQL语句输出 ================================================================================================================================================================

    public static String toSqlString(String sql, DbType dbType) {
        return toSqlString(sql, dbType, false);
    }

    public static String toSqlString(String sql, DbType dbType, boolean prettyFormat) {
        return toSqlString(sql, dbType, prettyFormat, false);
    }

    public static String toSqlString(String sql, DbType dbType, boolean prettyFormat, boolean uppCase) {
        return toSqlString(sql, dbType, prettyFormat, uppCase, false);
    }

    public static String toSqlString(String sql, DbType dbType, boolean prettyFormat, boolean uppCase, boolean desensitize) {
        return toSqlString(sql, dbType, prettyFormat, uppCase, desensitize, false);
    }

    public static String toSqlString(String sql, DbType dbType, boolean prettyFormat, boolean uppCase, boolean desensitize, boolean parameterized) {
        List<SQLStatement> sqlStatements = queryStatementList(sql, dbType);
        SQLUtils.FormatOption formatOption = new SQLUtils.FormatOption();
        formatOption.setPrettyFormat(prettyFormat);
        formatOption.setUppCase(uppCase);
        formatOption.setDesensitize(desensitize);
        formatOption.setParameterized(parameterized);
        return SQLUtils.toSQLString(sqlStatements, dbType, formatOption);
    }

    /**
     * 查询语句列表
     *
     * @param sql    sql
     * @param dbType dbType
     * @return statementList
     */
    private static List<SQLStatement> queryStatementList(String sql, DbType dbType) {
        List<SQLStatement> statementList;
        SQLStatementParser parser;
        try {
            parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
            statementList = parser.parseStatementList();
        } catch (ParserException e) {
            throw new SqlParseException("sql parse error:{}", e.getMessage(), e);
        }
        return statementList;
    }

}
