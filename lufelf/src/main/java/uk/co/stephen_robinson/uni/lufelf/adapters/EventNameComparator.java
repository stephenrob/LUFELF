package uk.co.stephen_robinson.uni.lufelf.adapters;

import java.util.Comparator;

/**
 * Created by James on 25/02/2014.
 */
public class EventNameComparator implements Comparator<EventListItem>{
    @Override
    public int compare(EventListItem item1, EventListItem item2) {
        return item1.getEventName().compareTo(item2.getEventName());
    }
}
