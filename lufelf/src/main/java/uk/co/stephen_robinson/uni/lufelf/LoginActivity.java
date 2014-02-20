package uk.co.stephen_robinson.uni.lufelf;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends BaseActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment;


    public void setLayout(int id){
        //get the fragment manager
        FragmentManager fragmentManager = getFragmentManager();
        //check if either the child or group position differs
        //if it does swap frgments
        if(id==R.layout.fragment_login)
            fragmentManager.beginTransaction().replace(R.id.container, LoginFragment.newInstance(),"login").addToBackStack(null).commit();
        if(id == R.layout.fragment_register)
            fragmentManager.beginTransaction().replace(R.id.container, RegisterFragment.newInstance(),"register").addToBackStack(null).commit();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View v=findViewById(R.id.container);
        setLayout(R.layout.fragment_login);
    }

}
