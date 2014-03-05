package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.fragments.MapViewFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.NavigationDrawerFragment;

/**
 * @author James
 * the navigatetoactivity manages the map fragment which navigates a user from A -> B
 */

public class NavigateToActivity extends BaseActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment;


    public void setLayout(int id){
        //get the fragment manager
        FragmentManager fragmentManager = getFragmentManager();
        //check if either the child or group position differs
        //if it does swap frgments
        if(id== R.layout.fragment_map){
            Bundle args = getIntent().getExtras();
            fragmentManager.beginTransaction().replace(R.id.container, MapViewFragment.newInstance(new LatLng(args.getDouble("lat"),args.getDouble("long"))),"NavigateTo").addToBackStack(null).commitAllowingStateLoss();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to);
        View v=findViewById(R.id.container);
        setLayout(R.layout.fragment_map);
    }
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
}
