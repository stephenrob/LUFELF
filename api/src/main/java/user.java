import android.text.TextUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Stephen on 14/02/2014.
 */
public class User {

    // Enum for user search

    public enum Search {USERNAME, LIB_NO, NAME, USER_ID}
    public enum Type {STAFF, STUDENT}

    // User field classes

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

    // Register new user with LUFELF server

    public void register(Hashtable params, NetworkCallback nc){

        List<NameValuePair> serverParams = new ArrayList<NameValuePair>(9);

        serverParams.add(new BasicNameValuePair(NAME, params.get(NAME).toString()));
        serverParams.add(new BasicNameValuePair(LIBRARY_NUMBER, params.get(LIBRARY_NUMBER).toString()));
        serverParams.add(new BasicNameValuePair(USERNAME, params.get(USERNAME).toString()));
        serverParams.add(new BasicNameValuePair(PASSWORD, params.get(PASSWORD).toString()));
        serverParams.add(new BasicNameValuePair(DATE_OF_BIRTH, params.get(DATE_OF_BIRTH).toString()));
        serverParams.add(new BasicNameValuePair(TYPE, params.get(TYPE).toString()));
        serverParams.add(new BasicNameValuePair(DESCRIPTION, params.get(DESCRIPTION).toString()));
        serverParams.add(new BasicNameValuePair(LOCATION_STATUS, params.get(LOCATION_STATUS).toString()));
        serverParams.add(new BasicNameValuePair(ACCESS_LEVEL, params.get(ACCESS_LEVEL).toString()));

        Network networkTask =  new Network(nc, Network.Script.CREATE_USER);
        networkTask.execute(serverParams);

    }

    // Login user to system and store login details locally

    public void login(Hashtable params, NetworkCallback nc){

        List<NameValuePair> serverParams = new ArrayList<NameValuePair>(2);

        serverParams.add(new BasicNameValuePair(USERNAME, params.get(USERNAME).toString()));
        serverParams.add(new BasicNameValuePair(PASSWORD, params.get(PASSWORD).toString()));

        Network networkTask =  new Network(nc, Network.Script.LOGIN_USER);
        networkTask.execute(serverParams);

    }

    // Get user details

    public void getDetails(Search searchField, String searchValue, NetworkCallback nc){

        List<NameValuePair> serverParams = new ArrayList<NameValuePair>(1);

        switch (searchField){
            case USERNAME:
                serverParams.add(new BasicNameValuePair(USERNAME, searchValue));
                break;
            case LIB_NO:
                serverParams.add(new BasicNameValuePair(LIBRARY_NUMBER, searchValue));
                break;
            case NAME:
                serverParams.add(new BasicNameValuePair(NAME, searchValue));
                break;
            case USER_ID:
                serverParams.add(new BasicNameValuePair(USER_ID, searchValue));
                break;
        }

        Network networkTask = new Network(nc, Network.Script.QUERY_USER_DETAILS);
        networkTask.execute(serverParams);
    }

    // Delete user account on LUFELF Server

    public void delete(Hashtable params, NetworkCallback nc){

        List<NameValuePair> serverParams = new ArrayList<NameValuePair>(2);

        serverParams.add(new BasicNameValuePair(USER_ID, params.get(USER_ID).toString()));
        serverParams.add(new BasicNameValuePair(PASSWORD, params.get(PASSWORD).toString()));

        Network networkTask =  new Network(nc, Network.Script.DELETE_USER);
        networkTask.execute(serverParams);

    }

}
