package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;

/**
 * @author James
 * Fragment for searching friends
 */
public class SearchFragment extends BaseFragment{
    public static final int placeSearch=0;
    public static final int friendsSearch=1;
    public static final int eventSearch=2;

    private ListView list;

    private EditText searchBox;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SearchFragment newInstance(int searchType) {
        SearchFragment fragment=new SearchFragment();
        Bundle args=new Bundle();
        args.putInt("search_type",searchType);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        list = (ListView)rootView.findViewById(R.id.search_listview);

        Bundle args = getArguments();

        searchBox = (EditText)rootView.findViewById(R.id.friend_search_box);

        int searchtype=args.getInt("search_type");

        switch (searchtype){
            case placeSearch:
                setUpForPlaceSearch();
                break;
            case friendsSearch:
                setUpForFriendSearch();
                break;
            case eventSearch:
                setUpForEventSearch();
                break;
        }



        return rootView;
    }
    public void setUpForPlaceSearch(){

    }
    public void setUpForFriendSearch(){
        searchBox.setHint("Enter username (Email Address).");

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    searchBox.performClick();
                    showActivitySpinner();
                    Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {
                            Enumeration keys=result.keys();
                            while(keys.hasMoreElements())
                                Log.e("Key", keys.nextElement().toString());
                            if(!toastMaker.isError(String.valueOf(result.get(Message.CODE)),(String)result.get(Message.MESSAGE))){
                                ArrayList<UserItem> userItems= new ArrayList<UserItem>();

                                String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                                String description = result.get("description")==null?"":String.valueOf(result.get("description"));
                                String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                                String username = result.get("username")==null?"":String.valueOf(result.get("username"));
                                int id = result.get("id")==null?0:Integer.valueOf(String.valueOf(result.get("name")));

                                userItems.add(new UserItem(name, description, libno, username, id));
                                list.setAdapter(new UserItemAdapter(context,userItems));

                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserItem user =(UserItem) list.getItemAtPosition(i);
                                        fragmentManager.beginTransaction().add(R.id.container, FriendsSubFragment.newInstance(user), "UserProfileSubView").addToBackStack(null).commit();
                                    }
                                });
                            }

                            hideActivitySpinner();
                        }
                    };

                    api.v1.getUserByUsername(searchBox.getText().toString(), single);

                    return true;
                }
                return false;
            }
        });
    }
    public void setUpForEventSearch(){

    }
}
