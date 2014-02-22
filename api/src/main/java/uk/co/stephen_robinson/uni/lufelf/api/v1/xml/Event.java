package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;


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
    protected int place_id;
    protected int user_id;

    public static final String ID = "event_id";
    public static final String NAME = "event_name";
    public static final String DATE = "event_date";
    public static final String TYPE = "event_type";
    public static final String DESCRIPTION = "event_description";
    public static final String IMAGE_URL = "event_image_url";
    public static final String PLACE_ID = "place_id";
    public static final String USER_ID = "user_id";

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
}
