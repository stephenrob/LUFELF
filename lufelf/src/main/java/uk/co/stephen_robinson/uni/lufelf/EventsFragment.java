package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.NetworkCallback;

/**
 * @author James
 * Displays a list of events in a fragment.
 */
public class EventsFragment extends BaseFragment{

    ListView list;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    public EventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_events, container, false);
        setContext(rootView.getContext());
        showActivitySpinner();

        //get the events listview
        list = (ListView)rootView.findViewById(R.id.events_listview);
        //add events to the listview
        addEvents(inflater, container, rootView);

        //set item click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                resetIndexes();
                EventListItem item = (EventListItem) list.getItemAtPosition(i);
                fragmentManager.beginTransaction().replace(R.id.container, EventSubFragment.newInstance(item), "EventSubView").addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    /**
     * add the events to the view
     * @param inflater the inflater for the layout
     * @param container the view group container
     * @param rootView the rootview of the fragment
     */
    public void addEvents(LayoutInflater inflater, ViewGroup container,View rootView){
        NetworkCallback nc=new NetworkCallback() {
            @Override
            public void results(Hashtable result) {
                //result.get()
                Log.e("CRaP","HERE");
                hideActivitySpinner();
            }
        };
        api.getAllEvents(nc);
        //make arraylist navdraweritems
        ArrayList navigationItems=new ArrayList<NavDrawerItem>();

        //add fake items
        for(int i=0;i<30;i++)
            navigationItems.add(new EventListItem("item "+i,R.drawable.ic_location,String.valueOf(i),"Event Name "+i,"Creator "+i,new LatLng(i,i),i+":"+i,"This is a description for EVENT "+i));

        //set the adapter
        list.setAdapter(new EventItemAdapter(rootView.getContext(), navigationItems));
    }
}