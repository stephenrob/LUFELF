package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by James on 30/01/2014.
 */
public class SettingsFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setContext(rootView.getContext());
        showDialog();
        Log.e("CRAP", "HERE");
        return rootView;
    }
}