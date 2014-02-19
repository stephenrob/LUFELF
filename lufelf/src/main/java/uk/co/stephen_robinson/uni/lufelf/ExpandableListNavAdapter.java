package uk.co.stephen_robinson.uni.lufelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author James
 * adapts nav drawer groups to make it compatible with the ExpandableList
 */
public class ExpandableListNavAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<NavDrawerGroup> groups;

    /**
     * create the list adapter
     * @param c the context of the fragment
     * @param group the group of navdrawer items
     */
    public ExpandableListNavAdapter(Context c, ArrayList<NavDrawerGroup> group){
        this.context=c;
        this.groups=group;
    }

    /**
     * add an item to the listview
     * @param item the nav drawer item
     * @param group the group of the nav drawer item
     */
    public void addItem(NavDrawerItem item,NavDrawerGroup group){
        if(!groups.contains(group))
            groups.add(group);

        int index=groups.indexOf(group);

        ArrayList<NavDrawerItem> child=groups.get(index).getItems();

        child.add(item);
        groups.get(index).setItems(child);

    }

    /**
     * get the child of the group
     * @param groupPosition group position
     * @param childPosition child position
     * @return returns the child
     */
    public Object getChild(int groupPosition,int childPosition){
        ArrayList<NavDrawerItem> childList=groups.get(groupPosition).getItems();
        return childList.get(childPosition);
    }

    /**
     * get the id of the child
     * @param groupPosition group position
     * @param childPosition child position
     * @return the id of the child
     */
    public long getChildId(int groupPosition,int childPosition){
        return childPosition;
    }

    /**
     * get the view of the child
     * @param groupPosition group position
     * @param childPosition child position
     * @param last is the last?
     * @param view the view to change
     * @param parent the parent of the view
     * @return the modified view
     */
    public View getChildView(int groupPosition,int childPosition,boolean last,View view,ViewGroup parent){
        NavDrawerItem item = (NavDrawerItem)getChild(groupPosition,childPosition);

        if(view==null){
            LayoutInflater inflator=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflator.inflate(R.layout.nav_drawer_layout,null);
        }
        ImageView im=(ImageView)view.findViewById(R.id.nav_drawer_icon);
        im.setImageResource(item.getIcon());

        TextView text=(TextView)view.findViewById(R.id.nav_drawer_title);
        text.setText(item.getTitle());

        return view;
    }

    /**
     * get the number of children
     * @param groupPosition group position
     * @return the number of items
     */
    public int getChildrenCount(int groupPosition){
        return groups.get(groupPosition).getItems().size();
    }

    /**
     * get the group
     * @param groupPosition
     * @return
     */
    public Object getGroup(int groupPosition){
        return groups.get(groupPosition);
    }
    public int getGroupCount(){
        return groups.size();
    }
    public long getGroupId(int groupPosition){
        return groupPosition;
    }
    public View getGroupView(int groupPosition,boolean last,View view,ViewGroup parent){
        NavDrawerGroup item = (NavDrawerGroup)getGroup(groupPosition);

        if(view==null){
            LayoutInflater inflator=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflator.inflate(R.layout.nav_group_layout,null);
        }
        ImageView im=(ImageView)view.findViewById(R.id.nav_drawer_icon);
        im.setImageResource(item.getIcon());

        TextView text=(TextView)view.findViewById(R.id.nav_drawer_title);
        text.setText(item.getName());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

}
