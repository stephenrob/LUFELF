package uk.co.stephen_robinson.uni.lufelf.api;

import android.content.Context;

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

    public Api(Context context, Version v){
        sessionManager = new SessionManager(context);
        version = v.getValue();
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static Integer getVersion() {
        return version;
    }
}
