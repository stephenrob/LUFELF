package uk.co.stephen_robinson.uni.lufelf.adapters;

/**
 * @author James
 * holds details about a friend
 */
public class FriendItem {
    protected int request_id;
    protected int friend_id;
    protected int user_id;
    protected int status;
    protected String name;
    protected String username;
    protected int location_status;
    protected Double lattitude;
    protected Double longitude;

    /**
     * returns the request id
     * @return request id
     */

    public int getRequest_id() {
        return request_id;
    }

    /**
     * returns the friend id
     * @return friend id
     */
    public int getFriend_id() {
        return friend_id;
    }

    /**
     * returns the user id
     * @return the user id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * get the status of the user
     * @return the users' status
     */
    public int getStatus() {
        return status;
    }

    /**
     * get the name of the user
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get the username of a user
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns the location status
     * @return  location status
     */
    public int getLocation_status() {
        return location_status;
    }

    /**
     * returns the current latitude of the user
     * @return latitude of the user
     */
    public Double getLattitude() {
        return lattitude;
    }

    /**
     * get the longitude of the user
     * @return users' longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * constructor for a friend item
     * @param request_id
     * @param friend_id
     * @param user_id
     * @param status
     * @param name
     * @param username
     * @param location_status
     * @param lattitude
     * @param longitude
     */
    public FriendItem(int request_id, int friend_id, int user_id, int status, String name, String username, int location_status, Double lattitude, Double longitude) {

        this.request_id = request_id;
        this.friend_id = friend_id;
        this.user_id = user_id;
        this.status = status;
        this.name = name;
        this.username = username;
        this.location_status = location_status;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    /**
     * returns a blank friend item
     * @return blank friend item.
     */
    public static FriendItem getBlankResult(){
        return new FriendItem(0,0,0,0,"None","",0,0.0,0.0);
    }
}
