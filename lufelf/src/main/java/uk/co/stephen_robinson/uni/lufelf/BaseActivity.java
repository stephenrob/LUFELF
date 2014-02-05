package uk.co.stephen_robinson.uni.lufelf;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.util.Log;
import uk.co.stephen_robinson.uni.*;

import com.google.android.gms.maps.MapFragment;

/**
 * Created by James on 30/01/2014.
 */
public class BaseActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    int currentPosition=-1;

    // array to hold the various fragments used in the navigation drawer.
    Fragment[] fragments={NewsFeedFragment.newInstance(),FriendsFragment.newInstance(),EventsFragment.newInstance(), MapViewFragment.newInstance(),LoginFragment.newInstance(),RegisterFragment.newInstance()};

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.e("CRAP","Current pos "+currentPosition+" position "+position);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if(currentPosition!=position)
            fragmentManager.beginTransaction().replace(R.id.container, fragments[position]).addToBackStack(null).commit();
        currentPosition=position;
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

}
