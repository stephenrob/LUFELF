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
public class FriendItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FriendItem> friendItems;

    /**
     *Constructor for the event item adapter
     * @param context the context of the view
     * @param friendItems the list of user items.
     */
    public FriendItemAdapter(Context context, ArrayList<FriendItem> friendItems){
        this.context = context;
        this.friendItems = friendItems;
    }

    /**
     *get the size of the list
     * @return the size of the list
     */
    @Override
    public int getCount() {
        return friendItems.size();
    }

    /**
     * get item at
     * @param position the position of the item
     * @return returns the object
     */
    @Override
    public Object getItem(int position) {
        return friendItems.get(position);
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
            convertView = mInflater.inflate(R.layout.user_listview_layout, null);
        }

        TextView username = (TextView) convertView.findViewById(R.id.user_listview_username);
        TextView name = (TextView) convertView.findViewById(R.id.user_listview_name);
        TextView description = (TextView) convertView.findViewById(R.id.user_listview_description);

        username.setText(friendItems.get(position).getUsername());
        name.setText(friendItems.get(position).getName());
        description.setText("");

        return convertView;
    }
}
