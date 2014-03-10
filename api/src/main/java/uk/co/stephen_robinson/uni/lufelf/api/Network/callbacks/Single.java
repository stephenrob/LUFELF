package uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks;

import java.util.Hashtable;

/**
 * Created by Stephen on 21/02/14.
 */

/**
 * @author stephen
 * Interface class for single request callbacks
 */
public interface Single {
    /**
     * Callback interface for returning single data item from the database
     *
     * @param result hashtable with api result
     */
    void results(Hashtable result);
}
