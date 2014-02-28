package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.FriendItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Friend;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * @author James
 * Place profile
 */
public class SendMessageFragment extends BaseFragment{
    private Spinner to;
    private ArrayList<Integer> friendId;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SendMessageFragment newInstance(String userid) {
        SendMessageFragment f =new SendMessageFragment();
        if(userid==null)
            return f;
        Bundle args = new Bundle();
        args.putString("id",userid);
        f.setArguments(args);
        return f;
    }

    public SendMessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_message_send, container, false);
        setContext(rootView.getContext());

        to=(Spinner)rootView.findViewById(R.id.message_to);

        final EditText messageContent=(EditText)rootView.findViewById(R.id.message_send_content);

        Button send = (Button)rootView.findViewById(R.id.message_send_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(messageContent.getText().toString(),to.getSelectedItemPosition());
            }
        });

        //showActivitySpinner();
        Bundle args = getArguments();

        loadFriends();

        return rootView;
    }
    public void loadFriends(){
        showActivitySpinner();
        Multiple mc = new Multiple() {


            @Override
            public void results(ArrayList result) {
                Message m =(Message)result.get(result.size()-1);
                ArrayList<String> friendNames = new ArrayList<String>();
                friendId=new ArrayList<Integer>();
                if(!toastMaker.isError(String.valueOf(m.getStatusCode()),m.getMessage())){

                    for(int i=0;i<result.size()-1;i++){
                        Friend f =(Friend)result.get(i);
                        friendNames.add(f.getUsername()+" ("+f.getName()+") ");
                        friendId.add(f.getUser_id());
                    }
                }else{
                    friendNames.add(FriendItem.getBlankResult().getName());
                    friendId.add(0);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.spinner_item,friendNames);
                to.setAdapter(adapter);


                hideActivitySpinner();
            }
        };

        api.v1.getFriendsList(mc);
    }
    public void sendMessage(String message,int pos){
        showActivitySpinner();
        Single single = new Single() {
            @Override
            public void results(Hashtable result) {
                if(!toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString())){
                    toastMaker.makeToast(result.get(Message.MESSAGE).toString());
                    hideActivitySpinner();
                    removeFragment();
                }
                hideActivitySpinner();
            }
        };

        api.v1.sendMessage(friendId.get(pos),message,single);
    }
}