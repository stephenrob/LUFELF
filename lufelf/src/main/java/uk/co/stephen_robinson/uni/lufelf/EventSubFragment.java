package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by James on 30/01/2014.
 */
public class EventSubFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EventSubFragment newInstance(EventListItem item) {
        EventSubFragment fragment=new EventSubFragment();

        Bundle args = new Bundle();

        args.putString("eventName",item.getEventName());
        args.putString("creator",item.getCreator());
        args.putString("dateTime",item.getDateTime());
        args.putString("description",item.getDescription());

        args.putDouble("lat",item.getLocation().latitude);
        args.putDouble("lat",item.getLocation().longitude);

        fragment.setArguments(args);
        return fragment;
    }

    public EventSubFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setFragmentManager(getFragmentManager());
        Bundle args=getArguments();

        View rootView = inflater.inflate(R.layout.fragment_event_profile, container, false);

        TextView eventName=(TextView)rootView.findViewById(R.id.event_name);
        TextView creator=(TextView)rootView.findViewById(R.id.event_host);
        TextView location=(TextView)rootView.findViewById(R.id.event_location);
        TextView datetime=(TextView)rootView.findViewById(R.id.event_date_time);
        TextView description=(TextView)rootView.findViewById(R.id.event_description_box);

        eventName.setText(args.getString("eventName"));
        creator.setText("By: "+args.getString("creator"));
        datetime.setText("Date/Time: \n"+args.getString("dateTime"));
        description.setText(args.getString("description"));
        location.setText("Location: \n"+String.valueOf(args.getDouble("lat"))+","+String.valueOf(args.getDouble("long")));

        Log.e("CRAP", "HERE");
        return rootView;
    }
}