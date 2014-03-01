package uk.co.stephen_robinson.uni.lufelf.activities;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.fragments.NavigationDrawerFragment;

public class MainActivity extends BaseActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment;
    private int priority;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle args=getIntent().getExtras();
        if(args==null)
            priority=1;
        else
            this.priority=args.getInt("priority");
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the actionbar
        ActionBar actionBar=getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        //mNavigationDrawerFragment=NavigationDrawerFragment.instanceOf(0);



        //get the navigationdrawer
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Set the default index
        onNavigationDrawerItemSelected(0,0);
    }

    /**
     * Method called to restore actionbar
     */
    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            Log.e("crap",String.valueOf(priority));
            if(priority==1)
                getMenuInflater().inflate(R.menu.news_feed, menu);
            else
                getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    public void logout(MenuItem item){
        FragmentManager manager = getFragmentManager();
        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Intent login = new Intent(this,LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        finish();
    }
}
