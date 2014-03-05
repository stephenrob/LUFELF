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
import uk.co.stephen_robinson.uni.lufelf.adapters.SentMessageItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.UserMessage;

/**
 * @author James
 * sent messages fragment
 */
public class SentMessagesFragment extends BaseFragment{

    private ListView list;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SentMessagesFragment newInstance() {
        return new SentMessagesFragment();
    }

    public SentMessagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_sent_messages, container, false);
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

    /**
     * load the users sent messages
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
                        messageItems.add(new MessageItem(m.getFrom(),m.getContent(),String.valueOf(m.getMessage_id()),m.getTo()));
                    }
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            fragmentManager.beginTransaction().add(R.id.container, ReceiveMessageFragment.newInstance((MessageItem) list.getItemAtPosition(i)), "MessageSubView").addToBackStack(null).commit();
                        }
                    });
                }else{
                    messageItems.add(MessageItem.getBlankResult());
                }
                //set the adapter
                list.setAdapter(new SentMessageItemAdapter(rootView.getContext(), messageItems));
                hideActivitySpinner();
            }
        };
        api.v1.getSentMessages(multiple);
    }
}