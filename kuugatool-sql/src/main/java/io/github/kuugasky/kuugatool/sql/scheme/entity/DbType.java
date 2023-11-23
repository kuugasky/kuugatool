package io.github.kuugasky.kuugatool.sql.scheme.entity;

import io.github.kuugasky.kuugatool.sql.scheme.constants.DbTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * DB类型
 *
 * @author kuuga
 */
@AllArgsConstructor
public enum DbType {

    /**
     *
     */
    MYSQL("com.mysql.cj.jdbc.Driver", DbTypeConstants.MYSQL_TYPE_MAP),
    ORACLE("oracle.jdbc.driver.OracleDriver", DbTypeConstants.ORACLE_TYPE_MAP),
    ;

    @Getter
    private final String driver;
    @Getter
    private final Map<String, String> typeMap;

}