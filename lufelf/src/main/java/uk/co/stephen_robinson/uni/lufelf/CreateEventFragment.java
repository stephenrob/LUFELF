package uk.co.stephen_robinson.uni.lufelf;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

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

        setFragmentManager(getFragmentManager());

        View rootView = inflater.inflate(R.layout.fragment_create_event, container, false);
        setContext(rootView.getContext());

        final EditText dateTime=(EditText)rootView.findViewById(R.id.setTimeDate);
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTime(dateTime);
            }
        });

        return rootView;
    }
    public void showDateTime(final EditText editText){

        Calendar currentDate=Calendar.getInstance();

        int currentyear=currentDate.get(Calendar.YEAR);
        int currentMonth=currentDate.get(Calendar.MONTH);
        int currentDay=currentDate.get(Calendar.DAY_OF_MONTH);

        final int hour=currentDate.get(Calendar.HOUR_OF_DAY);
        final int minute=currentDate.get(Calendar.MINUTE);

        DatePickerDialog datePicker=new DatePickerDialog(context, new OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                final String dateString=String.valueOf(selectedday)+"/"+String.valueOf(selectedmonth)+"/"+String.valueOf(selectedyear);
                TimePickerDialog timePicker = new TimePickerDialog(context,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int shour, int sminute) {
                        String dateAndtime=dateString+" "+String.valueOf(shour)+":"+String.valueOf(sminute);
                        editText.setText(dateAndtime);
                    }
                },hour,minute,false);
                timePicker.setTitle("Select time");
                timePicker.show();
            }
        },currentyear, currentMonth, currentDay);
        datePicker.setTitle("Select date");
        datePicker.show();
    }
}
