package uk.co.stephen_robinson.uni.lufelf.api;

import uk.co.stephen_robinson.uni.lufelf.api.xml.User;

/**
 * Created by Stephen on 18/02/14.
 */
public class UserDetails {

    int user_id = 0;
    String name = null;
    String lib_no = null;
    String username = null;
    String password = null;
    String dob = null;
    String type = null;
    String description = null;
    String avatar_url = null;
    int location_status = 0;
    int access_level = 0;
    Boolean is_new = null;

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
