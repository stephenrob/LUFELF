package uk.co.stephen_robinson.uni.lufelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.NetworkCallback;
import uk.co.stephen_robinson.uni.lufelf.api.UserDetails;

/**
 * @author James
 * Fragment for Registering
 */
public class RegisterFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        setContext(rootView.getContext());

        //get the submit button
        Button submit = (Button)rootView.findViewById(R.id.register_confim_button);

        //set the on click listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CRAP","CLICKED");
                //get all of the edittexts
                EditText[] editTexts={(EditText)rootView.findViewById(R.id.register_name),
                        (EditText)rootView.findViewById(R.id.register_lib_no),
                        (EditText)rootView.findViewById(R.id.register_email_add),
                        (EditText)rootView.findViewById(R.id.register_password),
                        (EditText)rootView.findViewById(R.id.register_confirm_password),
                        (EditText)rootView.findViewById(R.id.register_dob)};

                boolean allOk=ValidationChecker.standardValidationCheck(editTexts);

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
                    //if there are no errors in user input
                    //create a new network call back to handle the returned data.
                    NetworkCallback nc= new NetworkCallback() {
                        @Override
                        public void results(Hashtable result) {
                            hideActivitySpinner();
                            boolean error = toastMaker.isError(result.get("status_code").toString(),result.get("message").toString());
                            if(!error){
                                Intent swapToApp=new Intent(rootView.getContext(),MainActivity.class);
                                startActivity(swapToApp);
                            }
                        }
                    };

                    //tell the user the app is working
                    showActivitySpinner();

                    //create a new userdetails class for the api
                    UserDetails userDetails=new UserDetails();
                    userDetails.name=editTexts[0].getText().toString();
                    userDetails.lib_no=editTexts[1].getText().toString();
                    userDetails.username=editTexts[2].getText().toString();
                    userDetails.password=editTexts[3].getText().toString();
                    userDetails.dob=editTexts[5].getText().toString();

                    //call api
                    api.registerUser(userDetails,nc);
                }
            }
        });
        //showActivitySpinner();


        return rootView;
    }
}
