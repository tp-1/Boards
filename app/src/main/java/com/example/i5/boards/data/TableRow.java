package com.example.i5.boards.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.i5.boards.ALog;
import com.example.i5.boards.data.db.DBOperations;

/**
 * Superclass for classes representing rows from tables.
 * Statically instantiates {@link DBOperations} for the first time
 * Has some common methods.
 * For details on tables, see: {@link com.example.i5.boards.data.db.TableInfos}
 */
abstract class TableRow {
    final static private String TAG = TableRow.class.getSimpleName();

    protected static final DBOperations mDbOperations;

    static {
        // Must be executed after BoardActivity's onCreate()
        // Should be fine, since neither TableRow nor it's
        // child classes are used there
        // Srsly?
        mDbOperations = DBOperations.getInstance();
    }

    /**
     * Saves this row to db. Uses {@link #mDbOperations}
     */
    abstract public void save();

    /**
     * Fills fields of the row represented by the subclass with data from the cursor
     * @param cursor Cursor positioned on the row that should be restored
     */
    abstract protected void restoreFromCursor(Cursor cursor);

    /**
     * Get all rows from a table. Doesn't do anything,
     * every subclass should have this method defined
     * since I'm pretending to override it.
     * @return Cursor over all rows from the table
     */
    static public Cursor getAll() {
        ALog.w(TAG, "Using superclass' getAll() method that should be 'overriden'");
        return null;
    }
}
