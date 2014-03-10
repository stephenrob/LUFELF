package uk.co.stephen_robinson.uni.lufelf.api.v1;

import uk.co.stephen_robinson.uni.lufelf.api.Api;
import uk.co.stephen_robinson.uni.lufelf.api.SessionManager;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;

/**
 * Created by Stephen on 21/02/14.
 */

/**
 * @author stephen
 *
 * Public interface to version 1 of the api, hold all user callable methods
 */
public class V1 {

    /**
     * Registers a new user in the system
     *
     * @param name name of the user to create
     * @param lib_no library number of the user to create
     * @param username username of the user to create
     * @param password password of the user to create
     * @param dob dob of the user to create
     * @param type type of user to create
     * @param description description of the user to crate
     * @param location_status privacy status of users location
     * @param access_level access level for the user
     * @param sc single callback to execute when the network task completes
     */
    public void registerUser(String name, String lib_no, String username, String password, String dob, String type, String description, int location_status, int access_level, Single sc){
        User user = new User();

        user.name = name;
        user.lib_no = lib_no;
        user.username = username;
        user.password = password;
        user.dob = dob;
        user.type = type;
        user.description = description;
        user.location_status = location_status;
        user.access_level = access_level;

        User.register(user, sc);
    }

    /**
     * Logs user into lufelf
     *
     * @param username username of the user to login
     * @param password password of the user to login
     * @param sc single callback to execute when the network task completes
     */
    public void loginUser(String username, String password, Single sc){
        User user = new User();

        user.username = username;
        user.password = password;

        User.login(user, sc);
    }

    /**
     * Search user details by their username
     *
     * @param param username to search for
     * @param sc single callback to execute when the network task completes
     */
    public void getUserByUsername(String param, Single sc){
        User.getDetails(User.Search.USERNAME, param, sc);
    }

    /**
     * Search user details by their library number
     *
     * @param param library number to search for
     * @param sc single callback to execute when the network task completes
     */
    public void getUserByLibraryNumber(String param, Single sc){
        User.getDetails(User.Search.LIB_NO, param, sc);
    }

    /**
     * Search user details by their name
     * @param param name to search for
     * @param mc multiple callback to execute when the network task completes
     */
    public void getUserByName(String param, Multiple mc){
        User.getMultipleDetails(User.Search.NAME, param, mc);
    }

    /**
     * search user details by user id
     * @param param user id to search for
     * @param sc single callback to execute when the network task completes
     */
    public void getUserByID(String param, Single sc){
        User.getDetails(User.Search.USER_ID, param, sc);
    }

    /**
     * delete currently logged in user
     * @param sc single callback to execute when the network task completes
     */
    public void deleteUser(Single sc){
        User.delete(sc);
    }

    /**
     * Add a new place/location to lufelf
     *
     * @param name name of the place to create
     * @param address address of the place to create
     * @param lat lattitude of the place
     * @param lon longitude of the place
     * @param type type of place to create
     * @param description description of the place to create
     * @param sc single callback to execute when the network task completes
     */
    public void addPlace(String name, String address, Double lat, Double lon, String type, String description, Single sc){
        Place place = new Place();

        place.name = name;
        place.address = address;
        place.lattitude = lat;
        place.longditude = lon;
        place.type = type;
        place.description = description;

        Place.create(place, sc);
    }

    /**
     * get all the places stored within lufelf
     * @param mc multiple callback to execute when the network task completes
     */
    public void getAllPlaces(Multiple mc){
        Place.listAll(mc);
    }

    /**
     * get all the events stored within lufelf
     * @param mc multiple callback to execute when the network task completes
     */
    public void getAllEvents(Multiple mc){
        Event.listAll(mc);
    }

    /**
     * get single event information
     * @param id id of the event to get information on
     * @param mc multiple callback to execute when the network task completes
     */
    public void getEvent(Integer id, Multiple mc){
        Event.single(id, mc);
    }

    /**
     * create a new event in lufelf
     *
     * @param name name of the event to create
     * @param date date/time of the event
     * @param type type of event
     * @param description description of the event
     * @param place_id place event is at
     * @param sc single callback to execute when the network task completes
     */
    public void addEvent(String name, String date, String type, String description, Integer place_id, Single sc){
        Event event = new Event();

        event.name = name;
        event.date = date;
        event.type = type;
        event.description = description;
        event.place_id = place_id;

        Event.create(event, sc);
    }

    /**
     * delete event from the system, can only delete own events
     * @param eventId event id to delete
     * @param sc single callback to execute when the network task completes
     */
    public void deleteEvent(Integer eventId, Single sc){
        Event.delete(eventId, sc);
    }

    /**
     * add new friend
     * @param friendId user id of friend to add
     * @param sc single callback to execute when the network task completes
     */
    public void addFriend(Integer friendId, Single sc){
        Friend.add(friendId, sc);
    }

    /**
     * delete a friend
     * @param friendId user id of friend to delete
     * @param sc single callback to execute when the network task completes
     */
    public void deleteFriend(Integer friendId, Single sc){
        Friend.delete(friendId, sc);
    }

    /**
     * update location privacy of current user
     * @param privacy value of privacy to set
     * @param sc single callback to execute when the network task completes
     */
    public void updatePrivacy(Integer privacy, Single sc){
        Status.updatePrivacy(privacy, sc);
    }

    /**
     * get the users current privacy setting from shared prefs.
     * @return current privacy setting of the user
     */
    public String currentPrivacy(){
        return Api.getSessionManager().getPrivacy();
    }

    /**
     * update users location
     * @param lat lattitude of new location
     * @param lon longitude of new location
     * @param sc single callback to execute when the network task completes
     */
    public void updateLocation(Double lat, Double lon, Single sc){
        Status.updateLocation(lat, lon, sc);
    }

    /**
     * get the current users user id from shared prefs
     * @return user id
     */
    public String currentUserId(){
        return Api.getSessionManager().getUserDetails().get(SessionManager.KEY_USERID);
    }

    /**
     * get the current users hashed password from shared prefs
     * @return hashed user password
     */
    public String currentPasswordHash(){
        return Api.getSessionManager().getUserDetails().get(SessionManager.KEY_PASSWORD);
    }

    /**
     * get all friend requests
     * @param mc multiple callback to execute when the network task completes
     */
    public void getFriendRequests(Multiple mc){
        Friend.requests(mc);
    }

    /**
     * accept a friend request
     * @param request_id id of the request to accept
     * @param friend_id user id of the user in the friend request
     * @param sc single callback to execute when the network task completes
     */
    public void acceptFriendRequest(Integer request_id, Integer friend_id, Single sc){
        Friend.updateRequest(request_id, friend_id, 1, sc);
    }

    /**
     * ignore/hide a friend request
     * @param request_id id of the request to hide
     * @param friend_id user id of the user in the friend request
     * @param sc single callback to execute when the network task completes
     */
    public void hideFriendRequest(Integer request_id, Integer friend_id, Single sc){
        Friend.updateRequest(request_id, friend_id, 2 , sc);
    }

    /**
     * get list of all friends
     * @param mc multiple callback to execute when the network task completes
     */
    public void getFriendsList(Multiple mc){
        Friend.list(mc);
    }

    /**
     * attend event
     * @param event_id id of the event to attend
     * @param sc single callback to execute when the network task completes
     */
    public void attendEvent(Integer event_id, Single sc){
        Event.attend(event_id, sc);
    }

    /**
     * get all sent messages
     * @param mc multiple callback to execute when the network task completes
     */
    public void getSentMessages(Multiple mc){
        Message.sent(mc);
    }

    /**
     * get all received messages
     * @param mc multiple callback to execute when the network task completes
     */
    public void getReceivedMessages(Multiple mc){
        Message.received(mc);
    }

    /**
     * send a message
     * @param to user id to send message to
     * @param message message to send to the user
     * @param sc single callback to execute when the network task completes
     */
    public void sendMessage(Integer to, String message, Single sc){
        Message.send(to, message, sc);
    }

    /**
     * check is the user logged in via session manager
     * @return boolean of users login status via shared prefs.
     */
    public Boolean isLoggedIn(){
        return Api.getSessionManager().checkLogin();
    }

    /**
     * Logout the user from lufelf, clears shared prefs.
     */
    public void logoutUser(){
        Api.getSessionManager().logoutUser();
    }
}
