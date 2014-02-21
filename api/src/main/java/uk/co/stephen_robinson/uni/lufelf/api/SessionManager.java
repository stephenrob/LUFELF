package uk.co.stephen_robinson.uni.lufelf.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Stephen on 18/02/14.
 */
public class SessionManager {

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LUFELF";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_USERID = "user_id";

    public static final String KEY_PASSWORD = "password";

    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public void createLoginSession(String username, String password, String user_id){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_USERID, user_id);

        editor.commit();
    }

    public void updateUserId(String user_id){
        editor.putString(KEY_USERID, user_id);

        editor.commit();
    }

    public String getPassword(){
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public HashMap <String, String> getUserDetails(){
        HashMap<String, String> userDetails = new HashMap<String, String>();

        userDetails.put(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, null));
        userDetails.put(KEY_USERID, sharedPreferences.getString(KEY_USERID, null));
        userDetails.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));

        return userDetails;
    }

    protected boolean checkLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

}
