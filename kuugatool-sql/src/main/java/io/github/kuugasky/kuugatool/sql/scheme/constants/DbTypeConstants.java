package io.github.kuugasky.kuugatool.sql.scheme.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * DB类型常量
 *
 * @author kuuga
 */
public final class DbTypeConstants {

    private static final String LONG = "Long";
    private static final String DATE = "Date";
    private static final String STRING = "String";
    private static final String INTEGER = "Integer";
    private static final String BIG_DECIMAL = "BigDecimal";

    public static final Map<String, String> MYSQL_TYPE_MAP = new HashMap<>(16);
    public static final Map<String, String> ORACLE_TYPE_MAP = new HashMap<>(16);

    static {
        MYSQL_TYPE_MAP.put("TINYINT", INTEGER);
        MYSQL_TYPE_MAP.put("SMALLINT", INTEGER);
        MYSQL_TYPE_MAP.put("MEDIUMINT", INTEGER);
        MYSQL_TYPE_MAP.put("INT", INTEGER);
        MYSQL_TYPE_MAP.put("INTEGER", INTEGER);

        MYSQL_TYPE_MAP.put("BIGINT", LONG);

        MYSQL_TYPE_MAP.put("FLOAT", BIG_DECIMAL);
        MYSQL_TYPE_MAP.put("DOUBLE", BIG_DECIMAL);
        MYSQL_TYPE_MAP.put("DECIMAL", BIG_DECIMAL);

        MYSQL_TYPE_MAP.put("DATE", DATE);
        MYSQL_TYPE_MAP.put("TIME", DATE);
        MYSQL_TYPE_MAP.put("YEAR", DATE);
        MYSQL_TYPE_MAP.put("DATETIME", DATE);
        MYSQL_TYPE_MAP.put("TIMESTAMP", DATE);

        MYSQL_TYPE_MAP.put("CHAR", STRING);
        MYSQL_TYPE_MAP.put("VARCHAR", STRING);
        MYSQL_TYPE_MAP.put("TINYBLOB", STRING);
        MYSQL_TYPE_MAP.put("TINYTEXT", STRING);
        MYSQL_TYPE_MAP.put("BLOB", STRING);
        MYSQL_TYPE_MAP.put("TEXT", STRING);
        MYSQL_TYPE_MAP.put("MEDIUMBLOB", STRING);
        MYSQL_TYPE_MAP.put("MEDIUMTEXT", STRING);
        MYSQL_TYPE_MAP.put("LONGBLOB", STRING);
        MYSQL_TYPE_MAP.put("LONGTEXT", STRING);
    }

    static {
        ORACLE_TYPE_MAP.put("TIMESTAMP(6)", DATE);
        ORACLE_TYPE_MAP.put("TIMESTAMP", DATE);
        ORACLE_TYPE_MAP.put("DATE", DATE);

        ORACLE_TYPE_MAP.put("FLOAT", BIG_DECIMAL);
        ORACLE_TYPE_MAP.put("NUMBER", BIG_DECIMAL);

        ORACLE_TYPE_MAP.put("LONG", STRING);
        ORACLE_TYPE_MAP.put("CLOB", STRING);
        ORACLE_TYPE_MAP.put("CHAR", STRING);
        ORACLE_TYPE_MAP.put("UNDEFINED", STRING);
        ORACLE_TYPE_MAP.put("NCLOB", STRING);
        ORACLE_TYPE_MAP.put("VARCHAR2", STRING);
        ORACLE_TYPE_MAP.put("BLOB", STRING);
    }

}