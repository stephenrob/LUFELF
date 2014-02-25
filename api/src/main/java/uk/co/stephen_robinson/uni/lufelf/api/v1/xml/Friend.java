package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 24/02/2014.
 */
public class Friend extends Message {

    protected int request_id;
    protected int friend_id;
    protected int user_id;
    protected int friend_status;
    protected String name;
    protected String username;
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

    public int getRequest_id() {
        return request_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getFriend_status() {
        return friend_status;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getLocation_status() {
        return location_status;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
