package uk.co.stephen_robinson.uni.lufelf.adapters;

import com.google.android.gms.maps.model.LatLng;

import uk.co.stephen_robinson.uni.lufelf.R;

/**
 * @author James
 * Models a place
 */
public class PlaceItem {

    private static int other= R.drawable.university;
    private static int pub=R.drawable.pub;
    private static int restaurant=R.drawable.restaurant;
    private static int lecture_theatre=R.drawable.lecture;
    private static int seminar=R.drawable.seminar_maybe;
    private static int cafe=R.drawable.cafe;
    private static int takeaway=R.drawable.takeaway;
    private static int cinema=R.drawable.cinema;
    private static int hall=R.drawable.hall;
    private static int shop=R.drawable.shop;
    private static int library=R.drawable.library;

    private int id;
    private String name="";
    private String address="";
    private String type="";
    private String description="";
    private String imageUrl="";
    private int createdByID;
    private int icon;

    private LatLng loc;

    /**
     * Constructs a place in memory
     * @param id place id number
     * @param name place name
     * @param address place address
     * @param type place type
     * @param description place description
     * @param imageUrl place image
     * @param lat place latitude
     * @param longit place longitude
     */
    public PlaceItem(int id, String name,String address,String type,String description, int createdByID, String imageUrl, double lat, double longit){
        this.id=id;
        this.name=name;
        this.address=address;
        this.description=description;
        this.createdByID=createdByID;
        this.imageUrl=imageUrl;
        this.loc=new LatLng(lat,longit);
        this.type=type;
        setIcon(type);
    }

    /**
     * Gets the place id
     * @return place id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the place name
     * @return place name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the place address
     * @return place address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the place type
     * @return place type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the place description
     * @return place description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the place image
     * @return place image
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Gets the place icon
     * @return place icon
     */
    public int getIcon() {
        return icon;
    }

    public int getCreatedByID() {
        return createdByID;
    }

    /**
     * Gets the place location
     * @return place location
     */
    public LatLng getLocation() {
        return loc;
    }

    /**
     * Sets the icon depending type
     * @param type place type
     */
    public void setIcon(String type){
        if(type.equals("other"))
            icon=other;
        if(type.equals("pub"))
            icon=pub;
        if(type.equals("restaurant"))
            icon=restaurant;
        if(type.equals("lecture_theatre"))
            icon=lecture_theatre;
        if(type.equals("seminar"))
            icon=seminar;
        if(type.equals("cafe"))
            icon=cafe;
        if(type.equals("takeaway"))
            icon=takeaway;
        if(type.equals("cinema"))
            icon=cinema;
        if(type.equals("hall"))
            icon=hall;
        if(type.equals("shop"))
            icon=shop;
        if(type.equals("library"))
            icon=library;
        if(type.equals(""))
            icon=other;
        if(icon==0)
            icon=other;
    }

    /**
     * Converts an index from a switch into a compatible string for the database
     * @param selectedIndex the index which the user has selected
     * @return the database compatible string
     */
    public static String convertTypeIntoCompatibleString(int selectedIndex){
        switch (selectedIndex){
            case 0:
                return "pub";
            case 1:
                return "restaurant";
            case 2:
                return "lecture_theatre";
            case 3:
                return "seminar_room";
            case 4:
                return "cafe";
            case 5:
                return "takeaway";
            case 6:
                return "cinema";
            case 7:
                return "hall";
            case 8:
                return "shop";
            case 9:
                return "library";
            case 10:
                return "other";
        }
        return "";
    }
}
