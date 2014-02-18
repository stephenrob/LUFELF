package uk.co.stephen_robinson.uni.lufelf;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by James on 17/02/2014.
 */
public class PlaceItem {

    private static int other=R.drawable.other;
    private static int pub=R.drawable.pub;
    private static int restaurant=R.drawable.restaurant;
    private static int lecture_theatre=R.drawable.lecture;
    private static int seminar=R.drawable.seminar_maybe;
    private static int cafe=0;
    private static int takeaway=R.drawable.takeaway;
    private static int cinema=R.drawable.cinema;
    private static int hall=R.drawable.hall;
    private static int shop=R.drawable.shop;
    private static int library=R.drawable.library;

    private String id;
    private String name;
    private String address;
    private String type;
    private String description;
    private String imageUrl;

    private int icon;

    private LatLng loc;

    public PlaceItem(String id, String name,String address,String type,String description, String imageUrl, double lat, double longit){
        this.id=id;
        this.name=name;
        this.address=address;
        this.description=description;
        this.imageUrl=imageUrl;
        this.loc=new LatLng(lat,longit);
        this.type=type;
        setIcon(type);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getIcon() {
        return icon;
    }

    public LatLng getLocation() {
        return loc;
    }

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
    }

}
