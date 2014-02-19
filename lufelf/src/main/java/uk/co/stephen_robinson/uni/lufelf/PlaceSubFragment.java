package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * @author James
 * Place profile
 */
public class PlaceSubFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceSubFragment newInstance() {
        return new PlaceSubFragment();
    }

    public PlaceSubFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_place_profile, container, false);
        setContext(rootView.getContext());
        showActivitySpinner();

        return rootView;
    }
}
