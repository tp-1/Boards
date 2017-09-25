package com.example.i5.boards.data;

import android.database.Cursor;
import android.support.annotation.WorkerThread;

import com.example.i5.boards.data.db.DBOperations;

import org.jetbrains.annotations.Contract;

/**
 * Superclass for classes representing rows from tables.
 * Has some common methods.
 * For details on tables, see: {@link com.example.i5.boards.data.db.TableInfos}
 */
abstract class TableRow {
    final static private String TAG = TableRow.class.getSimpleName();

    /**
     * Saves this row to db. Uses {@link DBOperations}
     */
    @WorkerThread
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
    @Contract(" -> fail")
    static public Cursor getAll() {
        throw new UnsupportedOperationException(
                "Using superclass' getAll() method that should be 'overriden'");
    }
}
