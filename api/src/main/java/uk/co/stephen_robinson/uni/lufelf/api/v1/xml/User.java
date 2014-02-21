package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 20/02/14.
 */
public class User extends Message {

    protected int user_id = 0;
    protected String name = null;
    protected String username = null;
    protected String lib_no = null;
    protected String dob = null;
    protected String type = null;
    protected String description = null;
    protected String is_new = null;

    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String LIBRARY_NUMBER = "lib_no";
    public static final String DATE_OF_BIRTH = "dob";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String IS_NEW = "is_new";


}
