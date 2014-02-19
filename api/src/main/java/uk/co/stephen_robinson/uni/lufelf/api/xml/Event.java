package uk.co.stephen_robinson.uni.lufelf.api.xml;

/**
 * Created by Stephen on 19/02/14.
 */
public class Event {

    int event_id = 0;
    String name = null;
    String date = null;
    String type = null;
    String description = null;
    String image_url = null;
    int place_id = 0;
    int user_id = 0;

    public static final String EVENT_ID = "event_id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_TYPE = "event_type";
    public static final String EVENT_DESCRIPTION = "event_description";
    public static final String EVENT_IMAGE_URL = "event_image_url";
    public static final String PLACE_ID = "place_id";
    public static final String USER_ID = "user_id";

}
