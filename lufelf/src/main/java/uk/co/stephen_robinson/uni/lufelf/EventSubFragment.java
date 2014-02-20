package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author James
 * Displays the 'profile' of an event
 */
public class EventSubFragment extends BaseFragment{

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
        args.putString("eventName",item.getEventName());
        args.putString("creator",item.getCreator());
        args.putString("dateTime",item.getDateTime());
        args.putString("description",item.getDescription());
        args.putDouble("lat",item.getLocation().latitude);
        args.putDouble("lat",item.getLocation().longitude);

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
        //showActivitySpinner();

        //get the various textview from the layout
        TextView eventName=(TextView)rootView.findViewById(R.id.event_name);
        TextView creator=(TextView)rootView.findViewById(R.id.event_host);
        TextView location=(TextView)rootView.findViewById(R.id.event_location);
        TextView datetime=(TextView)rootView.findViewById(R.id.event_date_time);
        TextView description=(TextView)rootView.findViewById(R.id.event_description_box);

        //set the text
        eventName.setText(args.getString("eventName"));
        creator.setText("By: "+args.getString("creator"));
        datetime.setText(args.getString("dateTime"));
        description.setText(args.getString("description"));
        location.setText(String.valueOf(args.getDouble("lat"))+","+String.valueOf(args.getDouble("long")));

        return rootView;
    }
}