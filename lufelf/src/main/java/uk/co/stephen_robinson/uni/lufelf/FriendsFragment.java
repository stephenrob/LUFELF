package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author James
 * Instantiates a friends fragment
 */
public class FriendsFragment extends BaseFragment{

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
