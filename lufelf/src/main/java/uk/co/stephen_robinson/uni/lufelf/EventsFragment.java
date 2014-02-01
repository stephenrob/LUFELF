package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by James on 30/01/2014.
 */
public class EventsFragment extends BaseFragment{

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
        addEvents(inflater,container,rootView);
        Log.e("CRAP", "HERE");
        return rootView;
    }
    public void addEvents(LayoutInflater inflater, ViewGroup container,View rootView){
        ArrayList navigationItems=new ArrayList<NavDrawerItem>();
        for(int i=0;i<30;i++)
            navigationItems.add(new NavDrawerItem("item "+i,R.drawable.ic_location));

        ListView list = (ListView)rootView.findViewById(R.id.events_listview);

        list.setAdapter(new BaseItemAdapter(rootView.getContext(), navigationItems));
    }
}