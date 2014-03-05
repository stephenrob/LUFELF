package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.UserMessage;

/**
 * @author James
 * this fragment displays all messages.
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

        loadMessages();

        return rootView;
    }

    /**
     * loads all messages for the current user
     */
    public void loadMessages(){
        showActivitySpinner();
        Multiple multiple = new Multiple() {
            @Override
            public void results(ArrayList result) {
            Message message =(Message)result.get(result.size()-1);
            UserMessage m;
            ArrayList<MessageItem> messageItems = new ArrayList<MessageItem>();

            if(!toastMaker.isError(String.valueOf(message.getStatusCode()),message.getMessage())){

                for(int i=0;i<result.size()-1;i++){
                    m=(UserMessage)result.get(i);
                    messageItems.add(new MessageItem(m.getFrom(),m.getContent(),String.valueOf(m.getMessage_id()),null));
                }
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        resetIndexes();
                        fragmentManager.beginTransaction().add(R.id.container, ReceiveMessageFragment.newInstance((MessageItem) list.getItemAtPosition(i)), "MessageSubView").addToBackStack(null).commit();
                    }
                });
            }else{
                messageItems.add(MessageItem.getBlankResult());
            }
            //set the adapter
            list.setAdapter(new MessageItemAdapter(rootView.getContext(), messageItems));
            hideActivitySpinner();
            }
        };
        api.v1.getReceivedMessages(multiple);
    }
}