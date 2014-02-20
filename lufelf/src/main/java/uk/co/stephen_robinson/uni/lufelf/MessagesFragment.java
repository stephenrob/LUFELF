package uk.co.stephen_robinson.uni.lufelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.api.NetworkCallback;

/**
 * @author James
 * Place profile
 */
public class MessagesFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    public MessagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        loadMessages();

        return rootView;
    }

    public void loadMessages(){
        showActivitySpinner();
        NetworkCallback nc = new NetworkCallback() {
            @Override
            public void results(Hashtable result) {
                hideActivitySpinner();

            }
        };
        Hashtable params = new Hashtable();
        api.getReceivedMessages(params,nc);
    }
}