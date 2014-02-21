package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.stephen_robinson.uni.lufelf.R;

/**
 * @author James
 * Place profile
 */
public class SendMessageFragment extends BaseFragment{

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

        TextView to=(TextView)rootView.findViewById(R.id.message_to);
        //showActivitySpinner();
        Bundle args = getArguments();
        if(args!=null){
            to.setText(args.getString("id"));
        }


        return rootView;
    }
}