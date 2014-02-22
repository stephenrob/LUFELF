package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventListItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.NavDrawerItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;


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

        Multiple multipleCallback=new Multiple() {
            @Override
            public void results(ArrayList result) {
                ArrayList navigationItems=new ArrayList<NavDrawerItem>();
                Message m = (Message)result.get(result.size()-1);
                if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){
                    for(int i=0;i<result.size()-1;i++){
                        navigationItems.add(new EventListItem("item "+i,R.drawable.ic_location,String.valueOf(i),"Event Name "+i,"Creator "+i,new LatLng(i,i),i+":"+i,"This is a description for EVENT "+i));
                    }
                    list.setAdapter(new EventItemAdapter(context, navigationItems));
                }
            }
        };
        //get the events
        //api.v1.getAllEvents(multipleCallback);
    }
}