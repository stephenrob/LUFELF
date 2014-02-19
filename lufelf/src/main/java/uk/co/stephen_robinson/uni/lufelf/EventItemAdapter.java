package uk.co.stephen_robinson.uni.lufelf;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author James
 * adapts an event item to make it compatible with the listview
 */
public class EventItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EventListItem> eventListItems;

    /**
     *Constructor for the event item adapter
     * @param context the context of the view
     * @param eventListItems the list of event items.
     */
    public EventItemAdapter(Context context, ArrayList<EventListItem> eventListItems){
        this.context = context;
        this.eventListItems = eventListItems;
    }

    /**
     *get the size of the list
     * @return the size of the list
     */
    @Override
    public int getCount() {
        return eventListItems.size();
    }

    /**
     * get item at
     * @param position the position of the item
     * @return returns the object
     */
    @Override
    public Object getItem(int position) {
        return eventListItems.get(position);
    }

    /**
     * get the id of an item
     * @param position the position of the item
     * @return the position of the item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *gets the view of the item for the list
     * @param position the position of the item
     * @param convertView the view to convert
     * @param parent the parent of the item
     * @return view of the list item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.event_listview_layout, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.event_item_icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.event_item_text);


        imgIcon.setImageResource(eventListItems.get(position).getIcon());
        txtTitle.setText(eventListItems.get(position).getTitle());

        return convertView;
    }
}
