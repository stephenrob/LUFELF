package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 29/01/2014.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LufelfDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lufelf.db";
    private static final int DATABASE_VERSION = 1;

    public LufelfDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        EventsTable.onCreate(database);
        AttendeesTable.onCreate(database);
        FriendRequestsTable.onCreate(database);
        FriendsTable.onCreate(database);
        MessagesTable.onCreate(database);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        EventsTable.onUpgrade(database, oldVersion, newVersion);
        AttendeesTable.onUpgrade(database, oldVersion, newVersion);
        FriendRequestsTable.onUpgrade(database, oldVersion, newVersion);
        FriendsTable.onUpgrade(database, oldVersion, newVersion);
        MessagesTable.onUpgrade(database, oldVersion, newVersion);
    }

}
