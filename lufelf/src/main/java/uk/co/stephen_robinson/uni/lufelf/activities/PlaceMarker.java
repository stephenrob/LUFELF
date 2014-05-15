package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.fragments.NavigationDrawerFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.PlaceMarkerFragment;

/**
 * @author James
 * the navigatetoactivity manages the map fragment which navigates a user from A -> B
 */

public class PlaceMarker extends BaseActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment;

    public void setLayout(int id){
        //get the fragment manager
        FragmentManager fragmentManager = getFragmentManager();
        //check if either the child or group position differs
        //if it does swap frgments
        if(id== R.layout.fragment_place_marker){
            fragmentManager.beginTransaction().replace(R.id.container, PlaceMarkerFragment.newInstance(),"NavigateTo").commit();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to);
        View v=findViewById(R.id.container);
        setLayout(R.layout.fragment_place_marker);
    }
    /*
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

    }*/
}
