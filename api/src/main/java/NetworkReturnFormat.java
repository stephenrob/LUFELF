import java.util.Hashtable;

/**
 * Created by Stephen on 17/02/14.
 */
public class NetworkReturnFormat {

    public static Hashtable message(XmlMessage message){
        Hashtable results = new Hashtable();

        results.put(XmlMessage.STATUS, message.status);
        results.put(XmlMessage.MESSAGE, message.message);

        return results;
    }

    public static Hashtable userDetails(XmlUserDetails userDetails, Network.Script serverScript){
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
