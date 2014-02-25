package uk.co.stephen_robinson.uni.lufelf.adapters;

import com.google.android.gms.maps.model.LatLng;

import uk.co.stephen_robinson.uni.lufelf.R;

/**
 * @author James
 *         This models an event item in memory
 */

public class EventListItem{

    private String eventName="";
    private int icon;
    private String id="";
    private String creator="";
    private LatLng location;
    private String dateTime="";
    private String description="";

    private static int social= R.drawable.seminar;
    private static int university=R.drawable.university;
    private static int open_day=R.drawable.university;
    private static int private_event=0;
    private static int public_event=0;
    private static int party=R.drawable.party;
    private static int study=R.drawable.seminar_maybe;


    /**
     * Constructor for the eventlistitem
     *
     * @param eventName   event name
     * @param iconID        drawable resource of event
     * @param id          event id
     * @param creator     the id of the person who created the event
     * @param loc         LatLng of the event.
     * @param dateTime    The date time of the event
     * @param description The description of the event.
     */
    public EventListItem(String id,String eventName, int iconID, String creator, LatLng loc, String dateTime, String description) {
        this.eventName = eventName;
        this.icon = iconID;
        this.id = id;
        this.creator = creator;
        this.location = loc;

        this.dateTime =dateTime;
        this.description = description;
    }

    /**
     * get title of the event
     *
     * @return title of the EventListItem
     */


    /**
     * get the icon of the event
     *
     * @return icon resource id of the event
     */
    public int getIcon() {
        return this.icon;
    }

    /**
     * get the id of the event
     *
     * @return the id of the event
     */
    public String getId() {
        return this.id;
    }

    /**
     * get the location of the event
     *
     * @return the location of the event
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * get the creator of the event
     *
     * @return id of the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * get the date and time of event
     *
     * @return the date and time of event
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * get the description of the event
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get the event name
     *
     * @return event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Converts an index from a switch into a compatible string for the database
     * @param selectedIndex the index which the user has selected
     * @return the database compatible string
     */
    public static String convertTypeIntoCompatibleString(int selectedIndex){
        switch (selectedIndex){
            case 0:
                return "social";
            case 1:
                return "university";
            case 2:
                return "open_day";
            case 3:
                return "private";
            case 4:
                return "public";
            case 5:
                return "party";
            case 6:
                return "study";
        }
        return "";
    }

}
