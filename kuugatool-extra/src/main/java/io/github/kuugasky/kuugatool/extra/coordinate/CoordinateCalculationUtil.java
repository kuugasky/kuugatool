package io.github.kuugasky.kuugatool.extra.coordinate;


import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.extra.coordinate.entity.LatitudeAndLongitude;
import io.github.kuugasky.kuugatool.extra.coordinate.entity.LatitudeAndLongitudeRange;

import java.util.List;

/**
 * 最大和最小经纬度坐标计算
 *
 * @author kuuga
 */
public final class CoordinateCalculationUtil {

    private CoordinateCalculationUtil() {
    }

    /**
     * 地球半径
     */
    private static final double EARTH_RADIUS = 6371.393;

    /**
     * 根据经纬计算指定公里范围经纬的最大最小值
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param kilometer 多少公里
     * @return 经纬度范围对象
     */
    public static LatitudeAndLongitudeRange getRange(double longitude, double latitude, int kilometer) {
        // 里面的 1 就代表搜索 1km 之内，单位km
        final double range = 180 / Math.PI * kilometer / EARTH_RADIUS;
        final double lngR = range / Math.cos(latitude * Math.PI / 180);
        // 最大纬度
        double maxLat = latitude + range;
        // 最小纬度
        double minLat = latitude - range;
        // 最大经度
        double maxLng = longitude + lngR;
        // 最小经度
        double minLng = longitude - lngR;

        LatitudeAndLongitudeRange rangeBean = new LatitudeAndLongitudeRange();
        rangeBean.setMinLongitude(minLng);
        rangeBean.setMaxLongitude(maxLng);
        rangeBean.setMinLatitude(minLat);
        rangeBean.setMaxLatitude(maxLat);

        return rangeBean;
    }

    /**
     * 根据经纬计算指定公里范围经纬的最大最小值
     *
     * @param latitudeAndLongitude 经纬度对象
     * @param kilometer            多少公里
     * @return 经纬度范围对象
     */
    public static LatitudeAndLongitudeRange getRange(LatitudeAndLongitude latitudeAndLongitude, int kilometer) {
        return getRange(latitudeAndLongitude.getLongitude(), latitudeAndLongitude.getLatitude(), kilometer);
    }

    /**
     * 根据经纬计算指定公里范围经纬的最大最小值
     *
     * @param latitudeAndLongitudes 经纬度对象集合
     * @param kilometer             多少公里
     * @return 经纬度范围对象集合
     */
    public static List<LatitudeAndLongitudeRange> queryRange(List<LatitudeAndLongitude> latitudeAndLongitudes, int kilometer) {
        if (ListUtil.isEmpty(latitudeAndLongitudes)) {
            return ListUtil.emptyList();
        }
        List<LatitudeAndLongitudeRange> result = ListUtil.newArrayList();
        latitudeAndLongitudes.forEach(latitudeAndLongitude -> result.add(getRange(latitudeAndLongitude, kilometer)));
        return result;
    }

    public static void main(String[] args) {
        // sql demo
        /*
        select *
        from syzl_garden.t_garden_basic t
        where
          (t.flongitude >= '114.01586699540523' and t.flongitude <= '114.11323700459478'
           and t.flatitude >= '22.503492693299975' and t.flatitude <= '22.593419306700028')

          or (t.flongitude >= '114.01586699540523' and t.flongitude <= '114.11323700459478'
              and t.flatitude >= '22.503492693299975' and t.flatitude <= '22.593419306700028')

          or (t.flongitude >= '114.01586699540523' and t.flongitude <= '114.11323700459478'
              and t.flatitude >= '22.503492693299975' and t.flatitude <= '22.593419306700028');
         */

        LatitudeAndLongitudeRange range = getRange(114.0645520000, 22.5484560000, 5);
        System.out.println(StringUtil.formatString(range));

        System.out.println("------------------");

        List<LatitudeAndLongitude> param = ListUtil.newArrayList();
        param.add(new LatitudeAndLongitude(114.0645520000, 22.5484560000));
        param.add(new LatitudeAndLongitude(114.0645520000, 22.5484560000));
        param.add(new LatitudeAndLongitude(114.0645520000, 22.5484560000));
        param.add(new LatitudeAndLongitude(114.0645520000, 22.5484560000));

        List<LatitudeAndLongitudeRange> latitudeAndLongitudeRanges = queryRange(param, 5);
        latitudeAndLongitudeRanges.forEach(bean -> System.out.println(StringUtil.formatString(bean)));

    }

}