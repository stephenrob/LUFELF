package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 24/02/2014.
 */

/**
 * @author stephen
 *
 * Stored variables and xml tag names for Friend data returned from the api
 */
public class Friend extends Message {

    protected int request_id;
    protected int friend_id;
    protected int user_id;
    protected int friend_status;
    protected String name;
    protected String username;
    protected String lib_no;
    protected int location_status;
    protected Double lattitude;
    protected Double longitude;

    public static final String REQUEST_ID = "request_id";
    public static final String USER_ID = "user_id";
    public static final String FRIEND_ID = "friend_id";
    public static final String STATUS = "friend_status";
    public static final String NAME = "name";
    public static final String FRIEND_NAME = "friend_name";
    public static final String USERNAME = "friend_username";
    public static final String LOCATION_STATUS = "location_status";
    public static final String LATTITUDE = "lat";
    public static final String LONGITUDE = "lon";
    public static final String USER = "user";
    public static final String USERS = "users";
    public static final String FRIEND = "friend";
    public static final String FRIENDS = "friends";

    /**
     * getter for request id
     * @return friend request id
     */
    public int getRequest_id() {
        return request_id;
    }

    /**
     * getter for friend id
     * @return friend id
     */
    public int getFriend_id() {
        return friend_id;
    }

    /**
     * getter for user id of friend
     * @return friends user id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * getter for friend status
     * @return friend status code
     */
    public int getFriend_status() {
        return friend_status;
    }

    /**
     * getter for friend name
     * @return friend name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for friends username
     * @return friends username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for friends location status
     * @return friends location status
     */
    public int getLocation_status() {
        return location_status;
    }

    /**
     * getter for friends lattitude location
     * @return friends lattitude as double
     */
    public Double getLattitude() {
        return lattitude;
    }

    /**
     * getter for friends longitude location
     * @return friends longitude as double
     */
    public Double getLongitude() {
        return longitude;
    }
}
