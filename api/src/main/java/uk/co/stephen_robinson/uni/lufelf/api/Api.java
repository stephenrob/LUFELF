package uk.co.stephen_robinson.uni.lufelf.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import uk.co.stephen_robinson.uni.lufelf.api.v1.V1;

/**
 * Created by Stephen on 19/02/14.
 */

/**
 * @author stephen
 * Main interface to the generic api class
 */
public class Api {

    /**
     * Enum for storing the version number
     */
    public enum Version{
        V1(1);

        private final int value;

        /**
         * Method for setting the value number of the enum
         *
         * @param newValue value to set version to
         */

        private Version(final int newValue){
            value = newValue;
        }

        /**
         * Method for getting the version value
         *
         * @return integer of the api version
         */
        public Integer getValue(){ return value; }
    }

    private static SessionManager sessionManager;
    private static Integer version;
    public final V1 v1;
    private static Context context;

    /**
     * Constructor for initialising a new instance of the api
     *
     * @param c Context for the api to initialise its session manager
     * @param v Version of the api to initialise and use
     */
    public Api(Context c, Version v){
        sessionManager = new SessionManager(c);
        version = v.getValue();
        v1 = new V1();
        context = c;
    }

    /**
     * Static method for returning the current session manager
     *
     * @return Session Manager
     */

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * Static method for returning the current api version initialised
     *
     * @return
     */
    public static Integer getVersion() {
        return version;
    }

    /**
     * Static method for checking if the network is available
     *
     * @return Boolean indicating network availability
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
