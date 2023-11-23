package io.github.kuugasky.kuugatool.extra.coordinate.entity;

/**
 * 经纬度
 *
 * @author kuuga
 */
public final class LngLat {

    /**
     * 经度
     */
    private double longitude;
    /**
     * 维度
     */
    private double latitude;

    public LngLat() {
    }

    public LngLat(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "LngLat{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
