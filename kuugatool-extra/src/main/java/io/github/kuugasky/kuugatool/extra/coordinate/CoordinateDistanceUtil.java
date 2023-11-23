package io.github.kuugasky.kuugatool.extra.coordinate;

/**
 * 两个经纬度坐标距离计算工具类
 *
 * @author kuuga
 */
public final class CoordinateDistanceUtil {

    /**
     * 地球半径
     */
    private static final double EARTH_RADIUS = 6371008.7714000001D;
    // private static final double EARTH_RADIUS = 6378.137;

    /**
     * 圆周率
     */
    public final static double PI = Math.PI;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    // /**
    //  * 通过经纬度获取距离(单位：米)
    //  *
    //  * @param lat1 坐标1一纬度
    //  * @param lng1 坐标1——经度
    //  * @param lat2 坐标2一纬度
    //  * @param lng2 坐标2——经度
    //  * @return 距离
    //  */
    // public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
    //     double radLat1 = rad(lat1);
    //     double radLat2 = rad(lat2);
    //     double a = radLat1 - radLat2;
    //     double b = rad(lng1) - rad(lng2);
    //     double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
    //             + Math.cos(radLat1) * Math.cos(radLat2)
    //             * Math.pow(Math.sin(b / 2), 2)));
    //     s = s * EARTH_RADIUS;
    //     s = Math.round(s * 10000d) / 10000d;
    //     s = s * 1000;
    //     return s;
    // }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param longt1 坐标1——经度
     * @param lat1   坐标1一纬度
     * @param longt2 坐标2——经度
     * @param lat2   坐标2一纬度
     * @return 距离
     */
    public static double getDistance(double longt1, double lat1, double longt2, double lat2) {

        double x, y, distance;

        x = (longt2 - longt1) * PI * EARTH_RADIUS * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;

        y = (lat2 - lat1) * PI * EARTH_RADIUS / 180;

        distance = Math.hypot(x, y);

        return distance;

    }

    /**
     * 计算是否在距离内
     *
     * @return boolean
     */
    public static boolean innerDistance(double longt1, double lat1, double longt2, double lat2, int range) {
        return getDistance(longt1, lat1, longt2, lat2) <= range;
    }

    // 114.0645520000	22.5484560000	114.0676174838	22.5404325928
    // 114.0645520000	22.5484560000	114.0669778004	22.5542606669
    // 114.0645520000	22.5484560000	114.0682320146	22.5461909003
    // 114.0645520000	22.5484560000	114.0598004293	22.5454326141
    // 114.0645520000	22.5484560000	114.0562134055	22.5526132576
    // 114.0645520000	22.5484560000	114.0612458028	22.5403392115

    public static void main(String[] args) {
        double distance = getDistance(114.0645520000, 22.5484560000, 114.0612458028, 22.5403392115);
        System.out.println("距离" + distance / 1000 + "公里");
    }

}
