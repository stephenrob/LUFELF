package uk.co.stephen_robinson.uni.lufelf.fragments;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.ExpandableListNavAdapter;
import uk.co.stephen_robinson.uni.lufelf.adapters.NavDrawerGroup;
import uk.co.stephen_robinson.uni.lufelf.adapters.NavDrawerItem;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_GROUP_SELECTED_POSITION = "selected_navigation_drawer_group";

    private static final String STATE_CHILD_SELECTED_POSITION = "selected_navigation_drawer_child";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedGroupPosition = 0;
    private int mCurrentSelectedChildPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private int priority;

    public static NavigationDrawerFragment instanceOf(int priority){
        NavigationDrawerFragment f = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putInt("priority",priority);
        f.setArguments(args);
        return f;
    }
    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedGroupPosition=savedInstanceState.getInt(STATE_GROUP_SELECTED_POSITION);
            mCurrentSelectedChildPosition = savedInstanceState.getInt(STATE_CHILD_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        priority = getActivity().getIntent().getExtras().getInt("priority");
        mDrawerListView = (ExpandableListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //set group click handler
        mDrawerListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int pos, long l) {
                int index=expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(pos));
                NavDrawerGroup group=(NavDrawerGroup)mDrawerListView.getItemAtPosition(index);
                if(group.countItems()==0){
                    selectItem(pos, 0);
                }
                return false;
            }
        });
        //set child click handler
        mDrawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long id) {
                int index=expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPos, childPos));
                mDrawerListView.setItemChecked(index, true);
                selectItem(groupPos, childPos);
                return false;
            }
        });


        //choose which set of options to display
        ArrayList navigationGroups;

        if(priority==1)
            navigationGroups=setUpForUser();
        else
            navigationGroups=setUpForGuest();
        //apply the nav adapter
        mDrawerListView.setAdapter(new ExpandableListNavAdapter(
                getActionBar().getThemedContext(),
                navigationGroups));

        //set the current item checked
        mDrawerListView.setItemChecked(mCurrentSelectedGroupPosition, true);

        //return the nav drawer
        return mDrawerListView;
    }
    public ArrayList setUpForGuest(){
        //create the items for the nav drawer
        ArrayList navigationGroups=new ArrayList<NavDrawerGroup>();

        navigationGroups.add(new NavDrawerGroup(getString(R.string.locations_activity),R.drawable.ic_location_place,new ArrayList<NavDrawerItem>()));

        return navigationGroups;
    }
    public ArrayList setUpForUser(){
        //create the items for the nav drawer
        ArrayList navigationGroups=new ArrayList<NavDrawerGroup>();

        ArrayList friendsItems=new ArrayList<NavDrawerItem>();
        friendsItems.add(new NavDrawerItem(getString(R.string.view_friends_text), 0));
        friendsItems.add(new NavDrawerItem(getString(R.string.add_friends_text), 0));

        ArrayList eventItems=new ArrayList<NavDrawerItem>();
        eventItems.add(new NavDrawerItem(getString(R.string.view_event_text), 0));
        eventItems.add(new NavDrawerItem(getString(R.string.create_event_text), 0));
        eventItems.add(new NavDrawerItem(getString(R.string.remove_event_text), 0));

        ArrayList placeItems=new ArrayList<NavDrawerItem>();
        placeItems.add(new NavDrawerItem("View Locations",0));
        placeItems.add(new NavDrawerItem("Create Location",0));

        navigationGroups.add(new NavDrawerGroup(getString(R.string.locations_activity),R.drawable.ic_location_place,placeItems));
        navigationGroups.add(new NavDrawerGroup(getString(R.string.friends_activity),R.drawable.ic_friends,friendsItems));
        navigationGroups.add(new NavDrawerGroup(getString(R.string.events_activity),R.drawable.ic_events,eventItems));
        navigationGroups.add(new NavDrawerGroup(getString(R.string.messages_activity),R.drawable.ic_message,new ArrayList<NavDrawerItem>()));
        navigationGroups.add(new NavDrawerGroup(getString(R.string.settings_activity),R.drawable.ic_settings,new ArrayList<NavDrawerItem>()));
        navigationGroups.add(new NavDrawerGroup(getString(R.string.login_text),0,new ArrayList<NavDrawerItem>()));
        navigationGroups.add(new NavDrawerGroup(getString(R.string.register_text),0,new ArrayList<NavDrawerItem>()));
        navigationGroups.add(new NavDrawerGroup(getString(R.string.search_activity),0,new ArrayList<NavDrawerItem>()));
        return navigationGroups;
    }
    /**
     * this method checks if the drawer is open
     * @return boolean if the drawer is open
     */
    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                //getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                //getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     *
     * @param groupPos
     * @param position
     */
    private void selectItem(int groupPos,int position) {



        int previousGroup=mCurrentSelectedGroupPosition;
        int previousPos=mCurrentSelectedChildPosition;

        if(previousGroup==groupPos&&previousPos==position){
            mDrawerLayout.closeDrawer(mFragmentContainerView);
            Toast.makeText(getActivity().getBaseContext(), getString(R.string.already_here), Toast.LENGTH_SHORT).show();
            return;
        }

        mCurrentSelectedGroupPosition= groupPos;
        mCurrentSelectedChildPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setSelectedChild(groupPos,position,false);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }

        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(groupPos,position);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_GROUP_SELECTED_POSITION, mCurrentSelectedGroupPosition);
        outState.putInt(STATE_CHILD_SELECTED_POSITION, mCurrentSelectedChildPosition);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            //showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int groupPos, int position);
    }
    public void updateChildGroup(int groupPos,int childPos){
        this.mCurrentSelectedGroupPosition=groupPos;
        this.mCurrentSelectedChildPosition=childPos;
    }
}
