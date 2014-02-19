package uk.co.stephen_robinson.uni.lufelf.api.xml;

/**
 * Created by Stephen on 19/02/14.
 */
public class User extends Message {

    public int user_id = 0;
    public String name = null;
    public String username = null;
    public String lib_no = null;
    public String dob = null;
    public String type = null;
    public String description = null;
    public String is_new = null;

    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String LIBRARY_NUMBER = "lib_no";
    public static final String DATE_OF_BIRTH = "dob";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String IS_NEW = "is_new";

}
