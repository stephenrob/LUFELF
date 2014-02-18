import java.util.ArrayList;

/**
 * Created by Stephen on 18/02/14.
 */
public class XmlFriendRequests extends XmlMessage {

    ArrayList<XmlFriendRequest> requests = null;

    public static final String REQUEST_ID = "request_id";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String FRIENDS = "friends";
    public static final String FRIEND = "friend";

    public void addRequest(XmlFriendRequest request){
        requests.add(request);
    }

}
