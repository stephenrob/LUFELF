package uk.co.stephen_robinson.uni.lufelf.api.v1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.SessionManager;
import uk.co.stephen_robinson.uni.lufelf.api.network.MultipleGet;
import uk.co.stephen_robinson.uni.lufelf.api.network.MultiplePost;
import uk.co.stephen_robinson.uni.lufelf.api.network.SinglePost;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Single;

/**
 * Created by Stephen on 22/02/2014.
 */

/**
 * @author stephen
 *
 * Stores all server executable methods for events
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

    /**
     * static method for creating an event, pulls user id and password from shared preferences
     *
     * @param e event data to send to the server
     * @param sc single callback to execute when network task completes
     */
    static void create(Event e, Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(7);

        params.add(new BasicNameValuePair(Event.NAME, e.name));
        params.add(new BasicNameValuePair(Event.DATE, e.date));
        params.add(new BasicNameValuePair(Event.TYPE, e.type));
        params.add(new BasicNameValuePair(Event.DESCRIPTION, e.description));
        params.add(new BasicNameValuePair(Event.PLACE_ID, Integer.toString(e.place_id)));
        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask = new SinglePost(sc, Scripts.CREATE_EVENT);
        networkTask.execute(params);

    }

    /**
     * static method for listing all events
     *
     * @param mc multiple callback to execute when network task completes
     */
    static void listAll(Multiple mc){
        MultipleGet networkTask = new MultipleGet(mc, Scripts.EVENT_LIST);
        networkTask.execute();
    }

    /**
     * static method for getting single event information
     *
     * @param id event id to find on the server
     * @param mc multiple callback to execute when network task completes
     */
    static void single(Integer id, Multiple mc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);

        params.add(new BasicNameValuePair(Event.ID, id.toString()));

        MultiplePost networkTask = new MultiplePost(mc, Scripts.EVENT_DETAILS);
        networkTask.execute(params);

    }

    /**
     * static method for deleting an event
     *
     * @param eventId id of the event to delete
     * @param sc single callback to execute when network task completes
     */
    static void delete(Integer eventId, Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Event.ID, Integer.toString(eventId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.DELETE_EVENT);
        networkTask.execute(params);

    }

    /**
     * static method for attending an event, attends current user stored in shared prefs
     *
     * @param eventId id of event to attend
     * @param sc single callback to execute when network task completes
     */
    static void attend(Integer eventId, Single sc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair(User.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Event.ID, Integer.toString(eventId)));
        params.add(new BasicNameValuePair(User.PASSWORD, password));

        SinglePost networkTask =  new SinglePost(sc, Scripts.ATTEND_EVENT);
        networkTask.execute(params);

    }

}
