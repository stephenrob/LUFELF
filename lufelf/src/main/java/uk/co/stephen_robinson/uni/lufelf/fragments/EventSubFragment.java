package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventListItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Event;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * @author James
 * Displays the 'profile' of an event
 */
public class EventSubFragment extends BaseFragment{

    private LatLng finish;
    /**
     * create a fragment with a specific item
     * @param item the event to base the fragment on.
     * @return new instance of the EventSubFragment
     */

    public static EventSubFragment newInstance(EventListItem item) {

        //create fragment
        EventSubFragment fragment=new EventSubFragment();

        //create args
        Bundle args = new Bundle();

        //get EVENT details from item passed in the constructor
        args.putString("id",item.getId());

        //set the args
        fragment.setArguments(args);

        return fragment;
    }

    public EventSubFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        Bundle args=getArguments();
        rootView = inflater.inflate(R.layout.fragment_event_profile, container, false);
        setContext(rootView.getContext());
        showActivitySpinner();

        //get the various textview from the layout
        final TextView eventName=(TextView)rootView.findViewById(R.id.event_name);
        final TextView creator=(TextView)rootView.findViewById(R.id.event_host);
        final TextView location=(TextView)rootView.findViewById(R.id.event_location);
        final TextView datetime=(TextView)rootView.findViewById(R.id.event_date_time);
        final TextView description=(TextView)rootView.findViewById(R.id.event_description_box);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().add(R.id.container, MapViewFragment.newInstance(finish), "NavigateToEvent").addToBackStack(null).commit();
            }
        });

        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", eventName.getText().toString());
                startActivity(intent);
            }
        });

        Multiple m =new Multiple() {
            @Override
            public void results(ArrayList result) {
                Log.e("OUTPUT",result.toString());
                Message message=(Message)result.get(result.size()-1);
                Log.e("MSG",String.valueOf(message.getStatusCode())+" "+message.getMessage());
                if(!toastMaker.isError(String.valueOf(message.getStatusCode()),message.getMessage())){
                    Event event =(Event) result.get(0);
                    Log.e("OUTPUT",event.toString());
                    Log.e("OUTPUT",event.getDate());
                    eventName.setText(event.getName());

                    datetime.setText(event.getDate());
                    description.setText(event.getDescription());
                    finish=new LatLng(54.0470,2.8010);

                    //location.setText(String.valueOf(args.getDouble("lat"))+","+String.valueOf(args.getDouble("long")));
                    /*Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {
                            if(!toastMaker.isError(String.valueOf(result.get(Message.CODE)),String.valueOf(result.get(Message.MESSAGE)))){
                                creator.setText((String) result.get(User.NAME));

                            }

                        }
                    };

                    api.v1.getUserByID(String.valueOf(event.getUser_id()),single);*/
                }
                hideActivitySpinner();
            }
        };

        api.v1.getEvent(46,m);
        //set the text


        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}