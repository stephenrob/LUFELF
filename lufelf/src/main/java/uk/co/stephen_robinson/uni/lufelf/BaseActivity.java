package uk.co.stephen_robinson.uni.lufelf;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by James on 30/01/2014.
 */
public class BaseActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    int currentChildPosition=-1;
    int currentGroupPosition=-1;
    private Dialog dialog;
    // array to hold the various fragments used in the navigation drawer.
    Fragment[][] fragments={{MapViewFragment.newInstance(null)},
            {RegisterFragment.newInstance(),FriendsFragment.newInstance()},
            {EventsFragment.newInstance(),CreateEventFragment.newInstance(),EventsFragment.newInstance()},
            {SettingsFragment.newInstance()},
            {LoginFragment.newInstance()},
            {RegisterFragment.newInstance()}};


    String[][] tags={{"Locations"},
            {"Friends","Add Friends"},
            {"Events","Create Event","Remove Event"},
            {"Settings"},
            {"Login"},
            {"Register"}};

    @Override
    public void onNavigationDrawerItemSelected(int groupPos,int position) {
        Log.e("CRAP",groupPos+" "+position+" "+fragments[groupPos][position].toString());
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if(currentChildPosition!=position||currentGroupPosition!=groupPos){
            fragmentManager.beginTransaction().replace(R.id.container, fragments[groupPos][position],tags[groupPos][position]).addToBackStack(null).commit();
            Log.e("CRAP","INSIDE IF");
        }
        currentChildPosition=position;
        currentGroupPosition=groupPos;
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
        for(int outer=0;outer<tags.length;outer++){
            for(int inner=0;inner<tags[outer].length;inner++){
                if(tags[outer][inner].equals(title)){
                    onNavigationDrawerItemSelected(outer,inner);
                    break;
                }
            }
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
        /*while(currentFragment==null&&count<tags.length)
            currentFragment=fm.findFragmentByTag(tags[count]);*/

    }
    public void updateCurrentPositions(int groupPos,int position){
        currentGroupPosition=groupPos;
        currentChildPosition=position;
    }

}
