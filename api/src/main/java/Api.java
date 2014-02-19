import android.content.Context;
import android.util.Log;

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
