package uk.co.stephen_robinson.uni.lufelf.database;

/**
 * Created by Stephen on 29/01/2014.
 */

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MessagesTable {

    public static final String TABLE_MESSAGES = "messages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FRIEND = "friend_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TYPE = "type";

    private static final String CREATE_TABLE = "create table "
            + TABLE_MESSAGES
            + "("
            + COLUMN_ID + " integer primary key auto increment, "
            + COLUMN_FRIEND + " integer not null, "
            + COLUMN_CONTENT + " text not null, "
            + COLUMN_TYPE + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(MessagesTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(database);
    }

}
