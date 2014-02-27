package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Creates a fragment of a friends profile.
 */
public class FriendProfileFragment extends BaseFragment{
    private int id;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FriendProfileFragment newInstance(UserItem user) {
        FriendProfileFragment f = new FriendProfileFragment();
        Bundle args = new Bundle();
        args.putString("name",user.getName());
        args.putString("username",user.getEmailAdd());
        args.putString("lib_no",user.getLibraryNo());
        args.putInt("user_id", user.getId());
        args.putString("description", user.getDescription());
        args.putBoolean("friend", user.isFriend());
        f.setArguments(args);
        return f;
    }

    public FriendProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_friend_profile, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        final Bundle args = getArguments();

        TextView name = (TextView)rootView.findViewById(R.id.friend_name);
        TextView username = (TextView)rootView.findViewById(R.id.friend_email);
        TextView description = (TextView)rootView.findViewById(R.id.friend_profile_description);

        name.setText(args.getString("name"));
        username.setText("("+args.getString("username")+")");
        description.setText(args.getString("description"));

        id=args.getInt("user_id");

        ImageView imageView=(ImageView)rootView.findViewById(R.id.image_friend);
        DownloadImage downloadImage = new DownloadImage(imageView,getActivity(),DownloadImage.AVATAR,id);

        downloadImage.downloadFromServer();
        Button addFriend = (Button)rootView.findViewById(R.id.add_friend_button);
        if(!args.getBoolean("friend")){
            addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showActivitySpinner();
                    Single single =new Single() {
                        @Override
                        public void results(Hashtable result) {
                            boolean error=toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                            if(!error)
                                toastMaker.makeToast((String)result.get(Message.MESSAGE));
                            hideActivitySpinner();
                        }
                    };

                        api.v1.addFriend(id,single);
                }
            });
        }else{
            addFriend.setText("Remove Friend");
            addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showActivitySpinner();
                    Single single =new Single() {
                        @Override
                        public void results(Hashtable result) {
                            boolean error=toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                            if(!error)
                                toastMaker.makeToast((String)result.get(Message.MESSAGE));
                            hideActivitySpinner();
                        }
                    };

                    api.v1.deleteFriend(id, single);
                }
            });
        }
        return rootView;
    }
}