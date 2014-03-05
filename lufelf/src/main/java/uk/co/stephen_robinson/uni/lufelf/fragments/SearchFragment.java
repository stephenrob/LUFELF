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

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.utilities.EventDateComparator;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.adapters.EventListItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.NavDrawerItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.PlaceItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.PlaceItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItem;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItemAdapter;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Event;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.EventUser;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Place;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.User;
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
                setUpForUserSearch();
                break;
            case eventSearch:
                setUpForEventSearch();
                break;
        }



        return rootView;
    }

    /**
     * configure the search for places.
     */
    public void setUpForPlaceSearch(){

        searchBox.setHint("Enter your search criteria");

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if(ValidationChecker.checkSize(searchBox,3))
                        searchForLocations();

                    return true;
                }
                return false;
            }
        });

    }

    /**
     * search the locations
     */
    public void searchForLocations(){
        showActivitySpinner();
        Multiple multipleCallback = new Multiple() {
            @Override
            public void results(ArrayList result) {
                ArrayList<PlaceItem> placeItems=new ArrayList<PlaceItem>();
                Message m = (Message)result.get(result.size()-1);
                if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){
                    for(int i=0;i<result.size()-1;i++){
                        Place p =(Place)result.get(i);
                        placeItems.add(new PlaceItem(p.getId(),p.getName(),p.getAddress(),p.getType(),p.getDescription(),p.getUser_id(),p.getImage_url(),p.getLattitude(),p.getLongditude()));
                    }
                    list.setAdapter(new PlaceItemAdapter(context, matchPlaceCriteria(searchBox.getText().toString(), placeItems)));
                }
                hideActivitySpinner();
            }
        };
        api.v1.getAllPlaces(multipleCallback);
    }

    /**
     * setup the search for users.
     */
    public void setUpForUserSearch(){
        searchBox.setHint("Enter username, lib no or name");

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    searchBox.performClick();

                    Single single = new Single() {
                        @Override
                        public void results(Hashtable result) {
                            ArrayList<UserItem> userItems= new ArrayList<UserItem>();
                            if(!toastMaker.isError(String.valueOf(result.get(Message.CODE)),(String)result.get(Message.MESSAGE))){


                                final String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                                final String description = result.get("description")==null?"My description...":String.valueOf(result.get("description"));
                                final String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                                final String username = result.get("username")==null?"":String.valueOf(result.get("username"));
                                final int id = result.get("user_id")==null?0:Integer.valueOf(String.valueOf(result.get("user_id")));



                                userItems.add(new UserItem(name, description, libno, username, id,true));
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserItem user = (UserItem) list.getItemAtPosition(i);
                                        fragmentManager.beginTransaction().add(R.id.container, FriendProfileFragment.newInstance(user), "UserProfileSubView").addToBackStack(null).commit();
                                    }
                                });
                            }else{
                                userItems.add(UserItem.getBlankResult());

                            }
                            list.setAdapter(new UserItemAdapter(context,userItems));

                            hideActivitySpinner();
                        }
                    };
                    Multiple multiple = new Multiple() {
                        @Override
                        public void results(ArrayList result) {
                            Message m =(Message) result.get(result.size()-1);
                            ArrayList<UserItem> userItems= new ArrayList<UserItem>();
                            if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){

                                for(int i=0;i<result.size()-1;i++){
                                    User u =(User) result.get(i);
                                    userItems.add(new UserItem(u.getName(),u.getDescription(),u.getLib_no(),u.getUsername(),u.getUser_id(),false));
                                }
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserItem user = (UserItem) list.getItemAtPosition(i);
                                        fragmentManager.beginTransaction().add(R.id.container, FriendProfileFragment.newInstance(user), "UserProfileSubView").addToBackStack(null).commit();
                                    }
                                });

                            }else{
                                userItems.add(UserItem.getBlankResult());

                            }
                            list.setAdapter(new UserItemAdapter(context,userItems));
                            hideActivitySpinner();
                        }
                    };
                    String searchBoxValue=searchBox.getText().toString();
                    EditText[] editTexts={searchBox};
                    boolean ok = ValidationChecker.standardValidationCheck(editTexts);
                    if(ok){
                        showActivitySpinner();
                        if(ValidationChecker.isEmailValid(searchBoxValue))
                            api.v1.getUserByUsername(searchBoxValue, single);
                        else if(ValidationChecker.isAllNumbers(searchBoxValue))
                            api.v1.getUserByLibraryNumber(searchBoxValue, single);
                        else
                            api.v1.getUserByName(searchBoxValue, multiple);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    /**
     * set up the search for events
     */
    public void setUpForEventSearch(){
        searchBox.setHint("Enter your search criteria");

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if(ValidationChecker.checkSize(searchBox,3))
                        searchForEvents();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * search for the events.
     */
    public void searchForEvents(){
        showActivitySpinner();
        Multiple multipleCallback=new Multiple() {
            @Override
            public void results(ArrayList result) {
                ArrayList eventItems=new ArrayList<NavDrawerItem>();
                Message m = (Message)result.get(result.size()-1);
                if(!toastMaker.isError(String.valueOf(m.statusCode),m.message)){
                    for(int i=0;i<result.size()-1;i++){
                        Event e=(Event)result.get(i);;
                        eventItems.add(new EventListItem(String.valueOf(e.getId()),e.getName(),R.drawable.ic_location,"Creator "+i,new LatLng(i,i),e.getDate(),"This is a description for EVENT "+i,new ArrayList<EventUser>()));

                    }
                    Collections.sort(eventItems, new EventDateComparator());
                    list.setAdapter(new EventItemAdapter(context, matchCriteria(searchBox.getText().toString(),eventItems)));
                }
                hideActivitySpinner();
            }
        };
        api.v1.getAllEvents(multipleCallback);

    }

    /**
     * match the criteria
     * @param criteria the search criteria
     * @param eventListItems the events to search
     * @return the matched events
     */
    public ArrayList<EventListItem> matchCriteria(String criteria,ArrayList<EventListItem> eventListItems){
        ArrayList<EventListItem> returnedItems = new ArrayList<EventListItem>();
        String[] criteriaSplit =criteria.split("\\s* \\s*");
        for(int i=0;i<criteriaSplit.length;i++)
            for(EventListItem e:eventListItems)
                if(e.getEventName().contains(criteriaSplit[i])||e.getEventName().equals(criteriaSplit[i])||e.getCreator().contains(criteriaSplit[i])||e.getCreator().equals(criteriaSplit[i])||e.getDescription().contains(criteriaSplit[i])||e.getDescription().equals(criteriaSplit[i])&&!searchForId(returnedItems,e.getId()))
                    returnedItems.add(e);

        if(returnedItems.size()==0){
            returnedItems.add(new EventListItem("","No Results",0,"",new LatLng(0.0,0.0),"","",new ArrayList<EventUser>()));
        }else{
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    fragmentManager.beginTransaction().add(R.id.container, EventProfileFragment.newInstance((EventListItem) list.getItemAtPosition(i)), "EventSubView").addToBackStack(null).commit();

                }
            });
        }

        return returnedItems;
    }

    /**
     * search places using criteria
     * @param criteria the search criteria
     * @param placeItems the list of places to search
     * @return all events that match the search criteria.
     */
    public ArrayList<PlaceItem> matchPlaceCriteria(String criteria,ArrayList<PlaceItem> placeItems){
        ArrayList<PlaceItem> returnedItems = new ArrayList<PlaceItem>();
        String[] criteriaSplit =criteria.split("\\s* \\s*");
        for(int i=0;i<criteriaSplit.length;i++)
            for(PlaceItem p:placeItems)
                if(p.getName().contains(criteriaSplit[i])||p.getName().equals(criteriaSplit[i])||p.getType().contains(criteriaSplit[i])||p.getDescription().contains(criteriaSplit[i])||p.getDescription().equals(criteriaSplit[i])&&!searchForPlaceId(returnedItems, p.getId()))
                    returnedItems.add(p);

        Log.e("ITEms",String.valueOf(returnedItems.size()));
        if(returnedItems.size()==0){
            returnedItems.add(new PlaceItem(0,"","No Results","","",0,"",0.0,0.0));
        }else{
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    fragmentManager.beginTransaction().add(R.id.container, PlaceProfileFragment.newInstance((PlaceItem) list.getItemAtPosition(i)), "EventSubView").addToBackStack(null).commit();

                }
            });
        }

        return returnedItems;
    }

    /**
     * search for an id
     * @param e the event list items
     * @param id the id to match
     * @return boolean if the item is found in the list
     */
    public boolean searchForId(ArrayList<EventListItem> e,String id){

        for(EventListItem eventListItem:e)
            if(eventListItem.getId().equals(id))
                return true;
        return false;
    }

    /**
     * search for an id of a place
     * @param p the list of places to search
     * @param id the id to search for
     * @return boolean if the places is found
     */
    public boolean searchForPlaceId(ArrayList<PlaceItem> p,int id){

        for(PlaceItem placeItem:p)
            if(placeItem.getId()==id)
                return true;
        return false;
    }

}
