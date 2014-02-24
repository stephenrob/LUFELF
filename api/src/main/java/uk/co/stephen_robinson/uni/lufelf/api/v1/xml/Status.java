package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 24/02/2014.
 */
public class Status extends Message {

    protected Integer id;
    protected Integer location;
    protected Double lattitude;
    protected Double longitude;
    protected Boolean is_new = false;

    public static final String USER_ID = "user_id";
    public static final String LOCATION = "location_status";
    public static final String LATTITUDE = "lat";
    public static final String LONGITUDE = "lon";
    public static final String IS_NEW = "is_new";


    public Integer getId() {
        return id;
    }

    public Integer getLocation() {
        return location;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Boolean getIs_new() {
        return is_new;
    }
}
