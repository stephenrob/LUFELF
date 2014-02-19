package uk.co.stephen_robinson.uni.lufelf;

import java.util.ArrayList;

/**
 * @author James
 * A group containing list draw items for the expandable listview
 */
public class NavDrawerGroup {
    private String name;
    private int icon;
    private ArrayList<NavDrawerItem> items;

    /**
     * Constructor for the class
     * @param name name of the group
     * @param icon icon set for the group
     * @param items sublists for the group
     */
    public NavDrawerGroup(String name, int icon,ArrayList<NavDrawerItem> items){
        this.name=name;
        this.icon=icon;
        this.items=items;
    }

    /**
     * Gets the name of the group
     * @return group name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the icon of the group
     * @return group icon
     */
    public int getIcon(){
        return this.icon;
    }

    /**
     * Gets an ArrayList of the sublists
     * @return group sublist
     */
    public ArrayList<NavDrawerItem> getItems(){
        return this.items;
    }

    /**
     * Gets the number of subitems in the group
     * @return the number of subitems
     */
    public int countItems(){
        return this.items.size();
    }

    /**
     * Sets items for the sublist in the group
     * @param items the items in the sublist
     */
    public void setItems(ArrayList<NavDrawerItem> items) {
        this.items = items;
    }

}
