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

/**
 * @author stephen
 *
 * Stores all server executable methods for status
 */
public class Status {

    protected Integer id;
    protected Integer location;
    protected Double lattitude;
    protected Double longitude;
    protected Boolean is_new;

    public static final String USER_ID = "user_id";
    public static final String LOCATION = "location_status";
    public static final String LATTITUDE = "lat";
    public static final String LONGITUDE = "lon";
    public static final String IS_NEW = "is_new";

    /**
     *
     * @return status id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return location status
     */
    public Integer getLocation() {
        return location;
    }

    /**
     *
     * @return lattitude of status
     */
    public Double getLattitude() {
        return lattitude;
    }

    /**
     *
     * @return longitude of status
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return boolean is a new status update
     */
    public Boolean getIs_new() {
        return is_new;
    }

    /**
     * static method for updating user privacy setting
     *
     * @param status privacy value to update to
     * @param sc single callback to execute when network task completes
     */
    static void updatePrivacy(int status, Single sc){
        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        Api.getSessionManager().setPrivacy(Integer.toString(status));

        params.add(new BasicNameValuePair(Status.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Status.LOCATION, Integer.toString(status)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.UPDATE_USER_STATUS);
        networkTask.execute(params);
    }

    /**
     * static method for updating user location
     *
     * @param lat lattitude of new location
     * @param lon longitude of new location
     * @param sc single callback to execute when network task completes
     */
    static void updateLocation(Double lat, Double lon, Single sc){
        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(4);

        params.add(new BasicNameValuePair(Status.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Status.LATTITUDE, Double.toString(lat)));
        params.add(new BasicNameValuePair(Status.LONGITUDE, Double.toString(lon)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.UPDATE_USER_LOCATION);
        networkTask.execute(params);
    }
}
