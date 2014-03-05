package uk.co.stephen_robinson.uni.lufelf.adapters;

/**
 * @author James
 * holds details about a user
 */
public class UserItem {
    private String name="";
    private String description="";
    private String libraryNo="";
    private String emailAdd="";
    private int id;
    private boolean friend=false;

    /**
     * constructor for a useritem
     * @param name
     * @param description
     * @param libraryNo
     * @param emailAdd
     * @param id
     * @param friend
     */
    public UserItem(String name, String description, String libraryNo, String emailAdd, int id,boolean friend) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.libraryNo = libraryNo;
        this.emailAdd = emailAdd;
        this.friend=friend;
    }

    /**
     * returns the id of a user
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * returns the description of a user
     * @return the users' description
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the library number
     * @return library number
     */
    public String getLibraryNo() {
        return libraryNo;
    }

    /**
     * returns the name of a user
     * @return the name of a user
     */
    public String getName() {

        return name;
    }

    /**
     * returns the username of a user
     * @return the username of a user
     */
    public String getEmailAdd() {
        return emailAdd;
    }

    /**
     * gets the isfriend boolean
     * @return a boolean. If friends, then it's true
     * if they're not friends they are false.
     */
    public boolean isFriend(){
        return friend;
    }

    /**
     * returns a blank user item
     * @return a blank user item.
     */
    public static UserItem getBlankResult(){
        return new UserItem("","None","","",0,false);
    }
}
