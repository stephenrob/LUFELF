package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.MenuItem;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.fragments.CreateEventFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.CreatePlaceFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.EventsFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.FriendsFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.FriendsSubFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.LoginFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.MapViewFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.MessagesFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.NavigationDrawerFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.RegisterFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.SearchFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.SettingsFragment;

/**
 * @author James
 * the base activity that holds the parent methods for any activity in the app
 */
public class BaseActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks{


    int currentChildPosition=-1;
    int currentGroupPosition=-1;


    // array to hold the various fragments used in the navigation drawer.
    Fragment[][] fragments={{MapViewFragment.newInstance(null), CreatePlaceFragment.newInstance()},
            {FriendsFragment.newInstance(), FriendsSubFragment.newInstance()},
            {EventsFragment.newInstance(), CreateEventFragment.newInstance(),EventsFragment.newInstance()},
            {MessagesFragment.newInstance()},
            {SettingsFragment.newInstance()},
            {LoginFragment.newInstance()},
            {RegisterFragment.newInstance()},
            {SearchFragment.newInstance(SearchFragment.friendsSearch)}};


    String[][] tags={{"Locations","Create Location"},
            {"Friends","Add Friends"},
            {"Events","Create Event","Remove Event"},
            {"Messages"},
            {"Settings"},
            {"Login"},
            {"Register"},
            {"Search"}};

    /**
     *Handles on navigationdrawer item selected (swap fragments)
     *
     * @param groupPos the group position of the fragment
     * @param position the list position inside the group
     */
    @Override
    public void onNavigationDrawerItemSelected(int groupPos,int position) {

        //get the fragment manager
        FragmentManager fragmentManager = getFragmentManager();

        //check if either the child or group position differs
        //if it does swap frgments

        if(currentChildPosition!=position||currentGroupPosition!=groupPos)
            fragmentManager.beginTransaction().replace(R.id.container, fragments[groupPos][position],tags[groupPos][position]).addToBackStack(null).commit();

        //update the current child and group positions
        currentChildPosition=position;
        currentGroupPosition=groupPos;
    }

    //implement onPause method
    @Override
    public void onPause(){
        super.onPause();
    }

    //implement onResume method
    @Override
    public void onResume(){
        super.onResume();
    }

    /**
     *if an action bar button is clicked - this is where it is handled
     * @param item The menu item clicked
     */
    //
    public void handleButton(MenuItem item){
        findTagPosition(item.getTitle().toString());
        onNavigationDrawerItemSelected(currentGroupPosition,currentChildPosition);
    }

    /**
     * Find the position of a fragment by using it's tag and updates the current group and child positions
     * @param tag the fragments' tag
     */
    public void findTagPosition(String tag){
        for(int outer=0;outer<tags.length;outer++){
            for(int inner=0;inner<tags[outer].length;inner++){
                if(tags[outer][inner].equals(tag)){
                    currentGroupPosition=outer;
                    currentChildPosition=inner;
                    return;
                }
            }
        }
        currentGroupPosition=0;
        currentChildPosition=0;

    }
    //custom onBackPressed action to allow backward navigation
    @Override
    public void onBackPressed() {
        //get the fragment manager
        FragmentManager fm=getFragmentManager();

        //get the size of the backstack
        int size=fm.getBackStackEntryCount();

        //if the size is greater than one pop
        //otherwise close the app
        if(size>1){
            this.getFragmentManager().popBackStackImmediate();
            Fragment f = getFragmentManager().findFragmentById(R.id.container);
            findTagPosition(f.getTag());
        }else{
            this.getFragmentManager().popBackStack();
            finish();
        }

    }

    /**
     *  method to update the currently held positions of the navigationdrawer options
     * @param groupPos the group position of the fragment
     * @param position the list position inside the group
     */

    public void updateCurrentPositions(int groupPos,int position){
        currentGroupPosition=groupPos;
        currentChildPosition=position;
    }

}
