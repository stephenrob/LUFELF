package uk.co.stephen_robinson.uni.lufelf.api.v1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.network.Script;
import uk.co.stephen_robinson.uni.lufelf.api.v1.Scripts.Name;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Formatter;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Parser;

/**
 * Created by Stephen on 21/02/14.
 */
public class NetworkHelper {

    public static Hashtable formatResult(Script serverScript, String serverResponse){

        Hashtable result = new Hashtable();

        switch(Name.valueOf(serverScript.name)){

            case CREATE_USER:
                result = Formatter.userDetails(Parser.parseUserDetails(serverResponse), serverScript);

                if (result.get(Message.STATUS).equals(Message.FAILURE)){
                    Api.getSessionManager().logoutUser();
                } else {
                    Api.getSessionManager().updateUserId(result.get(User.USER_ID).toString());
                }

                break;
            case LOGIN_USER:
                result = Formatter.userDetails(Parser.parseUserDetails(serverResponse), serverScript);

                if (result.get(Message.STATUS).equals(Message.FAILURE)){
                    Api.getSessionManager().logoutUser();
                } else {
                    Api.getSessionManager().updateUserId(result.get(User.USER_ID).toString());
                }

                break;
            case QUERY_USER_DETAILS:
                result = Formatter.userDetails(Parser.parseUserDetails(serverResponse), serverScript);
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
            case SENT_MESSAGES:
                break;
            case RECEIVED_MESSAGES:
                break;
            default:
                result = Formatter.message(Parser.parseGenericResult(serverResponse));
                break;
        }
        return result;
    }

    public static ArrayList formatMultipleResults(Script serverScript, String serverResponse){

        ArrayList results;

        switch(Name.valueOf(serverScript.name)){

            case PLACE_LIST:
                results = Parser.parsePlaces(serverResponse);
                break;

            case EVENT_LIST:
                results = Parser.parseEvents(serverResponse);
                break;

            default:
                results = null;
                break;
        }

        return results;
    }

}
