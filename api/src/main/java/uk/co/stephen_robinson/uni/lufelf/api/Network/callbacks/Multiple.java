package uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks;

import java.util.ArrayList;

/**
 * Created by Stephen on 21/02/14.
 */

/**
 * @author stephen
 * Interface class for multiple request callback
 */
public interface Multiple {
    /**
     * Callback interface for returning multiple data from the api
     *
     * @param result array list of resultant data to deal with
     */
    void results(ArrayList result);
}
