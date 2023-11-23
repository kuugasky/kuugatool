package io.github.kuugasky.kuugatool.extra.coordinate.entity;

/**
 * 经纬度
 *
 * @author kuuga
 */
public final class LatitudeAndLongitude {

    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    public LatitudeAndLongitude() {
    }

    public LatitudeAndLongitude(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}