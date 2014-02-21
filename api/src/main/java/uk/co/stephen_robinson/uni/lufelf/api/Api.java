package uk.co.stephen_robinson.uni.lufelf.api;

import android.content.Context;

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

    public Api(Context context, Version v){
        sessionManager = new SessionManager(context);
        version = v.getValue();
        v1 = new V1();
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static Integer getVersion() {
        return version;
    }
}
