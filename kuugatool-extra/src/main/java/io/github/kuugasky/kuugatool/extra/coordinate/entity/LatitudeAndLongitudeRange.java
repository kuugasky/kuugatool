package io.github.kuugasky.kuugatool.extra.coordinate.entity;

/**
 * 经纬度范围
 *
 * @author kuuga
 */
public final class LatitudeAndLongitudeRange {

    /**
     * 最小经度
     */
    private double minLongitude;

    /**
     * 最大经度
     */
    private double maxLongitude;

    /**
     * 最小纬度
     */
    private double minLatitude;

    /**
     * 最大纬度
     */
    private double maxLatitude;


    public double getMinLongitude() {
        return this.minLongitude;
    }

    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public double getMaxLongitude() {
        return this.maxLongitude;
    }

    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public double getMinLatitude() {
        return this.minLatitude;
    }

    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public double getMaxLatitude() {
        return this.maxLatitude;
    }

    public void setMaxLatitude(double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }
}