package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 28/01/2014.
 */

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Stephen
 * Creates and upgrades the places table in the local database.
 */

public class PlacesTable {

    // Table Specification

    public static final String TABLE_PLACES = "places";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_USER ="user_id";

    private static final String CREATE_TABLE = "create table "
        + TABLE_PLACES
        + "("
        + COLUMN_ID + " integer primary key autoincrement, "
        + COLUMN_NAME + " text not null, "
        + COLUMN_ADDRESS + " text not null, "
        + COLUMN_LAT + " real not null, "
        + COLUMN_LON + " real not null, "
        + COLUMN_USER + " integer not null, "
        + ");";

    /**
     *
     * @param database Database to create the table in
     */
    public static void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_TABLE);
    }

    /**
     *
     * @param database Database to create the table in
     * @param oldVersion Old version number of the database/table, used for logging upgrade
     * @param newVersion New version number of the database/table, used for logging upgrade
     */
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(PlacesTable.class.getName(), "Upgrading database from version "
            + oldVersion + " to " + newVersion
            + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(database);
    }

}
