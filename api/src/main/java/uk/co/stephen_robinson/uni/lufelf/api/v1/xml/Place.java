package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 21/02/2014.
 */

/**
 * @author stephen
 *
 * Stored variables and xml tag names for Place data returned from the api
 */
public class Place extends Message {

    protected int id = 0;
    protected String name = "";
    protected String address = "";
    protected Double lattitude;
    protected Double longditude;
    protected String type ="";
    protected String description = "";
    protected String image_url = "";
    protected int user_id = 0;

    public static final String PLACE_ID ="place_id";
    public static final String NAME ="place_name";
    public static final String ADDRESS ="place_adress";
    public static final String LATTITUDE ="place_lat";
    public static final String LONGDITUDE ="place_lon";
    public static final String TYPE = "place_type";
    public static final String DESCRIPTION = "place_description";
    public static final String IMAGE_URL = "place_image_url";
    public static final String USER_ID = "user_id";

    /**
     * getter for user id
     * @return user id of who created place
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * getter for place id
     * @return place id
     */
    public int getId() {
        return id;
    }

    /**
     * getter for place name
     * @return place name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for place address
     * @return place aadress
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for place lattitude
     * @return place lattitude as double
     */
    public Double getLattitude() {
        return lattitude;
    }

    /**
     * getter for place longitude
     * @return place longitude as double
     */
    public Double getLongditude() {
        return longditude;
    }

    /**
     * getter for place type
     * @return place type
     */
    public String getType() {
        return type;
    }

    /**
     * getter for place description
     * @return place description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter for place image url
     * @return place image url
     */
    public String getImage_url() {
        return image_url;
    }

}
