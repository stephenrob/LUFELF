package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 21/02/2014.
 */
public class Place extends Message {

    protected int id = 0;
    protected String name = null;
    protected String address = null;
    protected Long lattitude;
    protected Long longditude;
    protected String type;
    protected String description;
    protected String image_url;
    protected int user_id;

    public static final String PLACE_ID ="place_id";
    public static final String NAME ="place_name";
    public static final String ADDRESS ="place_address";
    public static final String LATTITUDE ="place_lat";
    public static final String LONGDITUDE ="place_lon";
    public static final String TYPE = "place_type";
    public static final String DESCRIPTION = "place_desc";
    public static final String IMAGE_URL = "place_image_url";
    public static final String USER_ID = "user_id";

}
