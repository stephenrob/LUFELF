package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Enumeration;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.activities.LoginActivity;
import uk.co.stephen_robinson.uni.lufelf.activities.MainActivity;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.CustomMessages;
import uk.co.stephen_robinson.uni.lufelf.utilities.DialogCallback;
import uk.co.stephen_robinson.uni.lufelf.utilities.ValidationChecker;

/**
 * @author James
 * Fragment for log in
 */
public class LoginFragment extends BaseFragment{

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static LoginFragment newInstance() {
            LoginFragment fragment = new LoginFragment();

            return fragment;
        }

        public LoginFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //init
            setFragmentManager(getFragmentManager());
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            setContext(rootView.getContext());

            //get the textview for the register text
            TextView registerText=(TextView)rootView.findViewById(R.id.login_register_text);

            //when register is clicked swap to the register fragment.
            registerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  LoginActivity activity = (LoginActivity)getActivity();
                  activity.setLayout(R.layout.fragment_register);
                }
            });

            //get the textview for the register text
            TextView debugText=(TextView)rootView.findViewById(R.id.DEBUG_TEST);

            //when register is clicked swap to the register fragment.
            debugText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showActivitySpinner();
                    swapToMain(1);
                }
            });

            //get the text view for use as guest
            TextView guestText=(TextView)rootView.findViewById(R.id.login_use_as_guest);

            //when use as guest is clicked swap to main
            guestText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogCallback dialogCallback = new DialogCallback() {
                        @Override
                        public void messageComplete(int result) {
                            switch(result){
                                case 1:
                                    showActivitySpinner();
                                    swapToMain(0);
                                    break;
                                case -1:
                                    LoginActivity loginActivity =(LoginActivity) getActivity();
                                    loginActivity.setLayout(R.layout.fragment_register);
                                    break;
                            }
                        }
                    };
                    CustomMessages.showMessage("Use as Guest","As a guest you will have limited functionality","Use as guest?","Register.",context,dialogCallback);

                }
            });
            //get the login button and set the on click listener
            Button loginButton =(Button)rootView.findViewById(R.id.login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //get the username and password fields
                    EditText username = (EditText) rootView.findViewById(R.id.login_username_text);
                    EditText password = (EditText) rootView.findViewById(R.id.login_password_text);

                    EditText[] editTexts ={username,password};
                    boolean allOk= ValidationChecker.standardValidationCheck(editTexts);
                    //validate email address
                    if(allOk)
                        allOk=ValidationChecker.isEmailValid(editTexts[0]);
                    else
                        ValidationChecker.isEmailValid(editTexts[0]);
                    //get the text values of both the username and password
                    //hash the password and attach the network callback
                    if(allOk){
                        //create the call back for the api
                        Single networkCallback= new Single() {
                            @Override
                            public void results(Hashtable result) {
                                Enumeration keys = result.keys();
                                while(keys.hasMoreElements()){
                                    Log.e("CRAP",keys.nextElement().toString());
                                }
                                boolean error=toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                                if(error)
                                    hideActivitySpinner();
                                else if(!error){
                                    swapToMain(1);
                                }
                            }
                        };

                        api.v1.loginUser(username.getText().toString(), md5(password.getText().toString()), networkCallback);
                        //we will be waiting for the ui to update - begin spinner
                        showActivitySpinner();
                    }
                }
            });
            return rootView;
        }

    /**
     * swaps to the app
     * @param priority the user level. 0 for guest, 1 for student
     */
    public void swapToMain(int priority){
        Intent swapToApp=new Intent(rootView.getContext(),MainActivity.class);
        Bundle args = new Bundle();
        args.putInt("priority",priority);
        swapToApp.putExtras(args);
        hideActivitySpinner();
        startActivity(swapToApp);
    }
}
