package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventListItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventNameComparator;
import uk.co.stephen_robinson.uni.lufelf.adapters.NavDrawerItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.PlaceItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Event;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Place;
import uk.co.stephen_robinson.uni.lufelf.utilities.UploadImage;
import uk.co.stephen_robinson.uni.lufelf.utilities.ValidationChecker;

/**
 * @author James
 * Displays the createEvent fragment and all of the methods.
 */
public class CreateEventFragment extends BaseFragment{


    private ArrayList<PlaceItem> places;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    public CreateEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_create_event, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        //get the datetime textedit and set the onlcik listener
        final EditText dateTime=(EditText)rootView.findViewById(R.id.create_event_setTimeDate);
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTime(dateTime);
            }
        });

        //get the image view and set the on click listener
        ImageView imageView=(ImageView)rootView.findViewById(R.id.profile_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCameraDialog();
            }
        });

        final EditText name = (EditText)rootView.findViewById(R.id.create_event_name);

        //populate places spinner
        final Spinner placeSpinner=(Spinner)rootView.findViewById(R.id.create_event_place_spinner);
        loadPlacesIntoSpinner(placeSpinner);

        final Spinner eventType=(Spinner)rootView.findViewById(R.id.create_event_type_spinner);
        //Spinner eventPlace=(EditText)rootView.findViewById(R.id.create_event_name);
        final EditText description= (EditText)rootView.findViewById(R.id.create_event_name);

        Button submit = (Button)rootView.findViewById(R.id.create_event_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText[] editTexts={name,description};


                boolean allOk= ValidationChecker.standardValidationCheck(editTexts);

                //check for weird characters
                if(allOk)
                    allOk=ValidationChecker.noOddCharacters(editTexts[0]);
                else
                    ValidationChecker.noOddCharacters(editTexts[0]);

                if(allOk){
                    Single single= new Single() {
                        @Override
                        public void results(Hashtable result) {

                            //get all of the edittexts
                            boolean error=toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                            if(!error){
                                toastMaker.makeToast(result.get(Message.MESSAGE).toString());
                                uploadImage(name.getText().toString());

                            }
                        }
                    };
                    showActivitySpinner();
                    api.v1.addEvent(name.getText().toString(),dateTime.getText().toString(), EventListItem.convertTypeIntoCompatibleString(eventType.getSelectedItemPosition()),description.getText().toString(),places.get(placeSpinner.getSelectedItemPosition()).getId(),single);
                }
            }
        });




        return rootView;
    }

    /**
     * Presents date and time dialog to the user.
     * @param editText The text field that acts as a button
     */
    public void showDateTime(final EditText editText){

        //get current time and date
        Calendar currentDate=Calendar.getInstance();

        //set current variables
        int currentyear=currentDate.get(Calendar.YEAR);
        int currentMonth=currentDate.get(Calendar.MONTH);
        int currentDay=currentDate.get(Calendar.DAY_OF_MONTH);
        final int hour=currentDate.get(Calendar.HOUR_OF_DAY);
        final int minute=currentDate.get(Calendar.MINUTE);

        //create datepicker and time dialogs dialogs
        DatePickerDialog datePicker=new DatePickerDialog(context, new OnDateSetListener() {
            //set the callback method when the user sets the date
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                //create the date string
                final String dateString=String.valueOf(selectedday)+"/"+String.valueOf(selectedmonth)+"/"+String.valueOf(selectedyear);
                //create time picker dialog
                TimePickerDialog timePicker = new TimePickerDialog(context,new TimePickerDialog.OnTimeSetListener() {
                    //set the functionality of the callback method
                    @Override
                    public void onTimeSet(TimePicker timePicker, int shour, int sminute) {
                        String dateAndtime=dateString+" "+String.valueOf(shour)+":"+String.valueOf(sminute);
                        editText.setText(dateAndtime);
                    }
                },hour,minute,false);
                timePicker.setTitle("Select Time");
                timePicker.show();
            }
        },currentyear, currentMonth, currentDay);
        datePicker.setTitle("Select Date");
        datePicker.show();
    }

    public void loadPlacesIntoSpinner(final Spinner placeSpinner){
        showActivitySpinner();
        Multiple multipleCallback = new Multiple() {
            @Override
            public void results(ArrayList result) {
                Log.e("multiple callback", result.toString());
                Message m = (Message)result.get(result.size()-1);
                if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){
                    places=new ArrayList<PlaceItem>();
                    ArrayList<String> placesStrings = new ArrayList<String>();
                    for(int i=0;i<result.size()-1;i++){
                        Place p =(Place)result.get(i);
                        Log.e("place",p.toString());
                        places.add(new PlaceItem(p.getId(), p.getName(), p.getAddress(), p.getType(), p.getDescription(), p.getUser_id(), p.getImage_url(), 0, 0));
                        placesStrings.add(p.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.spinner_item,placesStrings);
                    placeSpinner.setAdapter(adapter);
                    hideActivitySpinner();
                }
            }
        };
        api.v1.getAllPlaces(multipleCallback);
    }
    public void uploadImage(final String eventName){



        Multiple m = new Multiple() {
            @Override
            public void results(ArrayList result) {
                ArrayList eventItems=new ArrayList<NavDrawerItem>();
                Message m = (Message)result.get(result.size()-1);
                if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){
                    for(int i=0;i<result.size()-1;i++){
                        Event e=(Event)result.get(i);
                        Log.e("EVENT DATES",e.getDate());
                        eventItems.add(new EventListItem(String.valueOf(e.getId()),e.getName(),R.drawable.ic_location,"Creator "+i,new LatLng(i,i),e.getDate(),"This is a description for EVENT "+i));
                    }
                    int index =Collections.binarySearch(eventItems,new EventListItem("",eventName,0,"",new LatLng(0,0),"",""), new EventNameComparator());

                    UploadImage uploadImage = new UploadImage()
                }
                hideActivitySpinner();
                removeFragment();
            }
        };

        api.v1.getAllEvents(m);

    }
}
