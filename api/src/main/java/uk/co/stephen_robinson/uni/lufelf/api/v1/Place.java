package uk.co.stephen_robinson.uni.lufelf.api.v1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.SessionManager;
import uk.co.stephen_robinson.uni.lufelf.api.network.SinglePost;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Single;

/**
 * Created by Stephen on 21/02/14.
 */
public class Place {

    int id = 0;
    String name = null;
    String address = null;
    Long lattitude;
    Long longditude;
    String type;
    String description;
    String image_url;
    int user_id;

    public static final String PLACE_ID ="place_id";
    public static final String NAME ="name";
    public static final String ADDRESS ="address";
    public static final String LATTITUDE ="lat";
    public static final String LONGDITUDE ="lon";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "desc";
    public static final String IMAGE_URL = "image_url";
    public static final String USER_ID = "user_id";

    static void create(Place p, Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(8);

        params.add(new BasicNameValuePair(Place.NAME, p.name));
        params.add(new BasicNameValuePair(Place.ADDRESS, p.address));
        params.add(new BasicNameValuePair(Place.LATTITUDE, p.lattitude.toString()));
        params.add(new BasicNameValuePair(Place.LONGDITUDE, p.longditude.toString()));
        params.add(new BasicNameValuePair(Place.TYPE, p.type));
        params.add(new BasicNameValuePair(Place.DESCRIPTION, p.description));
        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask = new SinglePost(sc, Scripts.CREATE_PLACE);
        networkTask.execute(params);

    }

}
