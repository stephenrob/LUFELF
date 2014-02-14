package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 29/01/2014.
 */

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EventsTable {

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION_NAME = "location_name";
    public static final String COLUMN_LOCATION_ADDRESS = "location_address";
    public static final String COLUMN_LOCATION_LAT = "location_lat";
    public static final String COLUMN_LOCATION_LON = "location_lon";
    public static final String COLUMN_ORGANISER_NAME = "organiser_name";
    public static final String COLUMN_ORGANISER_EMAIL = "organiser_email";
    public static final String COLUMN_ATTENDING = "attending";

    private static final String CREATE_TABLE = "create table "
            + TABLE_EVENTS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DATE + " text not null, "
            + COLUMN_LOCATION_NAME + " text not null, "
            + COLUMN_LOCATION_ADDRESS + " text not null, "
            + COLUMN_LOCATION_LAT + " real not null, "
            + COLUMN_LOCATION_LON + " real not null, "
            + COLUMN_ORGANISER_NAME + " text not null, "
            + COLUMN_ORGANISER_EMAIL + " text not null, "
            + COLUMN_ATTENDING + " integer"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(EventsTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(database);
    }

}
