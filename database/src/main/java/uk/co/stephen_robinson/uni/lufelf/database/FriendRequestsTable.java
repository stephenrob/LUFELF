package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 29/01/2014.
 */

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Stephen
 * Creates and upgrades the friend requests table in the local database.
 */

public class FriendRequestsTable {

    public static final String TABLE_FRIEND_REQUESTS = "friend_requests";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FRIEND_ID = "friend_id";
    public static final String COLUMN_STATUS = "status";

    private static final String CREATE_TABLE = "create table "
            + TABLE_FRIEND_REQUESTS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_FRIEND_ID + " integer not null, "
            + COLUMN_STATUS + " integer no null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    /**
     *
     * @param database Database to create the table in
     * @param oldVersion Old version number of the database/table, used for logging upgrade
     * @param newVersion New version number of the database/table, used for logging upgrade
     */
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(FriendRequestsTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND_REQUESTS);
        onCreate(database);
    }

}
