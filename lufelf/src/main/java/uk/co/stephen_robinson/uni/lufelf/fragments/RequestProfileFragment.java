package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.FriendItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Creates a fragment of a friends profile.
 */
public class RequestProfileFragment extends BaseFragment{
    private int id;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RequestProfileFragment newInstance(FriendItem friendItem) {
        RequestProfileFragment f = new RequestProfileFragment();
        Bundle args = new Bundle();

        args.putInt("user_id",friendItem.getUser_id());
        args.putString("name", friendItem.getName());
        args.putInt("request_id", friendItem.getRequest_id());
        args.putInt("friend_id", friendItem.getFriend_id());
        if(friendItem.getFriend_id()!=0){

            args.putString("username",friendItem.getUsername());
            args.putInt("loc_status", friendItem.getLocation_status());
            args.putDouble("lat", friendItem.getLattitude());
            args.putDouble("long", friendItem.getLongitude());

            args.putInt("friend_id",friendItem.getFriend_id());
        }
        f.setArguments(args);
        return f;
    }

    public RequestProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_friend_profile_request, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        final Bundle args = getArguments();

        TextView name = (TextView)rootView.findViewById(R.id.friend_name);
        TextView username = (TextView)rootView.findViewById(R.id.friend_email);
        TextView description = (TextView)rootView.findViewById(R.id.friend_profile_description);
        LinearLayout navigateToLayout = (LinearLayout)rootView.findViewById(R.id.navigate_to_friend);

        name.setText(args.getString("name"));
        if(args.getInt("friend_id")!=0){
            username.setText("("+args.getString("username")+")");
        }
        id=args.getInt("user_id");

        loadUser(username,description);

        if(args.getInt("loc_status")==1&&args.getDouble("lat")!=0||args.getDouble("long")!=0){
            navigateToLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swapToNavigateTo(new LatLng(args.getDouble("lat"),args.getDouble("long")));
                }
            });
        }else{
            navigateToLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastMaker.makeToast(args.getString("name") + " is not sharing their location right now!");
                }
            });
        }


        ImageView imageView=(ImageView)rootView.findViewById(R.id.image_friend);
        DownloadImage downloadImage = new DownloadImage(imageView,getActivity(),DownloadImage.AVATAR,id);

        downloadImage.downloadFromServer();
        Button confirmFriend = (Button)rootView.findViewById(R.id.add_friend_button);
        Button remove = (Button)rootView.findViewById(R.id.reject_friend_button);

        if(args.getInt("friend_id")>0){
            ViewGroup view = (ViewGroup) confirmFriend.getParent();
            view.removeView(confirmFriend);
            remove.setText("Remove Friend");
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showActivitySpinner();
                    Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {
                            boolean error = toastMaker.isError(result.get(Message.CODE).toString(), result.get(Message.MESSAGE).toString());
                            if (!error)
                                toastMaker.makeToast((String) result.get(Message.MESSAGE));
                            hideActivitySpinner();
                        }
                    };
                    api.v1.deleteFriend(args.getInt("user_id"), single);
                }
            });
        }else{
            confirmFriend.setText("Accept");
            remove.setText("Reject");
            confirmFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showActivitySpinner();
                    Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {
                            boolean error = toastMaker.isError(result.get(Message.CODE).toString(), result.get(Message.MESSAGE).toString());
                            if (!error)
                                toastMaker.makeToast((String) result.get(Message.MESSAGE));
                            hideActivitySpinner();
                        }
                    };
                    api.v1.acceptFriendRequest(args.getInt("request_id"), args.getInt("user_id"), single);
                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showActivitySpinner();
                    Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {
                            boolean error = toastMaker.isError(result.get(Message.CODE).toString(), result.get(Message.MESSAGE).toString());
                            if (!error)
                                toastMaker.makeToast((String) result.get(Message.MESSAGE));
                            hideActivitySpinner();
                        }
                    };
                    api.v1.hideFriendRequest(args.getInt("request_id"), args.getInt("user_id"), single);
                }
            });
        }
        return rootView;
    }
    public void loadUser(final TextView usernameView,final TextView descriptionView){
        Bundle args = getArguments();
        showActivitySpinner(); 
        Single single = new Single() {
            @Override
            public void results(Hashtable result) {
                if(!toastMaker.isError(String.valueOf(result.get(Message.CODE)),(String)result.get(Message.MESSAGE))){

                    final String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                    final String description = result.get("description").equals("")?"My description...":String.valueOf(result.get("description"));
                    final String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                    final String username = result.get("username")==null?"":String.valueOf(result.get("username"));
                    final int id = result.get("user_id")==null?0:Integer.valueOf(String.valueOf(result.get("user_id")));

                    descriptionView.setText(description);
                    usernameView.setText(username);
                }
                hideActivitySpinner();
            }
        };
        api.v1.getUserByID(String.valueOf(args.getInt("user_id")),single);
    }
}