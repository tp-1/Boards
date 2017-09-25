package com.example.i5.boards.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.RestrictTo;

import com.example.i5.boards.ALog;

import java.util.ArrayList;

/**
 * A helper object to create, open, and/or manage a database.
 * Singleton.
 *
 * The database is not actually created or opened until one
 * of getWritableDatabase() or getReadableDatabase() is called.
 */
class DBHelper extends SQLiteOpenHelper {
    final static private String TAG = DBHelper.class.getSimpleName();
    static private DBHelper sInstance = null;

    final static private String DATABASE_NAME = "boards.db";
    final static private int DATABASE_VERSION = 1;

    private DBHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName , null, dbVersion);
        ALog.d(TAG, ALog.DATA, "DBHelper object constructed");
    }

    /**
     * TODO maybe use for instrumentation tests
     * Construct a DBHelper object for tests, with an in memory database ( = New database is
     * created purely in memory. The database ceases to exist as soon as the database connection
     * is closed.)
     */
    @RestrictTo(RestrictTo.Scope.TESTS)
    protected DBHelper(Context context) {
        this(context, /*in-memory db*/ null, DATABASE_VERSION);
    }

    /**
     * Get or create instance of {@link DBHelper}. This class is a singleton.
     * @param context to use to open or create the database. Will use application context
     */
    /* package */ static DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext(),
                    DATABASE_NAME, DATABASE_VERSION);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createBoardTable(db);
        createStoryTable(db);
        createIssueTable(db);
    }

    /**
     * Create board table
     */
    private void createBoardTable(SQLiteDatabase db) {
        String columns = TableInfos.BoardTable.ColumnNames._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TableInfos.BoardTable.ColumnNames.NAME + " VARCHAR(100) NOT NULL, " +
                TableInfos.BoardTable.ColumnNames.START_TIME + " INTEGER";
        String createTable = "CREATE TABLE " + TableInfos.BoardTable.NAME +
                             " (" + columns + ")";

        db.execSQL(createTable);
        ALog.d(TAG, ALog.DATA, "Creating Board table");
    }

    /**
     * Create story table
     */
    private void createStoryTable(SQLiteDatabase db) {
        String columns = TableInfos.StoryTable.ColumnNames._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TableInfos.StoryTable.ColumnNames.NAME + " VARCHAR(100) NOT NULL, " +
                TableInfos.StoryTable.ColumnNames.DESCRIPTION + " VARCHAR(500)";
        String createTable = "CREATE TABLE " + TableInfos.StoryTable.NAME +
                " (" + columns + ")";

        db.execSQL(createTable);
        ALog.d(TAG, ALog.DATA, "Creating Story table");
    }

    /**
     * Create issue table
     */
    private void createIssueTable(SQLiteDatabase db) {
        String columns = TableInfos.IssueTable.ColumnNames._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TableInfos.IssueTable.ColumnNames.BOARD_KEY + " INTEGER REFERENCES " + TableInfos.BoardTable.NAME + ", " +
                TableInfos.IssueTable.ColumnNames.STORY_KEY + " INTEGER REFERENCES " + TableInfos.StoryTable.NAME + ", " +
                TableInfos.IssueTable.ColumnNames.NAME + " VARCHAR(100) NOT NULL, " +
                TableInfos.IssueTable.ColumnNames.DESCRIPTION + " VARCHAR(500), " +
                TableInfos.IssueTable.ColumnNames.STATUS + " INTEGER, " +
                TableInfos.IssueTable.ColumnNames.ESTIMATED + " INTEGER, " +
                TableInfos.IssueTable.ColumnNames.LOGGED + " INTEGER, " +
                TableInfos.IssueTable.ColumnNames.REMAINING + " INTEGER";
        String createTable = "CREATE TABLE " + TableInfos.IssueTable.NAME +
                " (" + columns + ")";

        db.execSQL(createTable);
        ALog.d(TAG, ALog.DATA, "Creating Issue table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ALog.d(TAG, ALog.DATA, "Upgrading database...");

        db.execSQL("DROP TABLE IF EXISTS " + TableInfos.BoardTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableInfos.StoryTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableInfos.IssueTable.NAME);

        onCreate(db);
    }

    /**
     * Didn't write this. Added by {@link AndroidDatabaseManager}
     */
    @SuppressWarnings("all")
    public ArrayList<Cursor> getData(String Query){
        ALog.d(TAG, ALog.DATA, "AndroidDatabaseManager's getData() method called");

        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            ALog.d("printing exception", ALog.DATA, sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            ALog.d("printing exception", ALog.DATA, ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }
}
