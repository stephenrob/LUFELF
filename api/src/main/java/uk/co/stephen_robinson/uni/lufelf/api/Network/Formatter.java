package uk.co.stephen_robinson.uni.lufelf.api.Network;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.xml.User;

/**
 * Created by Stephen on 19/02/14.
 */
public class Formatter {

    protected static Hashtable message(Message m){
        Hashtable message = new Hashtable();

        message.put(Network.STATUS, m.status);
        message.put(Network.STATUS_CODE, m.statusCode);
        message.put(Network.MESSAGE, m.message);

        return message;
    }

    protected static Hashtable userDetails(User userDetails, Network.Script serverScript){
        Hashtable results = new Hashtable();

        switch (serverScript){
            case CREATE_USER:
                if (userDetails.status.equals("ok")){
                    results.put(Network.STATUS, Network.SUCCESS);
                    results.put(User.IS_NEW, userDetails.is_new);
                    results.put(User.USER_ID, userDetails.user_id);
                    results.put(User.NAME, userDetails.name);
                    results.put(User.LIBRARY_NUMBER, userDetails.lib_no);
                }
                else if (userDetails.status.equals("false")){
                    results.put(Network.STATUS, Network.FAILURE);
                    results.put(Network.STATUS_CODE, userDetails.statusCode);
                    results.put(Network.MESSAGE, userDetails.message);
                }
                break;

            case LOGIN_USER:
                if (userDetails.status.equals("ok")){
                    results.put(Network.STATUS, Network.SUCCESS);
                    results.put(User.USER_ID, userDetails.user_id);
                }
                else if (userDetails.status.equals("false")){
                    results.put(Network.STATUS, Network.FAILURE);
                    results.put(Network.STATUS_CODE, userDetails.statusCode);
                    results.put(Network.MESSAGE, userDetails.message);
                }
                break;

            case QUERY_USER_DETAILS:
                if (userDetails.status.equals("ok")){
                    results.put(Network.STATUS, Network.SUCCESS);
                    results.put(User.USER_ID, userDetails.user_id);
                    results.put(User.NAME, userDetails.name);
                    results.put(User.LIBRARY_NUMBER, userDetails.lib_no);
                    results.put(User.USERNAME, userDetails.username);
                    results.put(User.DATE_OF_BIRTH, userDetails.dob);
                    results.put(User.TYPE, userDetails.type);
                    results.put(User.DESCRIPTION, userDetails.description);
                }
                else if (userDetails.status.equals("false")){
                    results.put(Network.STATUS, Network.FAILURE);
                    results.put(Network.STATUS_CODE, userDetails.statusCode);
                    results.put(Network.MESSAGE, userDetails.message);
                }
                break;
        }

        return results;
    }

}
