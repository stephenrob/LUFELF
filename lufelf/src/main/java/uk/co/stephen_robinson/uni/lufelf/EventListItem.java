package uk.co.stephen_robinson.uni.lufelf;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by James on 11/02/2014.
 */
public class EventListItem {

    private String title;
    private int icon;
    private String id;
    private String eventName;
    private String creator;
    private LatLng location;
    private String dateTime;
    private String description;

    public EventListItem(String title, int icon,String id, String eventName, String creator,LatLng loc,String dateTime,String description){
        this.title=title;
        this.icon=icon;
        this.id=id;
        this.eventName=eventName;
        this.creator=creator;
        this.location=loc;
        this.dateTime=dateTime;
        this.description=description;
    }
    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public String getId(){
        return this.id;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getCreator() {
        return creator;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getEventName() {
        return eventName;
    }
}
