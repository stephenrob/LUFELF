package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.Network.Script;
import uk.co.stephen_robinson.uni.lufelf.api.v1.Scripts;

/**
 * Created by Stephen on 21/02/14.
 */

/**
 * @author stephen
 *
 * Formats returned data into hashtables for single requests
 */
public class Formatter {

    /**
     * Turns returned message into a hashtable
     *
     * @param m message returned to format
     * @return message encoded in a hashtable
     */
    public static Hashtable message(Message m){
        Hashtable message = new Hashtable();

        message.put(Message.STATUS, m.status);
        message.put(Message.CODE, m.statusCode);
        message.put(Message.MESSAGE, m.message);

        return message;
    }

    /**
     * Turns a returned user into a hashtable with the result
     *
     * @param userDetails user returned to format
     * @param serverScript script on the server that was executed to ensure correct details are only returned to the calling method
     * @return relevant user details encoded in a hashtable
     */
    public static Hashtable userDetails(User userDetails, Script serverScript){
        Hashtable results = new Hashtable();

        switch (Scripts.Name.valueOf(serverScript.name)){
            case CREATE_USER:
                if (userDetails.status.equals("ok")){
                    results.put(Message.STATUS, Message.SUCCESS);
                    results.put(User.IS_NEW, userDetails.is_new);
                    results.put(User.USER_ID, userDetails.user_id);
                    results.put(User.NAME, userDetails.name);
                    results.put(User.LIBRARY_NUMBER, userDetails.lib_no);
                    results.put(Message.CODE, userDetails.statusCode);
                    results.put(Message.MESSAGE, userDetails.message);
                }
                else if (userDetails.status.equals("fail")){
                    results.put(Message.STATUS, Message.FAILURE);
                    results.put(Message.CODE, userDetails.statusCode);
                    results.put(Message.MESSAGE, userDetails.message);
                }
                break;

            case LOGIN_USER:
                if (userDetails.status.equals("ok")){
                    results.put(Message.STATUS, Message.SUCCESS);
                    results.put(Message.CODE, userDetails.statusCode);
                    results.put(User.USER_ID, userDetails.user_id);
                    results.put(Message.MESSAGE, userDetails.message);
                }
                else if (userDetails.status.equals("fail")){
                    results.put(Message.STATUS, Message.FAILURE);
                    results.put(Message.CODE, userDetails.statusCode);
                    results.put(Message.MESSAGE, userDetails.message);
                }
                break;

            case QUERY_USER_DETAILS:
                if (userDetails.status.equals("ok")){
                    results.put(Message.STATUS, Message.SUCCESS);
                    results.put(Message.CODE, userDetails.statusCode);
                    results.put(Message.MESSAGE, userDetails.message);
                    results.put(User.USER_ID, userDetails.user_id);
                    results.put(User.NAME, userDetails.name);
                    results.put(User.LIBRARY_NUMBER, userDetails.lib_no);
                    results.put(User.USERNAME, userDetails.username);
                    results.put(User.DATE_OF_BIRTH, userDetails.dob);
                    results.put(User.TYPE, userDetails.type);
                    results.put(User.DESCRIPTION, userDetails.description);
                }
                else if (userDetails.status.equals("fail")){
                    results.put(Message.STATUS, Message.FAILURE);
                    results.put(Message.CODE, userDetails.statusCode);
                    results.put(Message.MESSAGE, userDetails.message);
                }
                break;
        }

        return results;
    }

    /**
     * Relevant information from updating location encoded in a hashtable
     *
     * @param status location status to encode in hashtable
     * @return hashtable of location status information encoded in a hashtable
     */
    public static Hashtable updateLocation(Status status){
        Hashtable result = new Hashtable();

        result.put(Status.STATUS, status.status);
        result.put(Status.CODE, status.statusCode);
        result.put(Status.MESSAGE, status.message);
        result.put(Status.IS_NEW, status.is_new);

        return result;

    }

}
