package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;

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


        //get the new message icon
        ImageView icon = (ImageView)rootView.findViewById(R.id.message_new_message_icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetIndexes();
                fragmentManager.beginTransaction().replace(R.id.container, SendMessageFragment.newInstance(null), "SendMessage").addToBackStack(null).commit();
            }
        });

        //get the events listview
        list = (ListView)rootView.findViewById(R.id.message_listview);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                resetIndexes();
                MessageItem item = (MessageItem) list.getItemAtPosition(i);
                fragmentManager.beginTransaction().replace(R.id.container, ReceiveMessageFragment.newInstance(item), "ViewMessage").addToBackStack(null).commit();

            }
        });
        loadMessages();

        return rootView;
    }

    public void loadMessages(){
        //showActivitySpinner();
        Single nc = new Single() {
            @Override
            public void results(Hashtable result) {
                hideActivitySpinner();

            }
        };
        //make arraylist navdraweritems
        ArrayList messageItems=new ArrayList<MessageItem>();

        //add fake items
        for(int i=0;i<30;i++)
            messageItems.add(new MessageItem("Test From "+i,"Test message that goes on for the length of 40 characters to test the elipses thing","215"));

        //set the adapter
        list.setAdapter(new MessageItemAdapter(rootView.getContext(), messageItems));

        Hashtable params = new Hashtable();
        //api.getReceivedMessages(params,nc);
    }
}