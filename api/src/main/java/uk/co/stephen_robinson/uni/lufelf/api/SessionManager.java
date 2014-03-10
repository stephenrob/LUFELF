package uk.co.stephen_robinson.uni.lufelf.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Stephen on 18/02/14.
 */

/**
 * @author stephen
 * Provides methods for interacting with a users session stored in shared preferences
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

    public static final String KEY_PRIVACY = "privacy";

    /**
     * Constructor for the session manager
     *
     * @param context Context in which to initialise shared preferences
     */

    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    /**
     * creates a new login session in shared preferences
     *
     * @param username Users username
     * @param password Users password
     * @param user_id Users User ID
     */
    public void createLoginSession(String username, String password, String user_id){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_USERID, user_id);
        editor.putString(KEY_PRIVACY, Integer.toString(1));

        editor.commit();
    }

    /**
     * Updates user id
     *
     * @param user_id New user id
     */
    public void updateUserId(String user_id){
        editor.putString(KEY_USERID, user_id);

        editor.commit();
    }

    /**
     * Returns the value stored in password field
     *
     * @return string with users password
     */
    public String getPassword(){
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    /**
     * Method for getting all of the users details
     *
     * @return hashmap with all user details
     */

    public HashMap <String, String> getUserDetails(){
        HashMap<String, String> userDetails = new HashMap<String, String>();

        userDetails.put(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, null));
        userDetails.put(KEY_USERID, sharedPreferences.getString(KEY_USERID, null));
        userDetails.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));

        return userDetails;
    }

    /**
     * Checks if the user is logged in
     *
     * @return Boolean with users login status
     */

    public boolean checkLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    /**
     * Method for setting users privacy
     *
     * @param privacy Privacy value to set
     */

    public void setPrivacy(String privacy){
        editor.putString(KEY_PRIVACY, privacy);

        editor.commit();
    }

    /**
     * Method for getting users privacy
     *
     * @return String with the users privacy setting
     */
    public String getPrivacy(){
        return sharedPreferences.getString(KEY_PRIVACY, null);
    }

    /**
     * Method for logging out the user
     */
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

}
