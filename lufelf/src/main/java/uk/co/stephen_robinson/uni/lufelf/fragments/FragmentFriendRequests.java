package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class FragmentFriendRequests extends BaseFragment{

    private ListView friendsList;

    public static FragmentFriendRequests newInstance() {
        return new FragmentFriendRequests();
    }

    public FragmentFriendRequests() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_friend_request, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

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
                ArrayList<FriendItem> friends = new ArrayList<FriendItem>();
                if(!toastMaker.isError(String.valueOf(m.getStatusCode()),m.getMessage())){
                    Log.e("result size ",String.valueOf(result.size()));
                    for(int i=0;i<result.size()-1;i++){

                        Friend f =(Friend)result.get(i);
                        friends.add(new FriendItem(f.getRequest_id(), f.getFriend_id(), f.getUser_id(), f.getFriend_status(), f.getName(), f.getUsername(), f.getLocation_status(), f.getLattitude(), f.getLongitude()));
                    }
                    friendsList.setAdapter(new FriendItemAdapter(context, friends));
                    friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            FriendItem item = (FriendItem) friendsList.getItemAtPosition(i);

                            fragmentManager.beginTransaction().add(R.id.container, RequestProfileFragment.newInstance(item), "FriendRequest").addToBackStack(null).commit();
                        }
                    });
                }else{
                    friends.add(FriendItem.getBlankResult());
                    friendsList.setAdapter(new FriendItemAdapter(context, friends));
                }
                hideActivitySpinner();
            }
        };

        api.v1.getFriendRequests(mc);
    }
}
