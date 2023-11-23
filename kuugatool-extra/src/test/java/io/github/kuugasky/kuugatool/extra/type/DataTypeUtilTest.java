package io.github.kuugasky.kuugatool.extra.type;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.extra.concurrent.KuugaDTO;
import io.github.kuugasky.kuugatool.json.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class DataTypeUtilTest {

    @Test
    public void isNumeric() {
        // 判断是否是数字类型的
        System.out.println(DataTypeUtil.isNumeric("123456"));
        System.out.println(DataTypeUtil.isNumeric("12345x"));
    }

    @Test
    public void isBoolean() {
        // 判断是否是布尔型的
        System.out.println(DataTypeUtil.isBoolean("true"));
        System.out.println(DataTypeUtil.isBoolean("yes"));
    }

    @Test
    public void isDateTime() {
        System.out.println(DataTypeUtil.isDateTime("2021-01-20", DateFormat.yyyy_MM_dd.format()));
        System.out.println(DataTypeUtil.isDateTime("2021-01-20", DateFormat.yyyy_MM_dd_HH_mm_ss.format()));
    }

    @Test
    public void isArrayNumber() {
        System.out.println(DataTypeUtil.isArrayNumber("[1,2,3,4,5,6]"));
        System.out.println(DataTypeUtil.isArrayNumber("[a,b,c,d,e,f]"));
    }

    @Test
    public void isArray() {
        System.out.println(DataTypeUtil.isArray("[1,2,3,4,5,6]"));
        System.out.println(DataTypeUtil.isArray("['a','b','c','d','e','f']"));

        List<KuugaDTO> list = ListUtil.newArrayList();
        list.add(KuugaDTO.builder().name("kuuga1").sex(0).build());
        list.add(KuugaDTO.builder().name("kuuga2").sex(1).build());

        System.out.println(DataTypeUtil.isArray(JsonUtil.toJsonString(list)));
    }

    @Test
    public void isArrayBoolean() {
        System.out.println(DataTypeUtil.isArrayBoolean("[true]"));
        System.out.println(DataTypeUtil.isArrayBoolean("[yes]"));
    }

    @Test
    public void isArrayDateTime() {
        System.out.println(DataTypeUtil.isArrayDateTime("['2021-01-20','2021-01-21']", DateFormat.yyyy_MM_dd.format()));
        System.out.println(DataTypeUtil.isArrayDateTime("['2021-01-20','2021-01-21']", DateFormat.yyyy_MM_dd_HH_mm_ss.format()));
    }

    @Test
    public void isTimestampSec() {
        long l1 = DateUtil.getTimeSeconds(DateUtil.now());
        System.out.println(l1);
        System.out.println(DataTypeUtil.isTimestampOfSecond(l1 + StringUtil.EMPTY));
        System.out.println(DataTypeUtil.isTimestampOfSecond(l1 + "x"));
    }

    @Test
    public void isTimestampMill() {
        long l1 = DateUtil.getTimeMillis(DateUtil.now());
        System.out.println(l1);
        System.out.println(DataTypeUtil.isTimestampOfSecondMS(l1 + StringUtil.EMPTY));
        System.out.println(DataTypeUtil.isTimestampOfSecondMS(l1 + "x"));
    }

    @Test
    public void getTimestampOfSecond() {
        Date now = DateUtil.now();
        // 获取date的秒级时间戳
        long l1 = DateUtil.getTimeSeconds(now);
        System.out.println(l1);
        long l = System.currentTimeMillis();
        System.out.println(l);
        // 获取date的毫秒级时间戳
        long timestampOfSecondMS = DateUtil.getTimeMillis(now);
        System.out.println(timestampOfSecondMS);
        long time = now.getTime();
        System.out.println(time);
    }

    @Test
    void currentTimeSeconds() {
        // 获取当前时间的秒级时间戳
        System.out.println(DateUtil.currentTimeSeconds());
        // 获取当前时间的毫秒级时间戳
        System.out.println(DateUtil.currentTimeMillis());
        Date now = DateUtil.now();
        // 获取date的秒级时间戳
        System.out.println(DateUtil.getTimeSeconds(now));
        // 获取date的毫秒级时间戳
        System.out.println(DateUtil.getTimeMillis(now));
    }

}