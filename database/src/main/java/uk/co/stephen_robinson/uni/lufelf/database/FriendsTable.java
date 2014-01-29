package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 29/01/2014.
 */

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FriendsTable {

    public static final String TABLE_FRIENDS = "friends";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_LOCATION_STATUS = "location_status";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_DELETE = "delete";

    private static final String CREATE_TABLE = "create table "
            + TABLE_FRIENDS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_USERNAME + " text not null, "
            + COLUMN_LOCATION_STATUS + " integer not null, "
            + COLUMN_LAT + " real, "
            + COLUMN_LON + " real, "
            + COLUMN_DELETE + " integer"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(FriendsTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        onCreate(database);
    }

}
