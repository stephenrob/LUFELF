package uk.co.stephen_robinson.uni.lufelf.adapters;

/**
 * @author James
 * An expandable list view
 */
public class NavDrawerItem {

    String title;
    int icon;

    /**
     * Constructor for the class
     * @param title name of the item
     * @param icon icon of the item
     */
    public NavDrawerItem(String title, int icon){
        this.title=title;
        this.icon=icon;
    }

    /**
     * Gets name of the sublist
     * @return sublist name
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Gets icon of the sublist
     * @return sublist icon
     */
    public int getIcon(){
        return this.icon;
    }
}
