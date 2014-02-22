package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 20/02/14.
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

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getLib_no() {
        return lib_no;
    }

    public String getDob() {
        return dob;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getIs_new() {
        return is_new;
    }
}
