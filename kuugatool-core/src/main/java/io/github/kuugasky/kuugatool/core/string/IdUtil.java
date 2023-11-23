package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.snow.SnowflakeIdUtil;

import java.util.UUID;

/**
 * IdUtil
 *
 * @author kuuga
 */
public class IdUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private IdUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 集群注意分布式事务
     */
    private static long num = 0;

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    @SuppressWarnings("all")
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     * @since 4.1.19
     */
    @SuppressWarnings("all")
    public static String fastSimpleUUID() {
        return io.github.kuugasky.kuugatool.core.lang.UUID.fastUUID().toString(true);
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    @SuppressWarnings("all")
    public static String simpleUUID() {
        return randomUUID().replace("-", StringUtil.EMPTY);
    }

    /**
     * 根据字符串生成固定UUID
     * <p>
     * 静态工厂，根据指定的字节数组检索类型3（基于名称的）UUID。
     */
    @SuppressWarnings("all")
    public static synchronized String randomUUID(String name) {
        UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
        String str = uuid.toString();
        return str.replace("-", StringUtil.EMPTY);
    }

    /**
     * 根据日期生成长整型id
     */
    public static synchronized long getLongId() {
        String date = String.valueOf(DateUtil.getTimeMillis(DateUtil.now()));
        if (num >= KuugaConstants.NINETY_NINE) {
            num = 0L;
        }
        ++num;
        if (num < KuugaConstants.TEN) {
            date = date + "00" + num;
        } else {
            date += num;
        }
        return Long.parseLong(date);
    }

    public static long getSnowflakeId() {
        return SnowflakeIdUtil.getSnowflakeId();
    }

}