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
public class ReceiveMessageFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReceiveMessageFragment newInstance() {
        return new ReceiveMessageFragment();
    }

    public ReceiveMessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_message_from, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        return rootView;
    }
}