package io.github.kuugasky.kuugatool.extra.coordinate;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.regex.RegexUtil;

import java.math.BigDecimal;

/**
 * 坐标工具类
 * 经纬度查询：https://jingweidu.bmcx.com/
 *
 * @author kuuga
 * @since 2021/4/6
 */
public class CoordinateUtil {

    /*
     * 原正则仅支持小数点后6位
     * //经度
     * public static final String LNG_PATTERN = "^[\\-\\+]?(0(\\.\\d{1,6})?|([1-9](\\d)?)(\\.\\d{1,6})?|1[0-7]\\d{1}(\\.\\d{1,6})?|180(\\.0{1,6})?)$";
     * //纬度
     * public static final String LAT_PATTERN = "^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,6})?|90(\\.0{1,6})?)$";
     */

    /**
     * 经度正则
     * 经度坐标的范围：-180 ~ 180 其中，负坐标代表西半球，正坐标代表东半球
     */
    public static final String LNG_PATTERN = "^[\\-\\+]?(0(\\.\\d{1,6})?|([1-9](\\d)?)(\\.\\d{1,8})?|1[0-7]\\d{1}(\\.\\d{1,8})?|180(\\.0{1,8})?)$";
    /**
     * 维度正则
     * 纬度坐标的范围：-90 ~ 90 其中，负坐标代表南半球，正坐标代表北半球
     */
    public static final String LAT_PATTERN = "^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,8})?|90(\\.0{1,8})?)$";

    /**
     * 是否有效经度
     *
     * @param longitude 经度
     * @return 是否有效
     */
    public static boolean isValidLongitude(BigDecimal longitude) {
        if (ObjectUtil.isNull(longitude)) {
            return false;
        }
        String longitudeStr = longitude.toPlainString();
        return RegexUtil.isMatch(LNG_PATTERN, longitudeStr);
    }

    /**
     * 是否有效维度
     *
     * @param latitude 维度
     * @return 是否有效
     */
    public static boolean isValidLatitude(BigDecimal latitude) {
        if (ObjectUtil.isNull(latitude)) {
            return false;
        }
        String latitudeStr = latitude.toPlainString();
        return RegexUtil.isMatch(LAT_PATTERN, latitudeStr);
    }

    /**
     * 经纬度校验
     *
     * @return 是否有效
     */
    public static boolean isValidLatitudeAndLongitude(BigDecimal longitude, BigDecimal latitude) {
        if (ObjectUtil.containsNull(longitude, latitude)) {
            return false;
        }
        return isValidLongitude(longitude) && isValidLatitude(latitude);
    }

    public static void main(String[] args) {
        BigDecimal longitude = new BigDecimal("113.88308");
        BigDecimal latitude = new BigDecimal("22.55329");
        // BigDecimal longitude = new BigDecimal("114.06427120");
        // BigDecimal latitude = new BigDecimal("22.54880640");
        System.out.println(isValidLongitude(longitude));
        System.out.println(isValidLongitude(latitude));
        System.out.println(isValidLatitudeAndLongitude(longitude, latitude));
        System.out.println(isValidLatitudeAndLongitude(latitude, longitude));
    }

}
