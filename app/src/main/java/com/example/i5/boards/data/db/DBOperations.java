package com.example.i5.boards.data.db;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.i5.boards.ALog;

import java.util.Arrays;

/**
 * Singleton class used for performing CRUD operations on tables in the database.
 */
public class DBOperations {
    final static private String TAG = DBOperations.class.getSimpleName();

    /**
     * Used for creation of {@link DBHelper}.
     * This is so ugly I don't know how I'm gonna live with myself
     */
    private static Application mAppContext;
    private SQLiteDatabase mDb;

    private static DBOperations sInstance;

    // FIXME: 14-Jul-17 DATABASE ACCESS, MOVE FROM UI THREAD
    private DBOperations(Application context) {
        DBHelper dbHelper = new DBHelper(context);
        mDb = dbHelper.getWritableDatabase();

        ALog.d(TAG, ALog.DATA, "Creating DBOperations singleton");
    }

    /**
     * {@link DBOperations} is a singleton. Get instance.
     * <br/>
     * <b>NOTE: Do not call before the first call of
     * {@link #setDatabaseContext(Application)}</b>
     * What a mess.
     */
    public static DBOperations getInstance() {
        if (sInstance == null) {
            if (mAppContext == null) {
                ALog.e(TAG, ALog.DATA, "Creating a DBOperations object without an application context");
                throw new IllegalArgumentException("App context for creating a db is null");
            }
            sInstance = new DBOperations(mAppContext);
        }
        return sInstance;
    }

    /**
     * Set context to be used for creation of {@link DBHelper}
     * @param context <b>Application</b> context
     */
    public static void setDatabaseContext(Application context) {
        mAppContext = context;
        ALog.d(TAG, ALog.DATA, "Setting database context");
    }

    public final int BOARD_ALL_PROJECTION_ID = 0; //FIXME this is ugly and possibly doesn't belong here
    public final int BOARD_ALL_PROJECTION_NAME = 1;
    public final int BOARD_ALL_PROJECTION_START_TIME = 2;

    /**
     * Arguments default to {@code null}
     * @see #query(String, String[], String, String[])
     */
    public Cursor query(String tableName) {
        return query(tableName, null, null, null);
    }

    /**
     * Find the row with given id in a table with the given name
     */
    public Cursor query(String tableName, long id) {
        // FIXME: 10-Jul-17 omg, you have an inner class called ColumnNamesUniversal
        String selection = TableInfos.ColumnNamesUniversal.ID + " = ?";
        String[] selectionArgs = new String[]{Long.toString(id)};
        return query(tableName, null, selection, selectionArgs);
    }

    /**
     * Query the given table, returning a Cursor over the result set.
     */
    public Cursor query(String tableName, String[] projection, String selection, String[] args) {
        ALog.d(TAG, ALog.DATA, "Querying database, table " + tableName);
        // FIXME: 15-Jul-17 Arrays.toString() called for every query!
        ALog.v(TAG, ALog.DATA, "Table name = %s\nProjection = %s\nSelection = %s\n" +
                        "Selection arguments = %s", tableName,
                Arrays.toString(projection), selection, Arrays.toString(args));
        return mDb.query(tableName, projection, selection, args, null, null, null);
    }

    /**
     * Insert the row in the db
     * @param tableName to insert the row into
     * @param cv Content values to save to the db
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long save(String tableName, ContentValues cv) {
        ALog.d(TAG, ALog.DATA, "Saving values to database, table " + tableName);
        ALog.v(TAG, ALog.DATA, "Table = %s\nValues = %s", tableName, cv);
        return mDb.insert(tableName, null, cv);
    }
}