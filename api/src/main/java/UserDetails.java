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

    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String LIBRARY_NUMBER = "lib_no";
    public static final String PASSWORD = "password";
    public static final String DATE_OF_BIRTH = "dob";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String LOCATION_STATUS = "location_status";
    public static final String ACCESS_LEVEL = "access_level";
    public static final String IS_NEW = "is_new";

    public enum Type {STAFF, STUDENT};

    public static final Integer ACCESS_NORMAL = 0;
    public static final Integer ACCESS_ADMIN = 1;

    public static final Integer LOCATION_PUBLIC = 1;
    public static final Integer LOCATION_PRIVATE = 0;

}
