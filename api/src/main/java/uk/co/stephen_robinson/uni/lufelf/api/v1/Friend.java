package uk.co.stephen_robinson.uni.lufelf.api.v1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.SessionManager;
import uk.co.stephen_robinson.uni.lufelf.api.Network.MultiplePost;
import uk.co.stephen_robinson.uni.lufelf.api.Network.SinglePost;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;

/**
 * Created by Stephen on 24/02/2014.
 */

/**
 * @author stephen
 *
 * Stores all server executable methods for friends
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

    /**
     *
     * @return friend request id
     */
    public int getRequest_id() {
        return request_id;
    }

    /**
     *
     * @return friend id
     */
    public int getFriend_id() {
        return friend_id;
    }

    /**
     *
     * @return friends user id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     *
     * @return friendship status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @return friends name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return friends username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return friends location status
     */
    public int getLocation_status() {
        return location_status;
    }

    /**
     *
     * @return location lattitude
     */
    public Double getLattitude() {
        return lattitude;
    }

    /**
     *
     * @return location longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * static method for adding a friend
     *
     * @param id user id of person to add as a friend
     * @param sc single callback to execute when network task completes
     */
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

    /**
     * static method for deleting a friend
     *
     * @param id user id of person to delete as a friend
     * @param sc single callback to execute when network task completes
     */
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

    /**
     * static method for getting all friend requests
     *
     * @param mc multiple callback to execute when network task completes
     */
    static void requests(Multiple mc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        MultiplePost networkTask =  new MultiplePost(mc, Scripts.GET_FRIEND_REQUESTS);
        networkTask.execute(params);

    }

    /**
     * static method for updating a friend request
     *
     * @param request_id friend request id to update
     * @param friend_id user id who's in the friend request
     * @param status status to set the request to
     * @param sc single callback to execute when network task completes
     */
    static void updateRequest(Integer request_id, Integer friend_id, Integer status, Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair("user_id1", Integer.toString(userId)));
        params.add(new BasicNameValuePair("user_id2", Integer.toString(friend_id)));
        params.add(new BasicNameValuePair("request_id", Integer.toString(request_id)));
        params.add(new BasicNameValuePair("status", Integer.toString(status)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.FRIEND_HANDSHAKE);
        networkTask.execute(params);

    }

    /**
     * static method for listing all friends
     *
     * @param mc multiple callback to execute when network task completes
     */
    static void list(Multiple mc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        MultiplePost networkTask =  new MultiplePost(mc, Scripts.FRIEND_LIST);
        networkTask.execute(params);

    }
}
