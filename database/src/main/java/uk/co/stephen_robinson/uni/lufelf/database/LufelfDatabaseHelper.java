package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 29/01/2014.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Stephen
 * Creates and upgrades the whole lufelf database and stores name and versioning information
 */

public class LufelfDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lufelf.db";
    private static final int DATABASE_VERSION = 1;

    public LufelfDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
     * @param database Database to create the table in
     */
    public void onCreate(SQLiteDatabase database) {
        EventsTable.onCreate(database);
        AttendeesTable.onCreate(database);
        FriendRequestsTable.onCreate(database);
        FriendsTable.onCreate(database);
        MessagesTable.onCreate(database);
    }

    /**
     *
     * @param database Database to create the table in
     * @param oldVersion Old version number of the database, used for logging upgrade
     * @param newVersion New version number of the database, used for logging upgrade
     */
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        EventsTable.onUpgrade(database, oldVersion, newVersion);
        AttendeesTable.onUpgrade(database, oldVersion, newVersion);
        FriendRequestsTable.onUpgrade(database, oldVersion, newVersion);
        FriendsTable.onUpgrade(database, oldVersion, newVersion);
        MessagesTable.onUpgrade(database, oldVersion, newVersion);
    }

}
