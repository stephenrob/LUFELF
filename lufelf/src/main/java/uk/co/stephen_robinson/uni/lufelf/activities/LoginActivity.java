package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.fragments.LoginFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.NavigationDrawerFragment;
import uk.co.stephen_robinson.uni.lufelf.fragments.RegisterFragment;

public class LoginActivity extends Activity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment;


    public void setLayout(int id){
        //get the fragment manager
        FragmentManager fragmentManager = getFragmentManager();
        //check if either the child or group position differs
        //if it does swap frgments
        if(id== R.layout.fragment_login)
            fragmentManager.beginTransaction().replace(R.id.container, LoginFragment.newInstance(),"login").addToBackStack(null).commit();
        if(id == R.layout.fragment_register)
            fragmentManager.beginTransaction().replace(R.id.container, RegisterFragment.newInstance(),"register").addToBackStack(null).commit();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.overridePendingTransition(0,R.anim.fade_in);
        View v=findViewById(R.id.container);
        setLayout(R.layout.fragment_login);
    }
    @Override
    public void onBackPressed() {
        //get the fragment manager
        FragmentManager fm=getFragmentManager();

        //get the size of the backstack
        int size=fm.getBackStackEntryCount();

        //if the size is greater than one pop
        //otherwise close the app
        if(size>1)
            this.getFragmentManager().popBackStack();
        else
            finish();
    }
}
