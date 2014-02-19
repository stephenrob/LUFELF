package uk.co.stephen_robinson.uni.lufelf.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Network.Network;

/**
 * Created by Stephen on 18/02/14.
 */
public class User {

    public enum Search {USERNAME, LIB_NO, NAME, USER_ID};

    static void register(UserDetails user, NetworkCallback nc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(9);

        params.add(new BasicNameValuePair(UserDetails.NAME, user.name));
        params.add(new BasicNameValuePair(UserDetails.LIBRARY_NUMBER, user.lib_no));
        params.add(new BasicNameValuePair(UserDetails.USERNAME, user.username));
        params.add(new BasicNameValuePair(UserDetails.PASSWORD, user.password));
        params.add(new BasicNameValuePair(UserDetails.DATE_OF_BIRTH, user.dob));
        params.add(new BasicNameValuePair(UserDetails.TYPE, user.type));
        params.add(new BasicNameValuePair(UserDetails.DESCRIPTION, user.description));
        params.add(new BasicNameValuePair(UserDetails.LOCATION_STATUS, Integer.toString(user.location_status)));
        params.add(new BasicNameValuePair(UserDetails.ACCESS_LEVEL, Integer.toString(user.access_level)));

        Network networkTask = new Network(nc, Network.Script.CREATE_USER);
        networkTask.execute(params);

    }

   static  void login(UserDetails user, NetworkCallback nc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(UserDetails.USERNAME, user.username));
        params.add(new BasicNameValuePair(UserDetails.PASSWORD, user.password));

       Network networkTask = new Network(nc, Network.Script.LOGIN_USER);
       networkTask.execute(params);

    }

    static void getDetails(Search searchField, String searchValue, NetworkCallback nc){

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);

        switch (searchField){
            case USERNAME:
                params.add(new BasicNameValuePair(UserDetails.USERNAME, searchValue));
                break;
            case LIB_NO:
                params.add(new BasicNameValuePair(UserDetails.LIBRARY_NUMBER, searchValue));
                break;
            case NAME:
                params.add(new BasicNameValuePair(UserDetails.NAME, searchValue));
                break;
            case USER_ID:
                params.add(new BasicNameValuePair(UserDetails.USER_ID, searchValue));
                break;
        }

        Network networkTask = new Network(nc, Network.Script.QUERY_USER_DETAILS);
        networkTask.execute(params);

    }

    static void delete(NetworkCallback nc){

        int userId = Integer.parseInt(Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID));
        String password = Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        params.add(new BasicNameValuePair(UserDetails.USER_ID, Integer.toString(userId)));
        params.add(new BasicNameValuePair(UserDetails.PASSWORD, password));

        Network networkTask =  new Network(nc, Network.Script.DELETE_USER);
        networkTask.execute(params);

    }

}
