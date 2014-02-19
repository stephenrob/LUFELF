package uk.co.stephen_robinson.uni.lufelf;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by James on 30/01/2014.
 */
public class CreateEventFragment extends BaseFragment{

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
        showActivitySpinner();

        //get the datetime textedit and set the onlcik listener
        final EditText dateTime=(EditText)rootView.findViewById(R.id.setTimeDate);
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

        //populate places spinner
        ArrayList<String> places = new ArrayList<String>();
        places.add("Current Location");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.spinner_item,places);
        Spinner placeSpinner=(Spinner)rootView.findViewById(R.id.event_place_spinner);
        placeSpinner.setAdapter(adapter);

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
}
