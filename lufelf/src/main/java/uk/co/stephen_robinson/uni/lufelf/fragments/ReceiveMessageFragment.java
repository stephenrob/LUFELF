package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.MessageItem;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Place profile
 */
public class ReceiveMessageFragment extends BaseFragment{

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

        TextView from = (TextView) rootView.findViewById(R.id.message_from_from);
        TextView content = (TextView) rootView.findViewById(R.id.message_from_content);
        ImageView senderImage=(ImageView)rootView.findViewById(R.id.image_friend_message);

        from.setText(args.getString("from"));
        content.setText(args.getString("content"));

        DownloadImage downloadImage=new DownloadImage(senderImage,getActivity(),"http://148.88.32.47/avatars/"+args.getString("id")+".jpg");
        downloadImage.downloadFromServer(isNetworkAvailable());

        Button replyButton = (Button)rootView.findViewById(R.id.message_from_reply_button);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetIndexes();
                fragmentManager.beginTransaction().replace(R.id.container, SendMessageFragment.newInstance(args.getString("id")), "MessageSubView").addToBackStack(null).commit();

            }
        });

        return rootView;
    }
}