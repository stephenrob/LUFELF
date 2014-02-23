package uk.co.stephen_robinson.uni.lufelf.api.v1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Network.MultipleGet;
import uk.co.stephen_robinson.uni.lufelf.api.Network.MultiplePost;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;

/**
 * Created by Stephen on 22/02/2014.
 */
public class Event {

    public int id;
    public String name = "";
    public String date = "";
    public String type = "";
    public String description = "";
    public String image_url = "";
    public int place_id;
    public int user_id;

    public static final String ID = "event_id";
    public static final String NAME = "event_name";
    public static final String DATE = "event_date";
    public static final String TYPE = "event_type";
    public static final String DESCRIPTION = "event_description";
    public static final String IMAGE_URL = "event_image_url";
    public static final String PLACE_ID = "place_id";
    public static final String USER_ID = "user_id";

    static void listAll(Multiple mc){
        MultipleGet networkTask = new MultipleGet(mc, Scripts.EVENT_LIST);
        networkTask.execute();
    }

    static void single(Integer id, Multiple mc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);

        params.add(new BasicNameValuePair(Event.ID, id.toString()));
        MultiplePost networkTask = new MultiplePost(mc, Scripts.EVENT_DETAILS);
        networkTask.execute(params);
    }

}
