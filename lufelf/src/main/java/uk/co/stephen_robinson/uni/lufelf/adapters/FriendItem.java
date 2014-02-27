package uk.co.stephen_robinson.uni.lufelf.adapters;

/**
 * Created by James on 26/02/2014.
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

    public int getRequest_id() {
        return request_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getLocation_status() {
        return location_status;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

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
    public static FriendItem getBlankResult(){
        return new FriendItem(0,0,0,0,"None","",0,0.0,0.0);
    }
}
