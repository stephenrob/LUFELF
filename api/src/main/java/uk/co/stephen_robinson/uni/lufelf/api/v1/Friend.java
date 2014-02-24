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
 * Created by Stephen on 24/02/2014.
 */
public class Friend {

    protected int request_id;
    protected int friend_id;
    protected int user_id;
    protected int status;
    protected String name;
    protected String username;
    protected int location_status;
    protected Double lattitude;
    protected Double longitude;

    public static final String REQUEST_ID = "request_id";
    public static final String USER_ID = "user_id";
    public static final String FRIEND_ID = "friend_id";
    public static final String STATUS = "friend_status";
    public static final String NAME = "name";
    public static final String FRIEND_NAME = "friend_name";
    public static final String USERNAME = "friend_username";
    public static final String LOCATION_STATUS = "location_status";
    public static final String LATTITUDE = "lat";
    public static final String LONGITUDE = "lon";

    public int getRequest_id() {
        return request_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getLocation_status() {
        return location_status;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    static void add(Integer id, Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair("user_id1", Integer.toString(userId)));
        params.add(new BasicNameValuePair("user_id2", Integer.toString(id)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.MAKE_FRIEND);
        networkTask.execute(params);

    }

    static void delete(Integer id, Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair("user_id1", Integer.toString(userId)));
        params.add(new BasicNameValuePair("user_id2", Integer.toString(id)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.DELETE_FRIEND);
        networkTask.execute(params);

    }
}
