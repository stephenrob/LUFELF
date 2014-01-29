package uk.co.stephen_robinson.uni.lufelf;

/**
 * Created by James on 29/01/2014.
 */
public class NavDrawerItem {

    String title;
    int icon;

    public NavDrawerItem(String title, int icon){
        this.title=title;
        this.icon=icon;
    }
    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }
}
