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
import java.util.Arrays;
import java.util.HashSet;

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
                break;

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

    @Override
    public Uri insert(Uri uri, ContentValues values){
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        String base_path = "";

        switch (uriType) {
            case ATTENDEES:
                id = sqlDB.insert(AttendeesTable.TABLE_ATTENDEES, null, values);
                base_path = ATTENDEES_BASE_PATH;
                break;

            case EVENTS:
                id = sqlDB.insert(EventsTable.TABLE_EVENTS, null, values);
                base_path = EVENTS_BASE_PATH;
                break;

            case FRIEND_REQUESTS:
                id = sqlDB.insert(FriendRequestsTable.TABLE_FRIEND_REQUESTS, null, values);
                base_path = FRIEND_REQUESTS_BASE_PATH;
                break;

            case FRIENDS:
                id = sqlDB.insert(FriendsTable.TABLE_FRIENDS, null, values);
                base_path = FRIENDS_BASE_PATH;
                break;

            case MESSAGES:
                id = sqlDB.insert(MessagesTable.TABLE_MESSAGES, null, values);
                base_path = MESSAGES_BASE_PATH;
                break;

            case PLACES:
                id = sqlDB.insert(PlacesTable.TABLE_PLACES, null, values);
                base_path = PLACES_BASE_PATH;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(base_path + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        String id = "";

        switch (uriType) {

            case ATTENDEES:
                rowsDeleted = sqlDB.delete(AttendeesTable.TABLE_ATTENDEES, selection, selectionArgs);
                break;

            case ATTENDEE:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(AttendeesTable.TABLE_ATTENDEES, AttendeesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(AttendeesTable.TABLE_ATTENDEES, AttendeesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case EVENTS:
                rowsDeleted = sqlDB.delete(EventsTable.TABLE_EVENTS, selection, selectionArgs);
                break;

            case EVENT:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(EventsTable.TABLE_EVENTS, EventsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(EventsTable.TABLE_EVENTS, EventsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case FRIEND_REQUESTS:
                rowsDeleted = sqlDB.delete(FriendRequestsTable.TABLE_FRIEND_REQUESTS, selection, selectionArgs);
                break;

            case FRIEND_REQUEST:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FriendRequestsTable.TABLE_FRIEND_REQUESTS, FriendRequestsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(FriendRequestsTable.TABLE_FRIEND_REQUESTS, FriendRequestsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case FRIENDS:
                rowsDeleted = sqlDB.delete(FriendsTable.TABLE_FRIENDS, selection, selectionArgs);
                break;

            case FRIEND:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FriendsTable.TABLE_FRIENDS, FriendsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(FriendsTable.TABLE_FRIENDS, FriendsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case MESSAGES:
                rowsDeleted = sqlDB.delete(MessagesTable.TABLE_MESSAGES, selection, selectionArgs);
                break;

            case MESSAGE:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MessagesTable.TABLE_MESSAGES, MessagesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(MessagesTable.TABLE_MESSAGES, MessagesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case PLACES:
                rowsDeleted = sqlDB.delete(PlacesTable.TABLE_PLACES, selection, selectionArgs);
                break;

            case PLACE:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(PlacesTable.TABLE_PLACES, PlacesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(PlacesTable.TABLE_PLACES, PlacesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        String id = "";

        switch (uriType) {

            case ATTENDEES:
                rowsUpdated = sqlDB.update(AttendeesTable.TABLE_ATTENDEES, values, selection, selectionArgs);
                break;

            case ATTENDEE:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(AttendeesTable.TABLE_ATTENDEES, values, AttendeesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(AttendeesTable.TABLE_ATTENDEES, values, AttendeesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case EVENTS:
                rowsUpdated = sqlDB.update(EventsTable.TABLE_EVENTS, values, selection, selectionArgs);
                break;

            case EVENT:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(EventsTable.TABLE_EVENTS, values, EventsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(EventsTable.TABLE_EVENTS, values, EventsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case FRIEND_REQUESTS:
                rowsUpdated = sqlDB.update(FriendRequestsTable.TABLE_FRIEND_REQUESTS, values, selection, selectionArgs);
                break;

            case FRIEND_REQUEST:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(FriendRequestsTable.TABLE_FRIEND_REQUESTS, values, FriendRequestsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(FriendRequestsTable.TABLE_FRIEND_REQUESTS, values, FriendRequestsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case FRIENDS:
                rowsUpdated = sqlDB.update(FriendsTable.TABLE_FRIENDS, values, selection, selectionArgs);
                break;

            case FRIEND:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(FriendsTable.TABLE_FRIENDS, values, FriendsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(FriendsTable.TABLE_FRIENDS, values, FriendsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case MESSAGES:
                rowsUpdated = sqlDB.update(MessagesTable.TABLE_MESSAGES, values, selection, selectionArgs);
                break;

            case MESSAGE:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(MessagesTable.TABLE_MESSAGES, values, MessagesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(MessagesTable.TABLE_MESSAGES, values, MessagesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            case PLACES:
                rowsUpdated = sqlDB.update(PlacesTable.TABLE_PLACES, values, selection, selectionArgs);
                break;

            case PLACE:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(PlacesTable.TABLE_PLACES, values, PlacesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(PlacesTable.TABLE_PLACES, values, PlacesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection){
        String[] available = {
                AttendeesTable.COLUMN_DATE,
                AttendeesTable.COLUMN_EVENT,
                AttendeesTable.COLUMN_ID,
                AttendeesTable.COLUMN_NAME,
                EventsTable.COLUMN_ATTENDING,
                EventsTable.COLUMN_DATE,
                EventsTable.COLUMN_ID,
                EventsTable.COLUMN_LOCATION_ADDRESS,
                EventsTable.COLUMN_LOCATION_LAT,
                EventsTable.COLUMN_LOCATION_LON,
                EventsTable.COLUMN_LOCATION_NAME,
                EventsTable.COLUMN_NAME,
                EventsTable.COLUMN_ORGANISER_EMAIL,
                EventsTable.COLUMN_ORGANISER_NAME,
                FriendRequestsTable.COLUMN_FRIEND_ID,
                FriendRequestsTable.COLUMN_ID,
                FriendRequestsTable.COLUMN_STATUS,
                FriendsTable.COLUMN_DELETE,
                FriendsTable.COLUMN_ID,
                FriendsTable.COLUMN_LAT,
                FriendsTable.COLUMN_LOCATION_STATUS,
                FriendsTable.COLUMN_LON,
                FriendsTable.COLUMN_NAME,
                FriendsTable.COLUMN_USERNAME,
                MessagesTable.COLUMN_CONTENT,
                MessagesTable.COLUMN_FRIEND,
                MessagesTable.COLUMN_ID,
                MessagesTable.COLUMN_TYPE,
                PlacesTable.COLUMN_ADDRESS,
                PlacesTable.COLUMN_ID,
                PlacesTable.COLUMN_LAT,
                PlacesTable.COLUMN_LON,
                PlacesTable.COLUMN_NAME,
                PlacesTable.COLUMN_USER
            };

        if (projection != null){
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            if(!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
