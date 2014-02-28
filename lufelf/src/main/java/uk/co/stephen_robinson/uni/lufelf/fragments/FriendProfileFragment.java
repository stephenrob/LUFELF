package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Friend;
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
        String dscrpText=args.getString("description").equals("")?"My profile...":args.getString("description");
        description.setText(dscrpText);

        id=args.getInt("user_id");

        ImageView imageView=(ImageView)rootView.findViewById(R.id.image_friend);
        DownloadImage downloadImage = new DownloadImage(imageView,getActivity(),DownloadImage.AVATAR,id);

        downloadImage.downloadFromServer(isNetworkAvailable());
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
            checkFriend(addFriend,args.getString("username"));
        }
        return rootView;
    }
    public void checkFriend(final TextView addFriend,final String username){
        showActivitySpinner();
        Multiple m = new Multiple() {
            @Override
            public void results(ArrayList result) {
                ArrayList<UserItem>users=new ArrayList<UserItem>();


                Message m =(Message)result.get(result.size()-1);

                if(result.size()>0){

                    for(int i=0;i<result.size()-1;i++){
                        Friend u =(Friend)result.get(i);
                        users.add(new UserItem("","","",u.getUsername(),0,true));
                    }
                    UserItem u =new UserItem("", "", "", username, id,false);

                    if(crossReference(u,users)){
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
                    }else{
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
                    }
                }
                hideActivitySpinner();
            }
        };

        api.v1.getFriendsList(m);
    }
    public boolean crossReference(UserItem user, ArrayList<UserItem> friends){

        for(UserItem u:friends){
            if(u.getEmailAdd().equals(user.getEmailAdd())){
                return true;
            }
        }

        return false;
    }
/*
    Multiple m = new Multiple() {
        @Override
        public void results(ArrayList result) {
            ArrayList<UserItem> userItems= new ArrayList<UserItem>();
            ArrayList<UserItem>users=new ArrayList<UserItem>();

            Message m =(Message)result.get(result.size()-1);

            if(result.size()>0){

                for(int i=0;i<result.size()-1;i++){
                    User u =(User)result.get(i);
                    users.add(new UserItem(u.getName(),u.getDescription(),u.getLib_no(),"",u.getUser_id(),true));
                }
                UserItem u =new UserItem(name, description, libno, username, id,false);
                Log.e("USER", crossReference(u, users).getName());
                userItems.add(crossReference(u,users));
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        UserItem user =(UserItem) list.getItemAtPosition(i);
                        fragmentManager.beginTransaction().add(R.id.container, FriendProfileFragment.newInstance(user), "UserProfileSubView").addToBackStack(null).commit();
                    }
                });


            }else{
                userItems.add(UserItem.getBlankResult());
            }
            list.setAdapter(new UserItemAdapter(context,userItems));
        }
    };

    api.v1.getFriendsList(m);
    */
}