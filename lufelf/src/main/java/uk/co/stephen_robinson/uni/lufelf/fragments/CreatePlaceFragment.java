package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.api.Network.Network;
import uk.co.stephen_robinson.uni.lufelf.api.NetworkCallback;
import uk.co.stephen_robinson.uni.lufelf.utilities.ValidationChecker;

/**
 * @author James
 * Fragment for Registering
 */
public class CreatePlaceFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CreatePlaceFragment newInstance() {
        CreatePlaceFragment fragment = new CreatePlaceFragment();

        return fragment;
    }

    public CreatePlaceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_create_place, container, false);
        setContext(rootView.getContext());

        //get the submit button
        Button submit = (Button)rootView.findViewById(R.id.create_place_submit);

        //set the on click listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get all of the edittexts
                EditText[] editTexts={(EditText)rootView.findViewById(R.id.create_place_name),
                        (EditText)rootView.findViewById(R.id.create_place_address),
                        (EditText)rootView.findViewById(R.id.create_place_description),
                        (EditText)rootView.findViewById(R.id.register_password)};

                boolean allOk= ValidationChecker.standardValidationCheck(editTexts);

                //check for weird characters
                if(allOk)
                    allOk=ValidationChecker.noOddCharacters(editTexts[0]);
                else
                    ValidationChecker.noOddCharacters(editTexts[0]);

                //validate email address
                if(allOk)
                    allOk=ValidationChecker.isEmailValid(editTexts[2]);
                else
                    ValidationChecker.isEmailValid(editTexts[2]);

                //validate passwords
                if(allOk)
                    allOk=ValidationChecker.fieldsHaveSameValue(editTexts[3], editTexts[4]);
                else
                    ValidationChecker.fieldsHaveSameValue(editTexts[3], editTexts[4]);

                if(allOk){
                    toastMaker.makeToast("All ok");
                    //if there are no errors in user input
                    //create a new network call back to handle the returned data.
                    NetworkCallback nc= new NetworkCallback() {
                        @Override
                        public void results(Hashtable result) {
                            hideActivitySpinner();
                            boolean error = toastMaker.isError(result.get(Network.STATUS_CODE).toString(),result.get(Network.MESSAGE).toString());
                            if(!error){

                            }
                        }
                    };

                    //tell the user the app is working
                    //showActivitySpinner();

                    //create a new userdetails class for the api


                    //call api
                    //api.registerUser(userDetails,nc);
                }
            }
        });
        //showActivitySpinner();


        return rootView;
    }
    public void showDate(final EditText editText){

        //get current time and date
        Calendar currentDate=Calendar.getInstance();

        //set current variables
        int currentyear=currentDate.get(Calendar.YEAR);
        int currentMonth=currentDate.get(Calendar.MONTH);
        int currentDay=currentDate.get(Calendar.DAY_OF_MONTH);


        //create datepicker and time dialogs dialogs
        DatePickerDialog datePicker=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            //set the callback method when the user sets the date
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                //create the date string
                final String dateString=String.valueOf(selectedday)+"/"+String.valueOf(selectedmonth)+"/"+String.valueOf(selectedyear);
                editText.setText(dateString);
            }
        },currentyear, currentMonth, currentDay);
        datePicker.setTitle("Select Date");
        datePicker.show();
    }
}
