package uk.co.stephen_robinson.uni.lufelf.api.v1.xml;

/**
 * Created by Stephen on 22/02/2014.
 */

/**
 * @author stephen
 *
 * Event User class, extends generic user class and stores information regarding an event user
 */
public class EventUser extends User {
    protected String date_accepted = "";

    public static final String DATE_ACCEPTED = "date_accepted";

    /**
     * getter method for date accepted
     * @return string with date user accepted event invite
     */
    public String getDate_accepted() {
        return date_accepted;
    }
}
