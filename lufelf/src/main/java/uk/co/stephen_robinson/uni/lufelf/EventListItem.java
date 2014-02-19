package uk.co.stephen_robinson.uni.lufelf;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author James
 *This models an event item in memory
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

        /**
         *Constructor for the eventlistitem
         * @param title Title of the event
         * @param icon  drawable resource of event
         * @param id    event id
         * @param eventName event name
         * @param creator   the id of the person who created the event
         * @param loc   LatLng of the event.
         * @param dateTime  The date time of the event
         * @param description   The description of the event.
         */
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

        /**
         * get title of the event
         * @return title of the EventListItem
         */
        public String getTitle(){
            return this.title;
        }

        /**
         * get the icon of the event
         * @return icon resource id of the event
         */
        public int getIcon(){
            return this.icon;
        }

        /**
         *get the id of the event
         * @return the id of the event
         */
        public String getId(){
            return this.id;
        }

        /**
         * get the location of the event
         * @return the location of the event
         */
        public LatLng getLocation() {
            return location;
        }

        /**
         * get the creator of the event
         * @return id of the creator
         */
        public String getCreator() {
            return creator;
        }

        /**
         * get the date and time of event
         * @return the date and time of event
         */
        public String getDateTime() {
            return dateTime;
        }

        /**
         * get the description of the event
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * get the event name
         * @return event name
         */
        public String getEventName() {
            return eventName;
        }
}
