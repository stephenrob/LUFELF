package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.EventUser;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.CSVGenerator;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Displays the 'profile' of an event
 */
public class EventProfileFragment extends BaseFragment{
    private UserItem owner;
    private LatLng finish;
    /**
     * create a fragment with a specific item
     * @param item the event to base the fragment on.
     * @return new instance of the EventProfileFragment
     */

    public static EventProfileFragment newInstance(EventListItem item) {

        //create fragment
        EventProfileFragment fragment=new EventProfileFragment();

        //create args
        Bundle args = new Bundle();

        //get EVENT details from item passed in the constructor
        args.putString("id",item.getId());

        //set the args
        fragment.setArguments(args);

        return fragment;
    }

    public EventProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        final Bundle args=getArguments();
        rootView = inflater.inflate(R.layout.fragment_event_profile, container, false);
        setContext(rootView.getContext());

        //get the various textview from the layout
        final TextView eventName=(TextView)rootView.findViewById(R.id.event_name);
        final TextView creator=(TextView)rootView.findViewById(R.id.event_host);
        final TextView address=(TextView)rootView.findViewById(R.id.event_address);
        final TextView location=(TextView)rootView.findViewById(R.id.event_location);
        final TextView datetime=(TextView)rootView.findViewById(R.id.event_date_time);
        final TextView description=(TextView)rootView.findViewById(R.id.event_description_box);
        final TextView attendees_list =(TextView)rootView.findViewById(R.id.event_attendees_text);
        final ImageView event_pic =(ImageView)rootView.findViewById(R.id.image_event);
        final ImageView remove_event=(ImageView)rootView.findViewById(R.id.remove_event);

        remove_event.setVisibility(View.INVISIBLE);

        loadDetails(eventName,datetime,description,address,creator,attendees_list,event_pic,remove_event,args);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapToNavigateTo(finish);
            }
        });

        final LinearLayout accept = (LinearLayout)rootView.findViewById(R.id.event_profile_accept);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActivitySpinner();
                Single sc = new Single() {
                    @Override
                    public void results(Hashtable result) {
                        toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                        toastMaker.makeToast(result.get(Message.MESSAGE).toString());
                        hideActivitySpinner();
                        loadDetails(eventName,datetime,description,address,creator,attendees_list,event_pic,remove_event,args);
                    }
                };
                api.v1.attendEvent(Integer.valueOf(args.getString("id")),sc);
            }
        });

        final LinearLayout reject = (LinearLayout)rootView.findViewById(R.id.event_profile_reject);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("REJECTING","REJETE");
                handleReject(args.getString("id"));
            }
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


        //set the text


        return rootView;
    }

    public void loadDetails(final TextView eventName,final TextView datetime,final TextView description,final TextView address,final TextView creator,final TextView attendees_list,final ImageView event_pic,final ImageView remove_event,final Bundle args){
        showActivitySpinner();
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
                    ArrayList<EventUser> attendees = event.getAttendees();

                    for(int i=0;i<attendees.size();i++){
                        if(attendees.size()==0)
                            attendees_list.setText("None.");
                        if(i==0)
                            attendees_list.setText(attendees_list.getText().toString() + attendees.get(i).getName());
                        else
                            attendees_list.setText(attendees_list.getText().toString()+", "+attendees.get(i).getName());
                    }
                    //get the lat long of the event for navigate to
                    String[] location=event.getLocation().split(",");
                    finish=new LatLng(Double.valueOf(location[0]),Double.valueOf(location[1]));

                    //get the creator id.
                    final String eventCreator = event.getOwner().getUsername();
                    Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {

                            if(!toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString())){

                                //get all the returned fields
                                String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                                String description = result.get("description")==null?"":String.valueOf(result.get("description"));
                                String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                                String username = result.get("username")==null?"":String.valueOf(result.get("username"));
                                int id = result.get("user_id")==null?0:Integer.valueOf(String.valueOf(result.get("user_id")));

                                //set this fragments owner object
                                owner=new UserItem(name, description, libno, username, id,false);


                                if(owner.getId()==Integer.valueOf(api.v1.currentUserId())){
                                    remove_event.setVisibility(View.VISIBLE);
                                    remove_event.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Single sc = new Single() {
                                                @Override
                                                public void results(Hashtable result) {
                                                    if(!toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString())){
                                                        toastMaker.makeToast(result.get(Message.MESSAGE).toString());
                                                        removeFragment();
                                                    }
                                                }
                                            };
                                            api.v1.deleteEvent(Integer.valueOf(args.getString("id")),sc);
                                        }
                                    });
                                }else{
                                    ViewGroup parent = (ViewGroup)remove_event.getParent();
                                    parent.removeView(remove_event);
                                }

                                //set the creator text
                                creator.setText("Created By: "+owner.getName());

                                //add on click listener for creator text
                                creator.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //swap fragments to the user profile when clicked.
                                        fragmentManager.beginTransaction().add(R.id.container, FriendProfileFragment.newInstance(owner), "UserProfileSubView").addToBackStack(null).commit();
                                    }
                                });

                                DownloadImage downloadImage = new DownloadImage(event_pic,getActivity(),DownloadImage.EVENT,Integer.valueOf(args.getString("id")));
                                downloadImage.downloadFromServer(isNetworkAvailable());
                            }
                            hideActivitySpinner();
                        }
                    };
                    //call to the api to get user details
                    api.v1.getUserByUsername(eventCreator,single);
                }

            }
        };

        api.v1.getEvent(Integer.valueOf(args.getString("id")),m);
    }

    public void handleReject(String id){
        CSVGenerator csvGenerator = new CSVGenerator();
        csvGenerator.append(id);
        ArrayList<String> ids = csvGenerator.getAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}