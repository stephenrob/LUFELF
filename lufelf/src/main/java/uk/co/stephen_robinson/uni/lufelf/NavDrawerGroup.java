package uk.co.stephen_robinson.uni.lufelf;

import java.util.ArrayList;

/**
 * Created by James on 09/02/2014.
 */
public class NavDrawerGroup {
    private String name;
    private int icon;
    private ArrayList<NavDrawerItem> items;

    /**
     *
     * @param name
     * @param icon
     * @param items
     */
    public NavDrawerGroup(String name, int icon,ArrayList<NavDrawerItem> items){
        this.name=name;
        this.icon=icon;
        this.items=items;
    }
    public String getName(){
        return this.name;
    }
    public int getIcon(){
        return this.icon;
    }
    public ArrayList<NavDrawerItem> getItems(){
        return this.items;
    }
    public int countItems(){
        return this.items.size();
    }
    public void setItems(ArrayList<NavDrawerItem> items) {
        this.items = items;
    }

}
