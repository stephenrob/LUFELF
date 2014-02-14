import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Stephen on 14/02/2014.
 */
public class network {

    // Server IP Address

    private static final String IP_ADDRESSS = "148.88.32.47";

    // Server php scripts

    // Users
    private static final String CREATE_USER = "create_user.php";
    private static final String LOGIN_USER = "login_user.php";
    private static final String QUERY_USER_DETAILS = "query_user_details.php";
    private static final String DELETE_USER = "delete_user.php";

    // Friends
    private static final String MAKE_FRIEND = "make_friends.php";
    private static final String DELETE_FRIEND = "delete_friend.php";
    private static final String GET_FRIEND_REQUESTS = "get_friend_requests.php";
    private static final String FRIEND_HANDSHAKE = "friend_handshake.php";
    private static final String FRIEND_LIST = "query_friend_list.php";

    // User Location
    private static final String UPDATE_USER_LOCATION = "update_user_status.php";
    private static final String UPDATE_USER_STATUS = "query_user_status.php";

    // Events
    private static final String CREATE_EVENT = "create_event.php";
    private static final String EVENT_DETAILS = "query_event_details.php";
    private static final String DELETE_EVENT = "delete_event.php";
    private static final String EVENT_LIST = "query_event_list.php";
    private static final String ATTEND_EVENT = "attend_event.php";

    // Places
    private static final String CREATE_PLACE = "create_place.php";
    private static final String PLACE_LIST = "query_place_list.php";

    // Messages
    private static final String SEND_MESSAGE = "send_message.php";
    private static final String SENT_MESSAGES = "query_sent_messages.php";
    private static final String RECEIVED_MESSAGES = "query_received_messages.php";

    // Images
    private static final String UPLOAD_PROFILE_PICTURE = "upload_picture.php";
    private static final String UPLOAD_PLACE_PICTURE = "upload_picture_place.php";
    private static final String UPLOAD_EVENT_PICTURE = "upload_picture_event.php";

    // Server Image Directories

    private static final String PROFILE_PICTURE = "avatars";
    private static final String PLACE_IMAGE = "place_images";
    private static final String EVENT_IMAGE = "event_images";

    // HTTP Client
    private HttpClient serverClient;

    public network(){

    }

}
