package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;


import java.util.ArrayList;

/**
 * Created by Stephen on 22/02/2014.
 */

/**
 * @author stephen
 *
 * Stored variables and xml tag names for Event data returned from the api
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

    /**
     * Getter for event id
     * @return event id
     */
    public int getId() {
        return id;
    }

    /**
     * getter for event name
     * @return event name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for event data/time
     * @return event date/time
     */
    public String getDate() {
        return date;
    }

    /**
     * getter for event type
     * @return event type
     */
    public String getType() {
        return type;
    }

    /**
     * getter for event description
     * @return event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter for image url
     * @return returns the image url
     */
    public String getImage_url() {
        return image_url;
    }

    /**
     * getter for place id, the location of the event
     * @return event place id
     */
    public int getPlace_id() {
        return place_id;
    }

    /**
     * getter for user id, created event
     * @return user id of the person who created the event
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * getter for the list of people attending the event
     * @return array list of event attendees
     */
    public ArrayList<EventUser> getAttendees() {
        return attendees;
    }

    /**
     * getter for event location name
     * @return event location name
     */
    public String getLocation_name() {
        return location_name;
    }

    /**
     * getter for event location address
     * @return event location address
     */
    public String getLocation_address() {
        return location_address;
    }

    /**
     * getter for event location
     * @return location gps co-ordinates as comma separated string
     */
    public String getLocation() {
        return location;
    }

    /**
     * getter for event owner
     * @return event owner as a User type
     */
    public User getOwner() {
        return owner;
    }
}
