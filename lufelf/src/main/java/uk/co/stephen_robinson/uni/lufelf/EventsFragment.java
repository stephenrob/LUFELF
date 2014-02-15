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

/**
 * Created by James on 30/01/2014.
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

        setFragmentManager(getFragmentManager());
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        list = (ListView)rootView.findViewById(R.id.events_listview);


        addEvents(inflater, container, rootView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                resetIndexes();
                EventListItem item=(EventListItem)list.getItemAtPosition(i);
                Log.e("CRAP","TEXT ="+item.getId());
                fragmentManager.beginTransaction().replace(R.id.container, EventSubFragment.newInstance(item),"EventSubView").addToBackStack(null).commit();
            }
        });
        Log.e("CRAP", "HERE");
        return rootView;
    }
    public void addEvents(LayoutInflater inflater, ViewGroup container,View rootView){
        ArrayList navigationItems=new ArrayList<NavDrawerItem>();
        for(int i=0;i<30;i++)
            navigationItems.add(new EventListItem("item "+i,R.drawable.ic_location,String.valueOf(i),"Event Name "+i,"Creator "+i,new LatLng(i,i),i+":"+i,"This is a description for event "+i));


        list.setAdapter(new EventItemAdapter(rootView.getContext(), navigationItems));
    }
}