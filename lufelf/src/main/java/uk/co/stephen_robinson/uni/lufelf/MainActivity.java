package uk.co.stephen_robinson.uni.lufelf;
import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends BaseActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
        ActionBar actionBar=getActionBar();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }


    public boolean showFriends(MenuItem item){
        Log.e("CRAP","Friends");
        return true;
    }
    public boolean showEvents(MenuItem item){
        Log.e("CRAP","Events");
        return true;
    }
    public boolean showLocations(MenuItem item){
        Log.e("CRAP","Locations");
        return true;
    }
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.news_feed_activity);
                break;
            case 2:
                mTitle = getString(R.string.friends_activity);
                break;
            case 3:
                mTitle = getString(R.string.events_activity);
                break;
            case 4:
                mTitle = getString(R.string.locations_activity);
                break;
            case 5:
                mTitle = getString(R.string.settings_activity);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.news_feed, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        return super.onOptionsItemSelected(item);
    }


}
