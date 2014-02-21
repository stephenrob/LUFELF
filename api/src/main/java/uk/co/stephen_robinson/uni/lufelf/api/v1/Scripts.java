package uk.co.stephen_robinson.uni.lufelf.api.v1;

import uk.co.stephen_robinson.uni.lufelf.api.network.Script;

/**
 * Created by Stephen on 20/02/14.
 */
public class Scripts {

    public static final Script LOGIN_USER = new Script(Name.LOGIN_USER.toString(), "/login_user.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script CREATE_USER = new Script(Name.CREATE_USER.toString(), "/create_user.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script QUERY_USER = new Script(Name.QUERY_USER_DETAILS.toString(), "/query_user_details.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script DELETE_USER = new Script(Name.DELETE_USER.toString(), "/delete_user.php", Script.Protocol.HTTP, Script.Type.POST);

    public static final Script MAKE_FRIEND = new Script(Name.MAKE_FRIEND.toString(), "/make_friends.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script DELETE_FRIEND = new Script(Name.DELETE_FRIEND.toString(), "/delete_friend.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script GET_FRIEND_REQUESTS = new Script(Name.GET_FRIEND_REQUESTS.toString(), "/get_friend_requests.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script FRIEND_HANDSHAKE = new Script(Name.FRIEND_HANDSHAKE.toString(), "/friend_handshake.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script FRIEND_LIST = new Script(Name.FRIEND_LIST.toString(), "/query_friend_list.php", Script.Protocol.HTTP, Script.Type.POST);

    public static final Script UPDATE_USER_LOCATION = new Script(Name.UPDATE_USER_LOCATION.toString(), "/update_user_status.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script UPDATE_USER_STATUS = new Script(Name.UPDATE_USER_STATUS.toString(), "/query_user_status.php", Script.Protocol.HTTP, Script.Type.POST);

    public static final Script CREATE_EVENT = new Script(Name.CREATE_EVENT.toString(), "/create_event.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script EVENT_DETAILS = new Script(Name.EVENT_DETAILS.toString(), "/query_event_details.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script DELETE_EVENT = new Script(Name.DELETE_EVENT.toString(), "/delete_event.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script EVENT_LIST = new Script(Name.EVENT_LIST.toString(), "/query_event_list.php", Script.Protocol.HTTP, Script.Type.GET);
    public static final Script ATTEND_EVENT = new Script(Name.ATTEND_EVENT.toString(), "/attend_event.php", Script.Protocol.HTTP, Script.Type.POST);

    public static final Script CREATE_PLACE = new Script(Name.CREATE_PLACE.toString(), "/create_place.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script PLACE_LIST = new Script(Name.PLACE_LIST.toString(), "/query_place_list.php", Script.Protocol.HTTP, Script.Type.GET);

    public static final Script SEND_MESSAGE = new Script(Name.SEND_MESSAGE.toString(), "/send_message.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script SENT_MESSAGES = new Script(Name.SENT_MESSAGES.toString(), "/query_sent_messages.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script RECEIVED_MESSAGE = new Script(Name.RECEIVED_MESSAGES.toString(), "/query_received_messages.php", Script.Protocol.HTTP, Script.Type.POST);

    public static final Script UPLOAD_PROFILE_PICTURE = new Script(Name.UPLOAD_PROFILE_PICTURE.toString(), "/upload_picture.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script UPLOAD_PLACE_PICTURE = new Script(Name.UPLOAD_PLACE_PICTURE.toString(), "/upload_picture_place.php", Script.Protocol.HTTP, Script.Type.POST);
    public static final Script UPLOAD_EVENT_PICTURE = new Script(Name.UPLOAD_EVENT_PICTURE.toString(), "/upload_picture_event.php", Script.Protocol.HTTP, Script.Type.POST);

    public enum Name {CREATE_USER, LOGIN_USER, QUERY_USER_DETAILS, DELETE_USER, MAKE_FRIEND, DELETE_FRIEND, GET_FRIEND_REQUESTS, FRIEND_HANDSHAKE,
        FRIEND_LIST, UPDATE_USER_LOCATION, UPDATE_USER_STATUS, CREATE_EVENT, EVENT_DETAILS, DELETE_EVENT, EVENT_LIST, ATTEND_EVENT, CREATE_PLACE,
        PLACE_LIST, SEND_MESSAGE, SENT_MESSAGES, RECEIVED_MESSAGES, UPLOAD_PROFILE_PICTURE, UPLOAD_PLACE_PICTURE, UPLOAD_EVENT_PICTURE
    }

}
