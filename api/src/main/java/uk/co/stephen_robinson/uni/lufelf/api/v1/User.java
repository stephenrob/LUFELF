package uk.co.stephen_robinson.uni.lufelf.api.v1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.SessionManager;
import uk.co.stephen_robinson.uni.lufelf.api.Network.SinglePost;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;

/**
 * Created by Stephen on 21/02/14.
 */
public class User {

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

    public enum Search {USERNAME, LIB_NO, NAME, USER_ID};

    static void register(User user, Single sc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(9);

        params.add(new BasicNameValuePair(User.NAME, user.name));
        params.add(new BasicNameValuePair(User.LIBRARY_NUMBER, user.lib_no));
        params.add(new BasicNameValuePair(User.USERNAME, user.username));
        params.add(new BasicNameValuePair(User.PASSWORD, user.password));
        params.add(new BasicNameValuePair(User.DATE_OF_BIRTH, user.dob));
        params.add(new BasicNameValuePair(User.TYPE, user.type));
        params.add(new BasicNameValuePair(User.DESCRIPTION, user.description));
        params.add(new BasicNameValuePair(User.LOCATION_STATUS, Integer.toString(user.location_status)));
        params.add(new BasicNameValuePair(User.ACCESS_LEVEL, Integer.toString(user.access_level)));

        Api.getSessionManager().createLoginSession(user.username, user.password, Integer.toString(user.user_id));

        SinglePost networkTask = new SinglePost(sc, Scripts.CREATE_USER);
        networkTask.execute(params);

    }

    static  void login(User user, Single sc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(User.USERNAME, user.username));
        params.add(new BasicNameValuePair(User.PASSWORD, user.password));

        Api.getSessionManager().createLoginSession(user.username, user.password, Integer.toString(0));

        SinglePost networkTask = new SinglePost(sc, Scripts.LOGIN_USER);
        networkTask.execute(params);

    }

    static void getDetails(Search searchField, String searchValue, Single sc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);

        switch (searchField){
            case USERNAME:
                params.add(new BasicNameValuePair(User.USERNAME, searchValue));
                break;
            case LIB_NO:
                params.add(new BasicNameValuePair(User.LIBRARY_NUMBER, searchValue));
                break;
            case NAME:
                params.add(new BasicNameValuePair(User.NAME, searchValue));
                break;
            case USER_ID:
                params.add(new BasicNameValuePair(User.USER_ID, searchValue));
                break;
        }

        SinglePost networkTask = new SinglePost(sc, Scripts.QUERY_USER);
        networkTask.execute(params);

    }

    static void delete(Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.DELETE_USER);
        networkTask.execute(params);

    }

}
