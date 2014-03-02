package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventListItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.NavDrawerItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Event;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.EventUser;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.CSVGenerator;
import uk.co.stephen_robinson.uni.lufelf.utilities.EventDateComparator;


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

        //get the events listview
        list = (ListView)rootView.findViewById(R.id.events_listview);
        //add events to the listview
        addEvents();

        //set item click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                resetIndexes();
                EventListItem item = (EventListItem) list.getItemAtPosition(i);
                fragmentManager.beginTransaction().replace(R.id.container, EventProfileFragment.newInstance(item), "EventSubView").addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    /**
     * add the events to the view
     */
    public void addEvents(){
        showActivitySpinner();
        Multiple multipleCallback=new Multiple() {
            @Override
            public void results(ArrayList result) {
                ArrayList eventItems=new ArrayList<NavDrawerItem>();
                Message m = (Message)result.get(result.size()-1);
                if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){
                    for(int i=0;i<result.size()-1;i++){
                        Event e=(Event)result.get(i);
                        eventItems.add(new EventListItem(String.valueOf(e.getId()), e.getName(), R.drawable.ic_location, "Creator " + i, new LatLng(i, i), e.getDate(), "This is a description for EVENT " + i, new ArrayList<EventUser>()));

                    }
                    Collections.sort(eventItems,new EventDateComparator());
                    list.setAdapter(new EventItemAdapter(context, checkDate(checkBlackList(eventItems))));
                }
                hideActivitySpinner();
            }
        };
        //get the events
        api.v1.getAllEvents(multipleCallback);
    }
    public ArrayList<EventListItem> checkBlackList(ArrayList<EventListItem> eventListItems){

        CSVGenerator csvGenerator=new CSVGenerator();

        int i=0;

        ArrayList<String> blacklist = csvGenerator.getAll();

        while(i<eventListItems.size()){
            for(int count =0;count<blacklist.size();count++)
                if(eventListItems.get(i).getId().equals(blacklist.get(count)))
                    eventListItems.remove(i);
            i++;
        }
        return eventListItems;
    }
    public ArrayList<EventListItem> checkDate(ArrayList<EventListItem> eventListItems){
        ArrayList<EventListItem> returnItems= new ArrayList<EventListItem>();
        //get current time and date
        Calendar currentDate=Calendar.getInstance();

        for(int i=0;i<eventListItems.size();i++){
            String date1 = eventListItems.get(i).getDateTime().substring(0,10);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try{
                Date date = formatter.parse(date1);
                Date currentDay = formatter.parse(formatter.format(currentDate.getTime()));
                Log.e("comparison result",String.valueOf(date.compareTo(currentDate.getTime())));
                if(date.compareTo(currentDay)>=0){
                    returnItems.add(eventListItems.get(i));
                }
            }catch (Exception e){

            }
        }
        return returnItems;
    }
}