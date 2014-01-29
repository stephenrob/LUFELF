package uk.co.stephen_robinson.uni.lufelf.contentprovider;

// Import Android Packages

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

// Import Database Classes
import uk.co.stephen_robinson.uni.lufelf.database.LufelfDatabaseHelper;
import uk.co.stephen_robinson.uni.lufelf.database.AttendeesTable;
import uk.co.stephen_robinson.uni.lufelf.database.EventsTable;
import uk.co.stephen_robinson.uni.lufelf.database.FriendRequestsTable;
import uk.co.stephen_robinson.uni.lufelf.database.FriendsTable;
import uk.co.stephen_robinson.uni.lufelf.database.MessagesTable;
import uk.co.stephen_robinson.uni.lufelf.database.PlacesTable;

/**
 * Created by Stephen on 29/01/2014.
 */
public class LufelfContentProvider extends ContentProvider {

    private LufelfDatabaseHelper database;

    private static final int ATTENDEES = 1;
    private static final int ATTENDEE = 2;
    private static final int EVENTS = 11;
    private static final int EVENT = 12;
    private static final int FRIEND_REQUESTS = 21;
    private static final int FRIEND_REQUEST = 22;
    private static final int FRIENDS = 31;
    private static final int FRIEND = 32;
    private static final int MESSAGES = 41;
    private static final int MESSAGE = 42;
    private static final int PLACES = 51;
    private static final int PLACE = 52;

    private static final String AUTHORITY = "uk.co.stephen_robinson.uni.lufelf.contentprovider";

    private static final String ATTENDEES_BASE_PATH = "attendees";
    private static final String EVENTS_BASE_PATH = "events";
    private static final String FRIEND_REQUESTS_BASE_PATH = "friend_requests";
    private static final String FRIENDS_BASE_PATH = "friends";
    private static final String MESSAGES_BASE_PATH = "messages";
    private static final String PLACES_BASE_PATH = "places";

    public static final Uri ATTENDEES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ATTENDEES_BASE_PATH);
    public static final Uri EVENTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + EVENTS_BASE_PATH);
    public static final Uri FRIEND_REQUESTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + FRIEND_REQUESTS_BASE_PATH);
    public static final Uri FRIENDS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + FRIENDS_BASE_PATH);
    public static final Uri MESSAGES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MESSAGES_BASE_PATH);
    public static final Uri PLACES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PLACES_BASE_PATH);

    public static final String ATTENDEES_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/attendees";
    public static final String ATTENDEES_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/attendee";

    public static final String EVENTS_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/events";
    public static final String EVENTS_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/event";

    public static final String FRIEND_REQUESTS_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/friend_requests";
    public static final String FRIEND_REQUESTS_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/friend_request";

    public static final String FRIENDS_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/friends";
    public static final String FRIENDS_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/friend";

    public static final String MESSAGES_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/messages";
    public static final String MESSAGES_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/message";

    public static final String PLACES_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/places";
    public static final String PLACES_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/place";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, ATTENDEES_BASE_PATH, ATTENDEES);
        sURIMatcher.addURI(AUTHORITY, ATTENDEES_BASE_PATH + "/#", ATTENDEE);
        sURIMatcher.addURI(AUTHORITY, EVENTS_BASE_PATH, EVENTS);
        sURIMatcher.addURI(AUTHORITY, EVENTS_BASE_PATH + "/#", EVENT);
        sURIMatcher.addURI(AUTHORITY, FRIEND_REQUESTS_BASE_PATH, FRIEND_REQUESTS);
        sURIMatcher.addURI(AUTHORITY, FRIEND_REQUESTS_BASE_PATH + "/#", FRIEND_REQUEST);
        sURIMatcher.addURI(AUTHORITY, FRIENDS_BASE_PATH, FRIENDS);
        sURIMatcher.addURI(AUTHORITY, FRIENDS_BASE_PATH + "/#", FRIEND);
        sURIMatcher.addURI(AUTHORITY, MESSAGES_BASE_PATH, MESSAGES);
        sURIMatcher.addURI(AUTHORITY, MESSAGES_BASE_PATH + "/#", MESSAGE);
        sURIMatcher.addURI(AUTHORITY, PLACES_BASE_PATH, PLACES);
        sURIMatcher.addURI(AUTHORITY, PLACES_BASE_PATH + "/#", PLACE);
    }

    @Override
    public boolean onCreate() {
        database = new LufelfDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){

        // Use query builder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check caller hasn't requested invalid column
        checkColumns(projection);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {

            case ATTENDEES:
                queryBuilder.setTables(AttendeesTable.TABLE_ATTENDEES);
                break;

            case ATTENDEE:
                queryBuilder.setTables(AttendeesTable.TABLE_ATTENDEES);
                queryBuilder.appendWhere(AttendeesTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;

            case EVENTS:
                queryBuilder.setTables(EventsTable.TABLE_EVENTS);
                break;

            case EVENT:
                queryBuilder.setTables(EventsTable.TABLE_EVENTS);
                queryBuilder.appendWhere(EventsTable.COLUMN_ID + "=" + uri.getLastPathSegment());

            case FRIEND_REQUESTS:
                queryBuilder.setTables(FriendRequestsTable.TABLE_FRIEND_REQUESTS);
                break;

            case FRIEND_REQUEST:
                queryBuilder.setTables(FriendRequestsTable.TABLE_FRIEND_REQUESTS);
                queryBuilder.appendWhere(FriendRequestsTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;

            case FRIENDS:
                queryBuilder.setTables(FriendsTable.TABLE_FRIENDS);
                break;

            case FRIEND:
                queryBuilder.setTables(FriendsTable.TABLE_FRIENDS);
                queryBuilder.appendWhere(FriendsTable.COLUMN_ID + "=" + uri.getLastPathSegment());

            case MESSAGES:
                queryBuilder.setTables(MessagesTable.TABLE_MESSAGES);
                break;

            case MESSAGE:
                queryBuilder.setTables(MessagesTable.TABLE_MESSAGES);
                queryBuilder.appendWhere(MessagesTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;

            case PLACES:
                queryBuilder.setTables(PlacesTable.TABLE_PLACES);
                break;

            case PLACE:
                queryBuilder.setTables(PlacesTable.TABLE_PLACES);
                queryBuilder.appendWhere(PlacesTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}
