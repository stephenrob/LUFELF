package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.FragmentManager;
import android.os.Bundle;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.fragments.SplashFragment;

public class SplashActivity extends BaseActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */


    public void setLayout(){
        //get the fragment manager
        FragmentManager fragmentManager = getFragmentManager();
        //check if either the child or group position differs
        //if it does swap frgments
        fragmentManager.beginTransaction().replace(R.id.container, SplashFragment.newInstance(),"Splash").commit();



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setLayout();


    }

}
