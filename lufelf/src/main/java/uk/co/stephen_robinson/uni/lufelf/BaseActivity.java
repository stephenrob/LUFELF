package uk.co.stephen_robinson.uni.lufelf;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.util.Log;

import com.google.android.gms.maps.MapFragment;

/**
 * Created by James on 30/01/2014.
 */
public class BaseActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // array to hold the various fragments used in the navigation drawer.
        Fragment[] fragments={NewsFeedFragment.newInstance(),FriendsFragment.newInstance(),EventsFragment.newInstance(), MapViewFragment.newInstance(),LoginFragment.newInstance()};

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.container, fragments[position]).commit();
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
