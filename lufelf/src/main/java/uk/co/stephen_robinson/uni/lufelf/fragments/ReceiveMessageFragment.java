package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Place profile
 */
public class ReceiveMessageFragment extends BaseFragment{

    private String username;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReceiveMessageFragment newInstance(MessageItem item) {
        ReceiveMessageFragment f = new ReceiveMessageFragment();
        Bundle args = new Bundle();
        args.putString("from",item.getMessageFrom());
        args.putString("content",item.getMessageContent());
        args.putString("id",item.getId());
        if(item.getMessageTo()!=null)
            args.putString("message_to",item.getMessageTo());

        f.setArguments(args);
        return f;
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

        final Bundle args = getArguments();

        TextView beforeText=(TextView) rootView.findViewById(R.id.message_before_text);
        TextView from = (TextView) rootView.findViewById(R.id.message_from_from);
        TextView content = (TextView) rootView.findViewById(R.id.message_from_content);
        ImageView senderImage=(ImageView)rootView.findViewById(R.id.image_friend_message);

        if(args.getString("message_to")!=null){
            from.setText(args.getString("message_to"));
            beforeText.setText("To:");
        }else{
            from.setText(args.getString("from"));
            beforeText.setText("From:");
        }
        content.setText(args.getString("content"));

        loadUser(args.getString("id"));

        DownloadImage downloadImage=new DownloadImage(senderImage,getActivity(),"http://148.88.32.47/avatars/"+args.getString("id")+".jpg");
        downloadImage.downloadFromServer(isNetworkAvailable());


        return rootView;
    }

    /**
     * get the user name of the user that sent the mssage
     * @param id the id of the user that sent the message
     */
    public void loadUser(String id){
        showActivitySpinner();
        Single sc = new Single() {
            @Override
            public void results(Hashtable result) {
                if(!toastMaker.isError(String.valueOf(result.get(Message.CODE)),(String)result.get(Message.MESSAGE))){


                    final String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                    final String description = result.get("description")==null?"My description...":String.valueOf(result.get("description"));
                    final String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                    final String usernamereturned = result.get("username")==null?"":String.valueOf(result.get("username"));
                    final int id = result.get("user_id")==null?0:Integer.valueOf(String.valueOf(result.get("user_id")));
                    username=usernamereturned;
                }
                hideActivitySpinner();
            }
        };
        api.v1.getUserByID(id,sc);
    }
}