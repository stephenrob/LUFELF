package uk.co.stephen_robinson.uni.lufelf.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.stephen_robinson.uni.lufelf.R;

/**
 * @author James
 * adapts an event item to make it compatible with the listview
 */
public class PlaceItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PlaceItem> placeItems;

    /**
     *Constructor for the event item adapter
     * @param context the context of the view
     * @param placeItems the list of place items.
     */
    public PlaceItemAdapter(Context context, ArrayList<PlaceItem> placeItems){
        this.context = context;
        this.placeItems = placeItems;
    }

    /**
     *get the size of the list
     * @return the size of the list
     */
    @Override
    public int getCount() {
        return placeItems.size();
    }

    /**
     * get item at
     * @param position the position of the item
     * @return returns the object
     */
    @Override
    public Object getItem(int position) {
        return placeItems.get(position);
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
            convertView = mInflater.inflate(R.layout.place_listview_layout, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.place_listview_name);
        TextView description = (TextView) convertView.findViewById(R.id.place_listview_description);

        name.setText(placeItems.get(position).getName());
        description.setText(placeItems.get(position).getDescription());

        return convertView;
    }
}
