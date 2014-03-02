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
import uk.co.stephen_robinson.uni.lufelf.adapters.FriendItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.FriendItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Friend;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * @author James
 * Instantiates a friends fragment
 */
public class FriendsFragment extends BaseFragment{

    private ListView friendsList;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();
        ImageView addFriend = (ImageView)rootView.findViewById(R.id.friends_title_buttons);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().add(R.id.container, SearchFragment.newInstance(SearchFragment.friendsSearch), "AddFriend").addToBackStack(null).commit();
            }
        });
        friendsList=(ListView)rootView.findViewById(R.id.friends_listview);


        loadFriends();

        return rootView;
    }
    public void loadFriends(){
        showActivitySpinner();
        Multiple mc = new Multiple() {


            @Override
            public void results(ArrayList result) {
                Message m =(Message)result.get(result.size()-1);
                ArrayList<FriendItem> friendItems = new ArrayList<FriendItem>();
                if(!toastMaker.isError(String.valueOf(m.getStatusCode()),m.getMessage())){

                    for(int i=0;i<result.size()-1;i++){
                        Friend f =(Friend)result.get(i);
                        friendItems.add(new FriendItem(f.getRequest_id(),f.getFriend_id(),f.getUser_id(),f.getFriend_status(),f.getName(),f.getUsername(),f.getLocation_status(),f.getLattitude(),f.getLongitude()));
                    }
                    friendsList.setAdapter(new FriendItemAdapter(context,friendItems));
                    friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            FriendItem item = (FriendItem) friendsList.getItemAtPosition(i);
                            fragmentManager.beginTransaction().add(R.id.container, RequestProfileFragment.newInstance(item), "EventSubView").addToBackStack(null).commit();
                        }
                    });
                }else{
                    friendItems.add(FriendItem.getBlankResult());
                    friendsList.setAdapter(new FriendItemAdapter(context,friendItems));
                }
                hideActivitySpinner();
            }
        };

        api.v1.getFriendsList(mc);
    }
}
