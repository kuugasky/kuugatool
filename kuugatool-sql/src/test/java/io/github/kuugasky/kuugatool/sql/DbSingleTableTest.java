package io.github.kuugasky.kuugatool.sql;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbConnectParam;
import io.github.kuugasky.kuugatool.sql.scheme.entity.DbType;
import io.github.kuugasky.kuugatool.sql.scheme.entity.FieldModel;
import io.github.kuugasky.kuugatool.sql.scheme.entity.TableInfo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * DbSingleTableUtil
 *
 * @author kuuga
 * @since 2022/6/9
 */
class DbSingleTableTest {

    static void main(String[] args) throws Exception {
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/agent_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        String username = "root";
        String password = "kfang.com";

        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, url, username, password);

        TableInfo allTableInfo = DbTableUtil.getTableInfo(dbConnectParam, "agent_house", "t_house");

        String tableDesc = allTableInfo.getTableDesc();
        System.out.println(tableDesc);
        List<FieldModel> defList = allTableInfo.getFieldModels();

        Map<String, String> defMap = MapUtil.newHashMap();

        defList.forEach(def -> {
            String columnName = def.getColumnName();
            String columnComment = def.getColumnComment();
            defMap.put(columnName, columnComment);
        });

        System.out.println(MapUtil.toString(defMap, true));
    }

    @Test
    void scan() throws Exception {
        String url = "jdbc:mysql:replication://10.210.10.155:3306,10.210.10.155:3306/agent_house?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&autoReconnect=true";
        String username = "root";
        String password = "kfang.com";

        DbConnectParam dbConnectParam = new DbConnectParam(DbType.MYSQL, url, username, password);

        TableInfo allTableInfo = DbTableUtil.getTableInfo(dbConnectParam, "agent_house", "t_house_log");
        System.out.println(allTableInfo.getTableDesc());
        List<FieldModel> defList = allTableInfo.getFieldModels();

        Map<String, String> defMap = MapUtil.newHashMap();
        for (FieldModel def : defList) {
            defMap.put(def.getColumnName(), def.getColumnComment());
        }
        System.out.println(MapUtil.toString(defMap, true));
    }

}
