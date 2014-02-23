package uk.co.stephen_robinson.uni.lufelf.adapters;

/**
 * Created by James on 23/02/2014.
 */
public class UserItem {
    private String name="";
    private String description="";
    private String libraryNo="";
    private String emailAdd="";
    private int id;


    public UserItem(String name, String description, String libraryNo, String emailAdd, int id) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.libraryNo = libraryNo;
        this.emailAdd = emailAdd;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLibraryNo() {
        return libraryNo;
    }
    public String getName() {

        return name;
    }
    public String getEmailAdd() {
        return emailAdd;
    }
}
