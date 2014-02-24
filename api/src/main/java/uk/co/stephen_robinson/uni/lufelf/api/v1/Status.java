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

    public Integer getId() {
        return id;
    }

    public Integer getLocation() {
        return location;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Boolean getIs_new() {
        return is_new;
    }

    static void updatePrivacy(int status, Single sc){
        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair(Status.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Status.LOCATION, Integer.toString(status)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.UPDATE_USER_STATUS);
        networkTask.execute(params);
    }

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
