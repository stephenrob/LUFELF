package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * @author James
 * Fragment for searching friends
 */
public class SearchFragment extends BaseFragment{
    public static final int placeSearch=0;
    public static final int friendsSearch=1;
    public static final int eventSearch=2;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SearchFragment newInstance(int searchType) {
        SearchFragment fragment=new SearchFragment();
        Bundle args=new Bundle();
        args.putInt("search_type",searchType);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        return rootView;
    }
}
