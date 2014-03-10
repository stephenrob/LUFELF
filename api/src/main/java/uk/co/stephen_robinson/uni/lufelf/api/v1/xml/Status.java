package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 24/02/2014.
 */

/**
 * @author stephen
 *
 * Stored variables and xml tag names for Status data returned from the api
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

    /**
     * getter for status id
     * @return status id
     */
    public Integer getId() {
        return id;
    }

    /**
     * getter for location
     * @return location id
     */
    public Integer getLocation() {
        return location;
    }

    /**
     * getter for lattitude
     * @return lattitude for status update
     */
    public Double getLattitude() {
        return lattitude;
    }

    /**
     * getter for longitude
     * @return longitude for status update
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * getter for is a new status update
     * @return boolean based on is a new status update
     */
    public Boolean getIs_new() {
        return is_new;
    }
}
