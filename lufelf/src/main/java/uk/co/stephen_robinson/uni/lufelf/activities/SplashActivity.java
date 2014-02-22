package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.fragments.NavigationDrawerFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.SplashFragment;

public class SplashActivity extends BaseActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment;


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
        Single nc = new Single() {
            @Override
            public void results(Hashtable result) {
                try{
                    Thread.sleep(10000);
                }catch (Exception e){

                }
                Intent swapToLogin=new Intent(getBaseContext(),LoginActivity.class);
                startActivity(swapToLogin);
                overridePendingTransition(R.anim.fade_out,0);
            }
        };

        Api api = new Api(getApplicationContext(), Api.Version.V1);
        api.v1.loginUser("test", "test", nc);

    }

}
