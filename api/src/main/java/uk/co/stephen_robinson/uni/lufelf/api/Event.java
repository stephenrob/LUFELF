package uk.co.stephen_robinson.uni.lufelf.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.network.Network;

/**
 * Created by Stephen on 19/02/14.
 */
public class Event {

    static void create(EventDetails event, NetworkCallback nc){
        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(7);

        params.add(new BasicNameValuePair(EventDetails.NAME, event.name));
        params.add(new BasicNameValuePair(EventDetails.DATE, event.date));
        params.add(new BasicNameValuePair(EventDetails.TYPE, event.type));
        params.add(new BasicNameValuePair(EventDetails.DESCRIPTION, event.description));
        params.add(new BasicNameValuePair(EventDetails.PLACE_ID, Integer.toString(event.place_id)));
        params.add(new BasicNameValuePair(EventDetails.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Network.PASSWORD, password));

        Network networkTask =  new Network(nc, Network.Script.CREATE_EVENT);
        networkTask.execute(params);
    }

    static void details(Integer event, NetworkCallback nc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);

        params.add(new BasicNameValuePair(EventDetails.ID, Integer.toString(event)));

        Network networkTask =  new Network(nc, Network.Script.EVENT_DETAILS);
        networkTask.execute(params);

    }

    static void delete(Integer event, NetworkCallback nc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair(EventDetails.ID, Integer.toString(event)));
        params.add(new BasicNameValuePair(EventDetails.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Network.PASSWORD, password));

        Network networkTask =  new Network(nc, Network.Script.DELETE_EVENT);
        networkTask.execute(params);

    }

    static void all(NetworkCallback nc){

        List<NameValuePair> params = null;

        Network networkTask =  new Network(nc, Network.Script.DELETE_EVENT);
        networkTask.execute(params);
    }

    static void attend(Integer event, NetworkCallback nc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(3);

        params.add(new BasicNameValuePair(EventDetails.ID, Integer.toString(event)));
        params.add(new BasicNameValuePair(EventDetails.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(Network.PASSWORD, password));

        Network networkTask =  new Network(nc, Network.Script.ATTEND_EVENT);
        networkTask.execute(params);

    }

}
