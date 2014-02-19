package uk.co.stephen_robinson.uni.lufelf.route;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author James
 * The point class used to hold the details of a route
 */
public class Point {
    private String name="";
    private double latitude=0;
    private double longitude=0;

    /**
     * create a point with a lat and a long
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public Point(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    /**
     * returns the latitude of the point
     * @return the latitude of this point
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * returns the longitude of the point
     * @return the longitude of this point
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * returns the name of the point - NOT USED
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the latitude of this point
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * sets the longitude of this point
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * set the name of this point
     * @param name the name of the point
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the latlng of a point
     * @return this points LatLng
     */
    public LatLng getLatLng(){
        return new LatLng(latitude,longitude);
    }
}
