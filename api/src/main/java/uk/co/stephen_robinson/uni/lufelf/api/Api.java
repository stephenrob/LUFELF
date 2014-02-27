package uk.co.stephen_robinson.uni.lufelf.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import uk.co.stephen_robinson.uni.lufelf.api.v1.V1;

/**
 * Created by Stephen on 19/02/14.
 */
public class Api {

    public enum Version{
        V1(1);

        private final int value;

        private Version(final int newValue){
            value = newValue;
        }

        public Integer getValue(){ return value; }
    }

    private static SessionManager sessionManager;
    private static Integer version;
    public final V1 v1;
    private static Context context;

    public Api(Context c, Version v){
        sessionManager = new SessionManager(c);
        version = v.getValue();
        v1 = new V1();
        context = c;
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static Integer getVersion() {
        return version;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
