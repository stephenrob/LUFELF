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
 * Created by James on 09/02/2014.
 */
public class ExpandableListNavAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<NavDrawerGroup> groups;

    public ExpandableListNavAdapter(Context c, ArrayList<NavDrawerGroup> group){
        this.context=c;
        this.groups=group;
    }
    public void addItem(NavDrawerItem item,NavDrawerGroup group){
        if(!groups.contains(group))
            groups.add(group);

        int index=groups.indexOf(group);

        ArrayList<NavDrawerItem> child=groups.get(index).getItems();

        child.add(item);
        groups.get(index).setItems(child);

    }
    public Object getChild(int groupPosition,int childPosition){
        ArrayList<NavDrawerItem> childList=groups.get(groupPosition).getItems();
        return childList.get(childPosition);
    }
    public long getChildId(int groupPosition,int childPosition){
        return childPosition;
    }
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
    public int getChildrenCount(int groupPosition){
        return groups.get(groupPosition).getItems().size();
    }
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
