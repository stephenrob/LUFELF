package uk.co.stephen_robinson.uni.lufelf.api.v1;

import uk.co.stephen_robinson.uni.lufelf.api.network.callbacks.Single;

/**
 * Created by Stephen on 21/02/14.
 */
public class V1 {

    public void registerUser(Single sc){
        User.register(new User(), sc);
    }

    public void loginUser(String username, String password, Single sc){
        User.login(new User(), sc);
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

    public void addPlace(String name, String address, Long lat, Long lon, String type, String description, Single sc){
        Place place = new Place();

        place.name = name;
        place.address = address;
        place.lattitude = lat;
        place.longditude = lon;
        place.type = type;
        place.description = description;

        Place.create(place, sc);
    }
}
