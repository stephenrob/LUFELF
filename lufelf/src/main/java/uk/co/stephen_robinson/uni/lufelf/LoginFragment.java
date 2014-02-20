package uk.co.stephen_robinson.uni.lufelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Enumeration;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.NetworkCallback;

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

            Button loginButton =(Button)rootView.findViewById(R.id.login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    //create the call back for the api
                    NetworkCallback networkCallback= new NetworkCallback() {
                        @Override
                        public void results(Hashtable result) {
                            Enumeration keys = result.keys();
                            while(keys.hasMoreElements())
                                Log.e("Crap",keys.nextElement().toString());
                            if(result==null){
                                Intent swapToApp=new Intent(rootView.getContext(),MainActivity.class);
                                hideActivitySpinner();
                                startActivity(swapToApp);
                            }

                            boolean error=toastMaker.isError(result.get("status_code").toString(),result.get("message").toString());
                            if(error)
                                hideActivitySpinner();
                        }
                    };
                    //get the username and password fields
                    EditText username = (EditText) rootView.findViewById(R.id.login_username_text);
                    EditText password = (EditText) rootView.findViewById(R.id.login_password_text);

                    EditText[] array={username,password};
                    boolean allOk=false;
                    for(int i=0;i<array.length;i++){
                        allOk=ValidationChecker.checkIfEmpty(array[i]);
                        allOk=ValidationChecker.checkSize(array[i],3);
                    }
                    allOk=ValidationChecker.isEmailValid(username);

                    //get the text values of both the username and password
                    //hash the password and attach the network callback
                    if(allOk){
                        api.loginUser(username.getText().toString(),md5(password.getText().toString()),networkCallback);
                        //we will be waiting for the ui to update - begin spinner
                        showActivitySpinner();
                    }
                }
            });
            return rootView;
        }
}
