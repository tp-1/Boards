package com.example.i5.boards.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.RestrictTo;
import android.support.annotation.WorkerThread;

import com.example.i5.boards.ALog;

import java.util.Arrays;

/**
 * Class used for performing CRUD operations on tables in the database.
 */
public class DBOperations {
    final static private String TAG = DBOperations.class.getSimpleName();

    private static SQLiteOpenHelper sDbHelper = null;

    /**
     * Create or save {@link DBHelper} instance to be used by operations performed with this class.
     * @param context Context used to create database. Will be used to fetch an application context
     */
    public static void setupDatabase(Context context) {
        ALog.d(TAG, ALog.DATA, "Setting up database");
        sDbHelper = DBHelper.getInstance(context);
    }

    /**
     * Call instead of {@link #setupDatabase(Context)} to setup fake database helper for tests.
     * Only use for tests that need a non-mock database, but which one - it's not important.
     */
    @RestrictTo(RestrictTo.Scope.TESTS)
    /* package */ static void setupDatabaseForTests(SQLiteOpenHelper dbHelper) {
        sDbHelper = dbHelper;
    }

    /**
     * Create and/or open a database that will be used for reading and writing.
     * @see DBHelper#getWritableDatabase()
     */
    @WorkerThread
    private static SQLiteDatabase getDatabase() {
        if (sDbHelper == null) {
            throw new IllegalStateException("Trying to get database, " +
                    "but DBHelper is not created yet");
        }
        return sDbHelper.getWritableDatabase();
    }

    public final int BOARD_ALL_PROJECTION_ID = 0; //FIXME this is ugly and possibly doesn't belong here
    public final int BOARD_ALL_PROJECTION_NAME = 1;
    public final int BOARD_ALL_PROJECTION_START_TIME = 2;

    /**
     * Arguments default to {@code null}
     * @see #query(String, String[], String, String[])
     */
    @WorkerThread
    public static Cursor query(String tableName) {
        return query(tableName, null, null, null);
    }

    /**
     * Find the row with given id in a table with the given name
     */
    @WorkerThread
    public static Cursor query(String tableName, long id) {
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = new String[]{Long.toString(id)};
        return query(tableName, null, selection, selectionArgs);
    }

    /**
     * Query the given table, returning a Cursor over the result set.
     */
    @WorkerThread
    public static Cursor query(String tableName, String[] projection, String selection, String[] args) {
        ALog.d(TAG, ALog.DATA, "Querying database, table " + tableName);
        // FIXME: 15-Jul-17 Arrays.toString() called for every query!
        ALog.v(TAG, ALog.DATA, "Table name = %s\nProjection = %s\nSelection = %s\n" +
                        "Selection arguments = %s", tableName,
                Arrays.toString(projection), selection, Arrays.toString(args));
        return getDatabase().query(tableName, projection, selection, args, null, null, null);
    }

    /**
     * Insert the row in the db
     * @param tableName to insert the row into
     * @param cv Content values to save to the db
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    @WorkerThread
    public static long save(String tableName, ContentValues cv) {
        ALog.d(TAG, ALog.DATA, "Saving values to database, table " + tableName);
        ALog.v(TAG, ALog.DATA, "Table = %s\nValues = %s", tableName, cv);
        return getDatabase().insert(tableName, null, cv);
    }
}