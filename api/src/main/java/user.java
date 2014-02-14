import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Stephen on 14/02/2014.
 */
public class user {


    // Register new user with LUFELF server

    public Hashtable register(Hashtable params){

        Hashtable results = new Hashtable();

        return results;

    }

    // Login user to system and store login details locally

    public Hashtable login(Hashtable params){

        Hashtable results = new Hashtable();

        return results;

    }

    // Get user details

    public Hashtable getDetails(Hashtable params){

        Hashtable results = new Hashtable();

        String searchField = params.get("Search Field").toString();
        String searchValue = params.get("Search Value").toString();

        return results;

    }

    // Delete user account on LUFELF Server

    public Hashtable delete(Hashtable params){

        Hashtable results = new Hashtable();

        return results;

    }

}
