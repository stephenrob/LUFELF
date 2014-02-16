import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Stephen on 14/02/2014.
 */
public class Network extends AsyncTask<List<NameValuePair>, Integer, Hashtable>{

    // Server IP Address
    private static final String IP_ADDRESSS = "148.88.32.47";

    // Server Protocol
    private static final String SERVER_PROTOCOL = "http://";

    // Server php scripts

    // Users
    private static final String CREATE_USER = "/create_user.php";
    private static final String LOGIN_USER = "/login_user.php";
    private static final String QUERY_USER_DETAILS = "/query_user_details.php";
    private static final String DELETE_USER = "/delete_user.php";

    // Friends
    private static final String MAKE_FRIEND = "/make_friends.php";
    private static final String DELETE_FRIEND = "/delete_friend.php";
    private static final String GET_FRIEND_REQUESTS = "/get_friend_requests.php";
    private static final String FRIEND_HANDSHAKE = "/friend_handshake.php";
    private static final String FRIEND_LIST = "/query_friend_list.php";

    // User Location
    private static final String UPDATE_USER_LOCATION = "/update_user_status.php";
    private static final String UPDATE_USER_STATUS = "/query_user_status.php";

    // Events
    private static final String CREATE_EVENT = "/create_event.php";
    private static final String EVENT_DETAILS = "/query_event_details.php";
    private static final String DELETE_EVENT = "/delete_event.php";
    private static final String EVENT_LIST = "/query_event_list.php";
    private static final String ATTEND_EVENT = "/attend_event.php";

    // Places
    private static final String CREATE_PLACE = "/create_place.php";
    private static final String PLACE_LIST = "/query_place_list.php";

    // Messages
    private static final String SEND_MESSAGE = "/send_message.php";
    private static final String SENT_MESSAGES = "/query_sent_messages.php";
    private static final String RECEIVED_MESSAGES = "/query_received_messages.php";

    // Images
    private static final String UPLOAD_PROFILE_PICTURE = "/upload_picture.php";
    private static final String UPLOAD_PLACE_PICTURE = "/upload_picture_place.php";
    private static final String UPLOAD_EVENT_PICTURE = "/upload_picture_event.php";

    // Server Image Directories

    public static final String PROFILE_PICTURE = "/avatars/";
    public static final String PLACE_IMAGE = "/place_images/";
    public static final String EVENT_IMAGE = "/event_images/";

    // HTTP Client
    private HttpClient serverClient;

    // Server Scripts ENUM

    public enum Script {CREATE_USER, LOGIN_USER, QUERY_USER_DETAILS, DELETE_USER, MAKE_FRIEND, DELETE_FRIEND, GET_FRIEND_REQUESTS, FRIEND_HANDSHAKE,
        FRIEND_LIST, UPDATE_USER_LOCATION, UPDATE_USER_STATUS, CREATE_EVENT, EVENT_DETAILS, DELETE_EVENT, EVENT_LIST, ATTEND_EVENT, CREATE_PLACE,
        PLACE_LIST, SEND_MESSAGE, SENT_MESSAGES, RECEIVED_MESSAGES, UPLOAD_PROFILE_PICTURE, UPLOAD_PLACE_PICTURE, UPLOAD_EVENT_PICTURE
    }

    // Callback
    private NetworkCallback networkCallback = null;
    private Script serverScript = null;

    public Network(NetworkCallback nc, Script script){
        this.networkCallback = nc;
        this.serverScript = script;
    }

    // Accepts List<namevaluepair> for server parameters

    protected Hashtable doInBackground(List<NameValuePair>... params){

        Hashtable result = new Hashtable();

        HttpPost postData = new HttpPost(SERVER_PROTOCOL + IP_ADDRESSS + selectScript(this.serverScript));

        try {

            postData.setEntity(new UrlEncodedFormEntity(params[0]));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseText = serverClient.execute(postData, responseHandler);

            // format response

            switch (this.serverScript){
                case CREATE_USER:
                    break;
                case LOGIN_USER:
                    break;
                case QUERY_USER_DETAILS:
                    break;
                case GET_FRIEND_REQUESTS:
                    break;
                case FRIEND_HANDSHAKE:
                    break;
                case FRIEND_LIST:
                    break;
                case EVENT_DETAILS:
                    break;
                case EVENT_LIST:
                    break;
                case PLACE_LIST:
                    break;
                case SENT_MESSAGES:
                    break;
                case RECEIVED_MESSAGES:
                    break;
                default:
                    result = formatMessage(XmlParser.parseGenericResult(responseText));
                    break;
            }

        } catch(Exception e) {
            Log.e("LUFELF", e.toString());
        }

        return result;
    }

    protected void onPostExecute(Hashtable params){
        networkCallback.results(params);
    }

    private Hashtable formatMessage(XmlMessage message){
        Hashtable resultMessage = new Hashtable();

        resultMessage.put(XmlMessage.STATUS, message.status);
        resultMessage.put(XmlMessage.MESSAGE, message.message);

        return resultMessage;
    }

    private String selectScript(Script script){
        switch (serverScript){
            case CREATE_USER:
                return CREATE_USER;
            case LOGIN_USER:
                return LOGIN_USER;
            case QUERY_USER_DETAILS:
                return QUERY_USER_DETAILS;
            case DELETE_USER:
                return DELETE_USER;
            case MAKE_FRIEND:
                return MAKE_FRIEND;
            case DELETE_FRIEND:
                return DELETE_FRIEND;
            case GET_FRIEND_REQUESTS:
                return GET_FRIEND_REQUESTS;
            case FRIEND_HANDSHAKE:
                return FRIEND_HANDSHAKE;
            case FRIEND_LIST:
                return FRIEND_LIST;
            case UPDATE_USER_LOCATION:
                return UPDATE_USER_LOCATION;
            case UPDATE_USER_STATUS:
                return UPDATE_USER_STATUS;
            case CREATE_EVENT:
                return CREATE_EVENT;
            case EVENT_DETAILS:
                return EVENT_DETAILS;
            case DELETE_EVENT:
                return DELETE_EVENT;
            case EVENT_LIST:
                return EVENT_LIST;
            case ATTEND_EVENT:
                return ATTEND_EVENT;
            case CREATE_PLACE:
                return CREATE_PLACE;
            case PLACE_LIST:
                return PLACE_LIST;
            case SEND_MESSAGE:
                return SEND_MESSAGE;
            case SENT_MESSAGES:
                return SENT_MESSAGES;
            case RECEIVED_MESSAGES:
                return RECEIVED_MESSAGES;
            case UPLOAD_PROFILE_PICTURE:
                return UPLOAD_PROFILE_PICTURE;
            case UPLOAD_EVENT_PICTURE:
                return UPLOAD_EVENT_PICTURE;
            case UPLOAD_PLACE_PICTURE:
                return UPLOAD_PLACE_PICTURE;
            default:
                return null;
        }
    }

}
