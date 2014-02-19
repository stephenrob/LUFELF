package uk.co.stephen_robinson.uni.lufelf;

import com.google.android.gms.maps.model.LatLng;

/**
 *@author James
 * @date 11/02/2014
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
         *
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
         *
         * @return
         */
        public String getTitle(){
            return this.title;
        }

        /**
         *
         * @return
         */
        public int getIcon(){
            return this.icon;
        }

        /**
         *
         * @return
         */
        public String getId(){
            return this.id;
        }

        /**
         *
         * @return
         */
        public LatLng getLocation() {
            return location;
        }

        /**
         *
         * @return
         */
        public String getCreator() {
            return creator;
        }

        /**
         *
         * @return
         */
        public String getDateTime() {
            return dateTime;
        }

        /**
         *
         * @return
         */
        public String getDescription() {
            return description;
        }

        /**
         *
         * @return
         */
        public String getEventName() {
            return eventName;
        }
}
