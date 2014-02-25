package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventListItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Event;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * @author James
 * Displays the 'profile' of an event
 */
public class EventSubFragment extends BaseFragment{
    private UserItem owner;
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
        final TextView address=(TextView)rootView.findViewById(R.id.event_address);
        final TextView location=(TextView)rootView.findViewById(R.id.event_location);
        final TextView datetime=(TextView)rootView.findViewById(R.id.event_date_time);
        final TextView description=(TextView)rootView.findViewById(R.id.event_description_box);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapToNavigateTo(finish);            }
        });

        //set the intent for when the date time is clicked
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the calendar
                Calendar cal = Calendar.getInstance();

                //set the date formatter
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                //set the fallback for if the next setting of dates does not work
                Date startDate=new Date(cal.getTimeInMillis());

                //try to set the date from the original formatter (some people haven't formatted the date correctly)
                try{
                    startDate=formatter.parse(datetime.getText().toString());
                }catch (Exception e){
                    //if the first attempt doesn't work
                    //adapt to the people who can;t read the document
                    formatter=new SimpleDateFormat("dd/MM/yyyyHH:mm");
                    try{
                        startDate=formatter.parse(datetime.getText().toString());
                    }catch (Exception f){
                        Log.e("inner date format exception",Log.getStackTraceString(f));
                    }
                    Log.e("date format exception",Log.getStackTraceString(e));
                }

                //set the intent
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime",startDate.getTime());
                intent.putExtra("allDay", true);
                intent.putExtra("endTime", startDate.getTime()+60*60*1000);
                intent.putExtra("title", eventName.getText().toString());
                startActivity(intent);
                //calendar event is added!
            }
        });

        Multiple m =new Multiple() {
            @Override
            public void results(ArrayList result) {
                Log.e("OUTPUT",result.toString());
                Message message=(Message)result.get(result.size()-1);
                Log.e("MSG",String.valueOf(message.getStatusCode())+" "+message.getMessage());
                if(!toastMaker.isError(String.valueOf(message.getStatusCode()),message.getMessage())){
                    //get the event obkect
                    Event event =(Event) result.get(0);

                    //set the fields of the view
                    eventName.setText(event.getName());
                    datetime.setText(event.getDate());
                    description.setText(event.getDescription());
                    address.setText(event.getLocation_address());

                    //get the lat long of the event for navigate to
                    String[] location=event.getLocation().split(",");
                    finish=new LatLng(Double.valueOf(location[0]),Double.valueOf(location[1]));

                    //get the creator id.
                    final int eventCreator = event.getUser_id();
                    Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {

                            if(!toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString())){

                                //get all the returned fields
                                String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                                String description = result.get("description")==null?"":String.valueOf(result.get("description"));
                                String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                                String username = result.get("username")==null?"":String.valueOf(result.get("username"));
                                int id = result.get("id")==null?0:Integer.valueOf(String.valueOf(result.get("name")));

                                //set this fragments owner object
                                owner=new UserItem(name, description, libno, username, id);

                                //set the creator text
                                creator.setText("Created By: "+owner.getName());

                                //add on click listener for creator text
                                creator.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //swap fragments to the user profile when clicked.
                                        fragmentManager.beginTransaction().add(R.id.container, FriendsSubFragment.newInstance(owner), "UserProfileSubView").addToBackStack(null).commit();
                                    }
                                });
                            }
                            hideActivitySpinner();
                        }
                    };
                    //call to the api to get user details
                    api.v1.getUserByID(String.valueOf(eventCreator),single);
                }

            }
        };

        api.v1.getEvent(Integer.valueOf(args.getString("id")),m);
        //set the text


        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}