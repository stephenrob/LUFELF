package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;


import java.util.ArrayList;

/**
 * Created by Stephen on 22/02/2014.
 */
public class Event extends Message {

    protected int id;
    protected String name = "";
    protected String date = "";
    protected String type = "";
    protected String description = "";
    protected String image_url = "";
    protected String location_name = "";
    protected String location_address = "";
    protected String location = "";
    protected int place_id;
    protected int user_id;
    protected User owner = new User();
    protected ArrayList<EventUser> attendees;

    public static final String ID = "event_id";
    public static final String NAME = "event_name";
    public static final String DATE = "event_date";
    public static final String TYPE = "event_type";
    public static final String DESCRIPTION = "event_description";
    public static final String SINGLE_NAME = "name";
    public static final String SINGLE_DATE = "date";
    public static final String SINGLE_TYPE = "type";
    public static final String SINGLE_DESCRIPTION = "description";
    public static final String IMAGE_URL = "event_image_url";
    public static final String PLACE_ID = "place_id";
    public static final String USER_ID = "user_id";
    public static final String LOCATION_NAME = "location_name";
    public static final String LOCATION_ADDRESS = "location_address";
    public static final String LOCATION_GPS = "location";

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getPlace_id() {
        return place_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public ArrayList<EventUser> getAttendees() {
        return attendees;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getLocation_address() {
        return location_address;
    }

    public String getLocation() {
        return location;
    }

    public User getOwner() {
        return owner;
    }
}
