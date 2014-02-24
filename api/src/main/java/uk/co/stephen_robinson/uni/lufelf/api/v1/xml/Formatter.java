package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.network.Script;
import uk.co.stephen_robinson.uni.lufelf.api.v1.Scripts;

/**
 * Created by Stephen on 21/02/14.
 */
public class Formatter {

    public static Hashtable message(Message m){
        Hashtable message = new Hashtable();

        message.put(Message.STATUS, m.status);
        message.put(Message.CODE, m.statusCode);
        message.put(Message.MESSAGE, m.message);

        return message;
    }

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

    public static Hashtable updateLocation(Status status){
        Hashtable results = new Hashtable();

        return results;

    }

}
