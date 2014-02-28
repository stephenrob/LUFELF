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
import uk.co.stephen_robinson.uni.lufelf.utilities.ValidationChecker;

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
        searchBox.setHint("Enter username, lib no or name");

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


                                final String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                                final String description = result.get("description")==null?"My description...":String.valueOf(result.get("description"));
                                final String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                                final String username = result.get("username")==null?"":String.valueOf(result.get("username"));
                                final int id = result.get("user_id")==null?0:Integer.valueOf(String.valueOf(result.get("user_id")));

                                ArrayList<UserItem> userItems= new ArrayList<UserItem>();

                                userItems.add(new UserItem(name, description, libno, username, id,true));
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserItem user = (UserItem) list.getItemAtPosition(i);
                                        fragmentManager.beginTransaction().add(R.id.container, FriendProfileFragment.newInstance(user), "UserProfileSubView").addToBackStack(null).commit();
                                    }
                                });
                                list.setAdapter(new UserItemAdapter(context,userItems));
                            }

                            hideActivitySpinner();
                        }
                    };
                    String searchBoxValue=searchBox.getText().toString();
                    boolean ok = ValidationChecker.checkIfEmpty(searchBoxValue);
                    if(!ok){
                        if(ValidationChecker.isEmailValid(searchBoxValue)){
                            Log.e("CRAP","email");
                            api.v1.getUserByUsername(searchBoxValue, single);
                        }else if(ValidationChecker.isAllNumbers(searchBoxValue)){
                            Log.e("CRAP","lib_no");
                            api.v1.getUserByLibraryNumber(searchBoxValue, single);
                        }else{
                            Log.e("CRAP","username");
                            api.v1.getUserByName(searchBoxValue, single);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }
    public void setUpForEventSearch(){

    }


}
