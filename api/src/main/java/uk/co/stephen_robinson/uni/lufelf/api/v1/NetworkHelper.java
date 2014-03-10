package uk.co.stephen_robinson.uni.lufelf.api.v1;

import java.util.ArrayList;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.Network.Script;
import uk.co.stephen_robinson.uni.lufelf.api.v1.Scripts.Name;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Formatter;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Parser;

/**
 * Created by Stephen on 21/02/14.
 */

/**
 * @author stephen
 *
 * Network helper for determining which parsing and formating of returned data needs to be done
 */
public class NetworkHelper {

    /**
     * static method for formatting result as hashtable
     *
     * @param serverScript script executed on the server
     * @param serverResponse returned xml data from the server
     * @return hashtable of formated result
     */
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

            case FRIEND_HANDSHAKE:
                result = Formatter.message(Parser.parseFriendHandshake(serverResponse));
                break;

            case UPDATE_USER_LOCATION:
                result = Formatter.updateLocation(Parser.parseUpdateLocation(serverResponse));
                break;
            default:
                result = Formatter.message(Parser.parseGenericResult(serverResponse));
                break;
        }
        return result;
    }

    /**
     * static method for formatting result as an array list
     *
     * @param serverScript script executed on the server
     * @param serverResponse returned xml data from the server
     * @return arraylist of returned data from the server
     */
    public static ArrayList formatMultipleResults(Script serverScript, String serverResponse){

        ArrayList results;

        switch(Name.valueOf(serverScript.name)){

            case PLACE_LIST:
                results = Parser.parsePlaces(serverResponse);
                break;

            case EVENT_LIST:
                results = Parser.parseEvents(serverResponse);
                break;

            case EVENT_DETAILS:
                results = Parser.parseEvents(serverResponse);
                break;

            case GET_FRIEND_REQUESTS:
                results = Parser.parseFriendRequests(serverResponse);
                break;

            case FRIEND_LIST:
                results = Parser.parseFriendsList(serverResponse);
                break;

            case SENT_MESSAGES:
                results = Parser.parseMessages(serverResponse);
                break;

            case RECEIVED_MESSAGES:
                results = Parser.parseMessages(serverResponse);
                break;

            case QUERY_USER_DETAILS:
                results = Parser.parseMultipleUsers(serverResponse);
                break;

            default:
                results = null;
                break;
        }

        return results;
    }

}
