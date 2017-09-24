package com.example.i5.boards.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * A helper object to create, open, and/or manage a database.
 * Currently used to test {@link DBOperations}.
 * DBOperations is a layer between {@link DBHelper} and {@link com.example.i5.boards.data.TableRow},
 * it should be tested without interference from logic from those two classes.
 */
class FakeDBHelper extends SQLiteOpenHelper {
    final static private String DATABASE_NAME = "testBoards.db";
    final static private int DATABASE_VERSION = 1;

    /* Table 1 */
    final static /* package */ String TABLE1_NAME = "testTable1";
    final static /* package */ String TABLE1_COLUMN_ID = BaseColumns._ID;
    final static /* package */ String TABLE1_COLUMN_NAME = "name";

    /* package */ FakeDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String columns = TABLE1_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                TABLE1_COLUMN_NAME + " VARCHAR(100) NOT NULL";
        String createTable = "CREATE TABLE " + TABLE1_NAME +
                " (" + columns + ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new IllegalStateException("onUpgrade() called on fake DBHelper");
    }
}
