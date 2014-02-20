package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.stephen_robinson.uni.lufelf.R;

/**
 * @author James
 * Place profile
 */
public class SplashFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.splash_screen_layout, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        return rootView;
    }
}