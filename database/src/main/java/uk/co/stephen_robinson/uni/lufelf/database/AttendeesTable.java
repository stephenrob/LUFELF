package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 29/01/2014.
 */

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Stephen
 * Creates and upgrades the attendees table in the local database.
 */

public class AttendeesTable {

    // Table Specification

    public static final String TABLE_ATTENDEES = "attendees";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";

    private static final String CREATE_TABLE = "create table "
            + TABLE_ATTENDEES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_EVENT + " integer not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DATE + " text not null"
            + ");";

    /**
     *
     * @param database Database to create the table in
     */
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
        Log.w(AttendeesTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDEES);
        onCreate(database);
    }

}
