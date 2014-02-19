package uk.co.stephen_robinson.uni.lufelf.api.Network;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.xml.Message;

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

}
