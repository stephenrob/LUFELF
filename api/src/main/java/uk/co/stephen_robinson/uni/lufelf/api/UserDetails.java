package uk.co.stephen_robinson.uni.lufelf.api;

import uk.co.stephen_robinson.uni.lufelf.api.xml.User;

/**
 * Created by Stephen on 18/02/14.
 */
public class UserDetails {

    public int user_id = 0;
    public String name = null;
    public String lib_no = null;
    public String username = null;
    public String password = null;
    public String dob = null;
    public String type = null;
    public String description = null;
    public String avatar_url = null;
    public int location_status = 0;
    public int access_level = 0;
    public Boolean is_new = null;

    public static final String USER_ID = User.USER_ID;
    public static final String NAME = User.NAME;
    public static final String USERNAME = User.USERNAME;
    public static final String LIBRARY_NUMBER = User.LIBRARY_NUMBER;
    public static final String PASSWORD = "password";
    public static final String DATE_OF_BIRTH = User.DATE_OF_BIRTH;
    public static final String TYPE = User.TYPE;
    public static final String DESCRIPTION = User.DESCRIPTION;
    public static final String LOCATION_STATUS = "location_status";
    public static final String ACCESS_LEVEL = "access_level";
    public static final String IS_NEW = User.IS_NEW;

    public enum Type {STAFF, STUDENT};

    public static final Integer ACCESS_NORMAL = 0;
    public static final Integer ACCESS_ADMIN = 1;

    public static final Integer LOCATION_PUBLIC = 1;
    public static final Integer LOCATION_PRIVATE = 0;

}
