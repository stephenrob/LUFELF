package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by James on 30/01/2014.
 */
public class FriendsFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        setContext(rootView.getContext());
        showActivitySpinner();

        return rootView;
    }
}
