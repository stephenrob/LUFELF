package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by James on 31/01/2014.
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
            setFragmentManager(getFragmentManager());
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            setContext(rootView.getContext());
            Log.e("CRAP", "HERE");
            return rootView;
        }
}
