package uk.co.stephen_robinson.uni.lufelf;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import uk.co.stephen_robinson.uni.*;

import com.google.android.gms.maps.MapFragment;

/**
 * Created by James on 30/01/2014.
 */
public class BaseActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    int currentPosition=-1;

    // array to hold the various fragments used in the navigation drawer.
    Fragment[] fragments={NewsFeedFragment.newInstance(),FriendsFragment.newInstance(),EventsFragment.newInstance(), MapViewFragment.newInstance(),LoginFragment.newInstance(),RegisterFragment.newInstance(),CreateEventFragment.newInstance()};
    String[] tags={"newsfeed","Friends","Events","Locations","Settings","register","create_event"};
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if(currentPosition!=position)
            fragmentManager.beginTransaction().replace(R.id.container, fragments[position],tags[position]).addToBackStack(null).commit();
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
    public void handleButton(MenuItem item){
        //get item title
        String title=String.valueOf(item.getTitle());

        //go through tags array until there is a match, then switch
        for(int i=0;i<tags.length;i++)
            if(tags[i].equals(title)){
                onNavigationDrawerItemSelected(i);
                break;
            }
    }
    @Override
    public void onBackPressed() {

        FragmentManager fm=getFragmentManager();
        int size=fm.getBackStackEntryCount();

        Log.d("CRAP", "size "+size);
        if(size>1)
            this.getFragmentManager().popBackStack();
        else
            finish();

        Fragment currentFragment=null;
        int count=0;
        while(currentFragment==null&&count<tags.length)
            currentFragment=fm.findFragmentByTag(tags[count]);

    }
}
