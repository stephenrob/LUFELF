package uk.co.stephen_robinson.uni.lufelf.api;

import java.util.Hashtable;

/**
 * Created by Stephen on 18/02/14.
 */
public class V1 {

    public void registerUser(UserDetails userDetails, NetworkCallback nc){
        User.register(userDetails, nc);
    }

    public void loginUser(String username, String password, NetworkCallback nc){
        UserDetails userDetails = new UserDetails();

        userDetails.username = username;
        userDetails.password = password;

        User.login(userDetails, nc);
    }

    public void findUserByUsername(String param, NetworkCallback nc){
        User.getDetails(User.Search.USERNAME, param, nc);
    }

    public void findUserByLibraryNumber(String param, NetworkCallback nc){
        User.getDetails(User.Search.LIB_NO, param, nc);
    }

    public void findUserByName(String param, NetworkCallback nc){
        User.getDetails(User.Search.NAME, param, nc);
    }

    public void findUserByUserId(String param, NetworkCallback nc){
        User.getDetails(User.Search.USER_ID, param, nc);
    }

    public void deleteUser(NetworkCallback nc){
        User.delete(nc);
    }

    public void createEvent(Hashtable params, NetworkCallback nc){

    }

    public void getSingleEventDetails(Hashtable params, NetworkCallback nc){

    }

    public void deleteEvent(Hashtable params, NetworkCallback nc){

    }

    public void getAllEvents(Hashtable params, NetworkCallback nc){

    }

    public void getSocialEvents(Hashtable params, NetworkCallback nc){

    }

    public void getUniversityEvents(Hashtable params, NetworkCallback nc){

    }

    public void getOpenDayEvents(Hashtable params, NetworkCallback nc){

    }

    public void getPublicEvents(Hashtable params, NetworkCallback nc){

    }

    public void getPartyEvents(Hashtable params, NetworkCallback nc) {

    }

    public void getStudyEvents(Hashtable params, NetworkCallback nc){

    }

    public void attendEvent(Hashtable params, NetworkCallback nc){

    }

    public void updateUserLocation(Hashtable params, NetworkCallback nc){

    }

    public void updateUserLocationStatus(Hashtable params, NetworkCallback nc){

    }

    public void sendMessage(Hashtable params, NetworkCallback nc){

    }

    public void getSentMessages(Hashtable params, NetworkCallback nc){

    }

    public void getReceivedMessages(Hashtable params, NetworkCallback nc){

    }

    public void createLocation(Hashtable params, NetworkCallback nc){

    }

    public void getAllLocations(Hashtable params, NetworkCallback nc){

    }

    public void getOtherLocations(Hashtable params, NetworkCallback nc){

    }

    public void getPubs(Hashtable params, NetworkCallback nc){

    }

    public void getRestaurants(Hashtable params, NetworkCallback nc){

    }

    public void getLectureTheatres(Hashtable params, NetworkCallback nc){

    }

    public void getSeminarRooms(Hashtable params, NetworkCallback nc){

    }

    public void getCafes(Hashtable params, NetworkCallback nc){

    }

    public void getTakeaways(Hashtable params, NetworkCallback nc){

    }

    public void getCinemas(Hashtable params, NetworkCallback nc){

    }

    public void getHalls(Hashtable params, NetworkCallback nc){

    }

    public void getShops(Hashtable params, NetworkCallback nc){

    }

    public void getLibraries(Hashtable params, NetworkCallback nc){

    }

    public void uploadProfilePicture(Hashtable params, NetworkCallback nc){

    }

    public void getProfilePicture(Hashtable params, NetworkCallback nc){

    }

    public void uploadLocationPicture(Hashtable params, NetworkCallback nc){

    }

    public void getLocationPicture(Hashtable params, NetworkCallback nc){

    }

    public void uploadEventPicture(Hashtable params, NetworkCallback nc){

    }

    public void getEventPicture(Hashtable params, NetworkCallback nc){

    }

    public void addFriend(Hashtable params, NetworkCallback nc){

    }

    public void deleteFriend(Hashtable params, NetworkCallback nc){

    }

    public void getFriendDetails(Hashtable params, NetworkCallback nc){

    }

    public void getFriendLocation(Hashtable params, NetworkCallback nc){

    }

    public void getFriendRequests(Hashtable params, NetworkCallback nc){

    }

    public void acceptFriendRequest(Hashtable params, NetworkCallback nc){

    }

    public void deleteFriendRequest(Hashtable params, NetworkCallback nc){

    }

    public void getAllFriends(Hashtable params, NetworkCallback nc){

    }
}
