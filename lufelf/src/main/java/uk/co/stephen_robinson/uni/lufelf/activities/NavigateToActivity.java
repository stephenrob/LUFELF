package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.fragments.MapViewFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.NavigationDrawerFragment;

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
            fragmentManager.beginTransaction().replace(R.id.container, MapViewFragment.newInstance(new LatLng(args.getDouble("lat"),args.getDouble("long"))),"NavigateTo").addToBackStack(null).commit();
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
}
