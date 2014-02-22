package uk.co.stephen_robinson.uni.lufelf.api.v1;

import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Multiple;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;

/**
 * Created by Stephen on 21/02/14.
 */
public class V1 {

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

    public void loginUser(String username, String password, Single sc){
        User user = new User();

        user.username = username;
        user.password = password;

        User.login(user, sc);
    }

    public void getUserByUsername(String param, Single sc){
        User.getDetails(User.Search.USERNAME, param, sc);
    }

    public void getUserByLibraryNumber(String param, Single sc){
        User.getDetails(User.Search.LIB_NO, param, sc);
    }

    public void getUserByName(String param, Single sc){
        User.getDetails(User.Search.NAME, param, sc);
    }

    public void getUserByID(String param, Single sc){
        User.getDetails(User.Search.USER_ID, param, sc);
    }

    public void deleteUser(Single sc){
        User.delete(sc);
    }

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

    public void getAllPlaces(Multiple mc){
        Place.listAll(mc);
    }

    public void getAllEvents(Multiple mc){
        Event.listAll(mc);
    }

    public void getEvent(Integer id, Multiple mc){
        Event.single(id, mc);
    }
}
