package uk.co.stephen_robinson.uni.lufelf;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.NetworkCallback;
import uk.co.stephen_robinson.uni.lufelf.api.V1;

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
        NetworkCallback nc = new NetworkCallback() {
            @Override
            public void results(Hashtable result) {
                try{
                    Thread.sleep(10000);
                }catch (Exception e){

                }
                Intent swapToLogin=new Intent(getBaseContext(),LoginActivity.class);
                startActivity(swapToLogin);
                overridePendingTransition(R.anim.fade_out,R.anim.fade_in );
            }
        };

        V1 api = new V1();
        api.loginUser("test","test", nc);

    }

}
