package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.api.NetworkCallback;

/**
 * @author James
 * Place profile
 */
public class MessagesFragment extends BaseFragment{

    private ListView list;
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

        //get the events listview
        list = (ListView)rootView.findViewById(R.id.message_listview);
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
        //make arraylist navdraweritems
        ArrayList messageItems=new ArrayList<MessageItem>();

        //add fake items
        for(int i=0;i<30;i++)
            messageItems.add(new MessageItem("Test Subject "+i,"Test message that goes on for the length of 40 characters to test the elipses thing"));

        //set the adapter
        list.setAdapter(new MessageItemAdapter(rootView.getContext(), messageItems));

        Hashtable params = new Hashtable();
        api.getReceivedMessages(params,nc);
    }
}