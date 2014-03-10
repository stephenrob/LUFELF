package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 20/02/14.
 */

/**
 * @author stephen
 *
 * Stored variables and xml tag names for user data returned from the api
 */
public class User extends Message {

    protected int user_id = 0;
    protected String name = "";
    protected String username = "";
    protected String lib_no = "";
    protected String dob = "";
    protected String type = "";
    protected String description = "";
    protected String is_new = "";

    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String LIBRARY_NUMBER = "lib_no";
    public static final String DATE_OF_BIRTH = "dob";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String IS_NEW = "is_new";

    /**
     * getter for user id
     * @return user id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * getter for users name
     * @return users name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for username
     * @return users username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for users library number
     * @return users library number
     */
    public String getLib_no() {
        return lib_no;
    }

    /**
     * getter for users date of birth
     * @return users date of birth
     */
    public String getDob() {
        return dob;
    }

    /**
     * getter for user type
     * @return user account type
     */
    public String getType() {
        return type;
    }

    /**
     * getter for user description
     * @return user description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter for is a new user
     * @return is user new
     */
    public String getIs_new() {
        return is_new;
    }
}
