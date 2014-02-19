package uk.co.stephen_robinson.uni.lufelf.api;

import android.content.Context;

/**
 * Created by Stephen on 19/02/14.
 */
public class Api {

    private static SessionManager sessionManager;
    public final V1 v1;

    public Api(Context context){
        sessionManager = new SessionManager(context);
        v1 = new V1();
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }
}
