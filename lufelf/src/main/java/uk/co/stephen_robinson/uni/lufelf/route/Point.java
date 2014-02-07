package uk.co.stephen_robinson.uni.lufelf.route;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by James on 06/02/2014.
 */
public class Point {
    private String name="";
    private double latitude=0;
    private double longitude=0;
    public Point(){

    }
    public Point(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LatLng getLatLng(){
        return new LatLng(latitude,longitude);
    }
}
