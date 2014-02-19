package uk.co.stephen_robinson.uni.lufelf.api;

import uk.co.stephen_robinson.uni.lufelf.api.xml.Event;

/**
 * Created by Stephen on 19/02/14.
 */
public class EventDetails {

    int event_id = 0;
    String name = null;
    String date = null;
    String type = null;
    String description = null;
    String image_url = null;
    int place_id = 0;
    int user_id = 0;

    public static final String ID = Event.EVENT_ID;
    public static final String NAME = Event.EVENT_NAME;
    public static final String DATE = Event.EVENT_DATE;
    public static final String TYPE = Event.EVENT_TYPE;
    public static final String DESCRIPTION = Event.EVENT_DESCRIPTION;
    public static final String IMAGE_URL = Event.EVENT_IMAGE_URL;
    public static final String PLACE_ID = Event.PLACE_ID;
    public static final String USER_ID = Event.EVENT_ID;

    public enum Type {SOCIAL, UNIVERSITY, OPENDAY, PRIVATE, PUBLIC, PARTY, STUDY}

}
