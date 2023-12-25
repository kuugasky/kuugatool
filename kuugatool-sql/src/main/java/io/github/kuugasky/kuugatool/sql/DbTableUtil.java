package io.github.kuugasky.kuugatool.sql;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.numerical.BigDecimalUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbConnectParam;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbType;
import io.github.kuugasky.kuugatool.sql.scheme.entity.FieldModel;
import io.github.kuugasky.kuugatool.sql.scheme.entity.TableInfo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DbTableUtil
 *
 * @author kuuga
 * @since 2022/7/8
 */
public final class DbTableUtil {

    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String COMMENTS = "COMMENTS";
    private static final String DATA_LENGTH = "DATA_LENGTH";
    private static final String INDEX_LENGTH = "INDEX_LENGTH";
    private static final String DATA_PRECISION = "DATA_PRECISION";
    private static final String DATA_SCALE = "DATA_SCALE";
    private static final String NULLABLE = "NULLABLE";
    private static final String TABLE_COMMENT = "TABLE_COMMENT";
    private static final String COLUMN_KEY = "COLUMN_KEY";
    private static final String COLUMN_COMMENT = "COLUMN_COMMENT";
    private static final String IS_NULLABLE = "IS_NULLABLE";
    private static final String COLUMN_TYPE = "COLUMN_TYPE";
    private static final String PRI = "PRI";
    private static final String YES = "YES";
    private static final String Y = "Y";
    private static final String ENGINE = "ENGINE";
    private static final String TABLE_ROWS = "TABLE_ROWS";
    private static final String CREATE_TIME = "CREATE_TIME";
    private static final String UPDATE_TIME = "UPDATE_TIME";

    /**
     * 遍历schema，过滤出所有表字段包含fieldName的表结构信息集合
     *
     * @param dbConnectParam db连接
     * @param schemaName     schema名
     * @param fieldName      字段名
     * @return 表结构信息集合
     * @throws SQLException sql异常
     */
    public static List<TableInfo> queryTableInfosByContainsField(DbConnectParam dbConnectParam, String schemaName, String fieldName) throws SQLException {
        return queryTableInfosByContainsField(dbConnectParam, schemaName, fieldName, false);
    }

    /**
     * 遍历schema，过滤出所有表字段包含fieldName的表结构信息集合
     *
     * @param dbConnectParam   db连接
     * @param schemaName       schema名
     * @param fieldName        字段名
     * @param equalsIgnoreCase 字段比对忽略大小写
     * @return 表结构信息集合
     * @throws SQLException sql异常
     */
    public static List<TableInfo> queryTableInfosByContainsField(DbConnectParam dbConnectParam, String schemaName, String fieldName, boolean equalsIgnoreCase) throws SQLException {
        List<TableInfo> result = ListUtil.newArrayList();

        List<TableInfo> tableInfos = queryTableInfos(dbConnectParam, schemaName);
        if (ListUtil.isEmpty(tableInfos)) {
            return result;
        }
        tableInfos.forEach(tableInfo -> {
            List<FieldModel> fieldModels = tableInfo.getFieldModels();
            long containsCount;
            if (equalsIgnoreCase) {
                containsCount = ListUtil.optimize(fieldModels).stream().filter(fieldModel -> fieldModel.getColumnName().equalsIgnoreCase(fieldName)).count();
            } else {
                containsCount = ListUtil.optimize(fieldModels).stream().filter(fieldModel -> fieldModel.getColumnName().equals(fieldName)).count();
            }
            if (containsCount > 0) {
                result.add(tableInfo);
            }
        });
        return result;
    }

    /**
     * 获取指定schema的所有表结构信息集合
     *
     * @param dbConnectParam 数据库连接信息
     * @param schemaName     schema名称
     * @return 表结构信息结合
     * @throws SQLException sql异常
     */
    @SuppressWarnings("all")
    public static List<TableInfo> queryTableInfos(DbConnectParam dbConnectParam, String schemaName) throws SQLException {
        Connection connection = DbConnectionUtil.getConnect(dbConnectParam);
        try {
            PreparedStatement preparedStatement;
            ResultSet rsOfSchema;

            String sql = String.format("select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA='%s';", schemaName);
            preparedStatement = connection.prepareStatement(sql);
            rsOfSchema = preparedStatement.executeQuery();

            List<TableInfo> tableInfos = ListUtil.newArrayList();

            while (rsOfSchema.next()) {
                String tableName = rsOfSchema.getString(TABLE_NAME);
                connection = DbConnectionUtil.getConnect(dbConnectParam);
                TableInfo info = getTableInfo(connection, dbConnectParam.dbType(), schemaName, tableName);
                tableInfos.add(info);
            }
            return tableInfos;
        } finally {
            DbConnectionUtil.closeConnect(connection);
        }
    }

    /**
     * 获取指定tableName的所有表结构信息
     *
     * @param dbConnectParam 数据库连接信息
     * @param schemaName     schema名称
     * @param tableName      表名
     * @return 表结构信息结合
     * @throws SQLException sql异常
     */
    public static TableInfo getTableInfo(DbConnectParam dbConnectParam, String schemaName, String tableName) throws SQLException {
        Connection connection = DbConnectionUtil.getConnect(dbConnectParam);
        return getTableInfo(connection, dbConnectParam.dbType(), schemaName, tableName);
    }

    /**
     * 获取指定tableName的所有表结构信息
     *
     * @param connection db连接
     * @param schemaName schema名称
     * @param tableName  表名
     * @return 表结构信息结合
     * @throws SQLException sql异常
     */
    private static TableInfo getTableInfo(Connection connection, DbType dbType, String schemaName, String tableName) throws SQLException {
        try {
            TableInfo info = new TableInfo();

            // 第三步，创建PreparedStatement
            if (ObjectUtil.equals(DbType.MYSQL, dbType)) {
                mysqlHandler(connection, schemaName, tableName, info);
            } else if (ObjectUtil.equals(DbType.ORACLE, dbType)) {
                oracleHandler(connection, tableName, info);
            }
            return info;
        } finally {
            DbConnectionUtil.closeConnect(connection);
        }
    }

    /**
     * mysql表信息组装
     *
     * @param connection db连接
     * @param schemaName schema名
     * @param tableName  表名
     * @param tableInfo  表结构信息
     * @throws SQLException sql异常
     */
    private static void mysqlHandler(Connection connection, String schemaName, String tableName, TableInfo tableInfo) throws SQLException {
        List<FieldModel> defs = new ArrayList<>();
        tableInfo.setFieldModels(defs);

        String sql = String.format("select TABLE_NAME, TABLE_COMMENT, ENGINE, TABLE_ROWS, CREATE_TIME, UPDATE_TIME, DATA_LENGTH, INDEX_LENGTH " +
                "from information_schema.TABLES where TABLE_SCHEMA='%s' and table_name='%s'", schemaName, tableName);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            tableInfo.setTableName(rs.getString(TABLE_NAME));
            tableInfo.setTableDesc(rs.getString(TABLE_COMMENT));
            tableInfo.setEngine(rs.getString(ENGINE));
            tableInfo.setTableRows(rs.getLong(TABLE_ROWS));
            tableInfo.setDataLength(rs.getLong(DATA_LENGTH));
            tableInfo.setDataCapacity(capacityCalculate(tableInfo.getDataLength()));
            tableInfo.setIndexLength(rs.getLong(INDEX_LENGTH));
            tableInfo.setIndexCapacity(capacityCalculate(tableInfo.getIndexLength()));
            tableInfo.setCreateTime(rs.getDate(CREATE_TIME));
            tableInfo.setUpdateTime(rs.getDate(UPDATE_TIME));
        }

        sql = String.format("select * from information_schema.columns where TABLE_SCHEMA='%s' and table_name='%s' order by ordinal_position", schemaName, tableName);
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();
        while (rs.next()) {
            FieldModel def = new FieldModel();
            def.setDataType(rs.getString(DATA_TYPE));
            def.setColumnName(rs.getString(COLUMN_NAME));
            def.setPrimaryKey(PRI.equals(rs.getString(COLUMN_KEY)));
            def.setColumnComment(rs.getString(COLUMN_COMMENT));
            def.setCanNull(YES.equals(rs.getString(IS_NULLABLE)));
            def.setColumnType(rs.getString(COLUMN_TYPE));
            def.setIndexKey(StringUtil.hasText(rs.getString(COLUMN_KEY)));
            defs.add(def);
        }
        DbConnectionUtil.closeJdbcStatement(statement, rs);
    }

    private static String capacityCalculate(long dataLength) {
        BigDecimal divide = BigDecimalUtil.divide(BigDecimal.valueOf(dataLength).doubleValue(), 1024, 1024);
        return BigDecimalUtil.round(divide.doubleValue(), 2) + "MB";
    }

    /**
     * oracle表信息组装
     *
     * @param connection db连接
     * @param tableName  表名
     * @param tableInfo  表结构信息
     * @throws SQLException sql异常
     */
    private static void oracleHandler(Connection connection, String tableName, TableInfo tableInfo) throws SQLException {
        List<FieldModel> defs = new ArrayList<>();
        tableInfo.setFieldModels(defs);

        String sql = String.format("select COMMENTS from user_tab_comments where table_name='%s' ", tableName);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            tableInfo.setTableDesc(rs.getString(COMMENTS));
        }
        sql = String.format("""
                        select
                            utc.DATA_LENGTH,
                            utc.DATA_PRECISION,
                            utc.DATA_SCALE,
                            utc.NULLABLE,
                            utc.DATA_TYPE,
                            utc.COLUMN_NAME,
                            utc.COLUMN_ID,
                            ucc.COMMENTS
                        from user_tab_columns utc
                        left join user_col_comments ucc on utc.table_name = ucc.table_name and utc.column_name = ucc.column_name
                        where
                         utc.table_name = '%s' order by utc.COLUMN_ID
                        """,
                tableName);
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();
        Map<String, FieldModel> middle = new HashMap<>(16);
        while (rs.next()) {
            FieldModel def = new FieldModel();
            def.setDataType(rs.getString(DATA_TYPE));
            def.setColumnName(rs.getString(COLUMN_NAME));
            def.setColumnComment(rs.getString(COMMENTS));

            String dataLength = rs.getString(DATA_LENGTH);
            String dataPrecision = rs.getString(DATA_PRECISION);
            String dataScale = rs.getString(DATA_SCALE);

            if (StringUtil.hasText(dataPrecision)) {
                def.setColumnType(def.getDataType() + "(" + dataPrecision + "," + dataScale + ")");
            } else {
                def.setColumnType(def.getDataType() + "(" + dataLength + ")");
            }
            def.setCanNull(Y.equals(rs.getString(NULLABLE)));

            defs.add(def);
            middle.put(def.getColumnName(), def);
        }
        sql = String.format("select t.*,i.index_type from user_ind_columns t,user_indexes i \n" +
                "where t.index_name = i.index_name and t.table_name = i.table_name and t.table_name = '%s'", tableName);
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();
        while (rs.next()) {
            String column = rs.getString(COLUMN_NAME);
            FieldModel def = middle.get(column);
            if (def != null) {
                def.setIndexKey(true);
            }
        }
        sql = String.format("select cu.* from user_cons_columns cu, user_constraints au \n" +
                "where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name = '%s'", tableName);
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();
        while (rs.next()) {
            String column = rs.getString(COLUMN_NAME);
            FieldModel def = middle.get(column);
            if (def != null) {
                def.setPrimaryKey(true);
            }
        }
        DbConnectionUtil.closeJdbcStatement(statement, rs);
    }

}
