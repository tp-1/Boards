package com.example.i5.boards.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.i5.boards.ALog;
import com.example.i5.boards.data.db.TableInfos;

/**
 * Represents rows from the Board table.
 * Stores column values and methods for fetching from
 * and saving rows of this table to database.
 */
public class Board extends TableRow {
    final static private String TAG = Issue.class.getSimpleName();

    private long mId;
    private String mName;
    private long mStartTime;

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        this.mStartTime = startTime;
    }

    /**
     * Pretending to override from superclass. Trust me - I know what I'm doing.
     * @return Cursor over all rows from the boards table
     */
    static public Cursor getAll() {
        return mDbOperations.query(TableInfos.BoardTable.NAME);
    }

    @Override
    public void save() {
        ContentValues cv = new ContentValues(2);
        cv.put(TableInfos.BoardTable.ColumnNames.NAME, mName);
        cv.put(TableInfos.BoardTable.ColumnNames.START_TIME, mStartTime);
        mDbOperations.save(TableInfos.BoardTable.NAME, cv);
    }

    @Override
    protected void restoreFromCursor(Cursor cursor) {
        // TODO: 13-Jul-17
        ALog.d(TAG, ALog.DATA, "Restored board " + mId + " from cursor");
        ALog.v(TAG, ALog.DATA, "Board:\n", this);
    }

    @Override
    public String toString() {
        // TODO: 13-Jul-17
        return super.toString();
    }
}