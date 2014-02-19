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
 * Created by James on 31/01/2014.
 */
public class EventItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EventListItem> eventListItems;

    /**
     *
     * @param context
     * @param eventListItems
     */
    public EventItemAdapter(Context context, ArrayList<EventListItem> eventListItems){
        this.context = context;
        this.eventListItems = eventListItems;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return eventListItems.size();
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return eventListItems.get(position);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
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
