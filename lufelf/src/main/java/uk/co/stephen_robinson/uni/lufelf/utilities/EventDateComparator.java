package uk.co.stephen_robinson.uni.lufelf.utilities;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import uk.co.stephen_robinson.uni.lufelf.adapters.EventListItem;

/**
 * Created by James on 25/02/2014.
 */
public class EventDateComparator implements Comparator<EventListItem> {
    @Override
    public int compare(EventListItem item1, EventListItem item2) {
        String date1 = item1.getDateTime().substring(0,10);
        String date2 = item2.getDateTime().substring(0,10);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateFormat1=null;
        Date dateFormat2=null;
        try{
            dateFormat1 = formatter.parse(date1);
            dateFormat2 = formatter.parse(date2);
        }catch (Exception e){
            Log.e("date format exception",Log.getStackTraceString(e));
        }
        if(dateFormat1.before(dateFormat2)){
            return -1;
        }
        if(dateFormat1.after(dateFormat2)){
            return 1;
        }
        if(dateFormat1.equals(dateFormat2)){
            return 0;
        }
        return date1.compareTo(date2);
    }
}
