package com.example.i5.boards.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.i5.boards.ALog;
import com.example.i5.boards.data.db.TableInfos;

/**
 * Represents rows from the Issue table.
 * Stores column values and methods for fetching from
 * and saving rows of this table to database.
 */
public class Issue extends TableRow {
    final static private String TAG = Issue.class.getSimpleName();

    private long id;
    private long boardKey;
    private long storyKey;
    private String name;
    private String description;
    /**
     * Stored as a string in db
     * @see <a href="https://stackoverflow.com/questions/8254038/storing-and-retrieving-enums-in-sqlite-with-java">
     *     Storing enums in SQLite</a>
     */
    private Status status;
    private long estimated;
    private long logged;
    private long remaining;

    /**
     * Represents status of completion the issue can be in
     */
    public enum Status {
        TODO, PROGRESS, DONE;
    }

    public Issue() {

    }

    public Issue(Cursor cursor) {
        restoreFromCursor(cursor);
    }

    @Override
    protected void restoreFromCursor(Cursor cursor) {
        // FIXME: 09-Jul-17 constants
        id = cursor.getLong(0);
        boardKey = cursor.getLong(1);
        storyKey = cursor.getLong(2);
        name = cursor.getString(3);
        description = cursor.getString(4);
        status = Status.valueOf(cursor.getString(5));
        estimated = cursor.getLong(6);
        logged = cursor.getLong(7);
        remaining = cursor.getLong(8);

        ALog.d(TAG, ALog.DATA, "Restored issue " + id + " from cursor");
        ALog.v(TAG, ALog.DATA, "Issue:\n", this);
    }

    @Override
    public void save() {
        ContentValues cv = new ContentValues(8);
        cv.put(TableInfos.IssueTable.ColumnNames.BOARD_KEY, boardKey);
        cv.put(TableInfos.IssueTable.ColumnNames.STORY_KEY, storyKey);
        cv.put(TableInfos.IssueTable.ColumnNames.NAME, name);
        cv.put(TableInfos.IssueTable.ColumnNames.DESCRIPTION, description);
        cv.put(TableInfos.IssueTable.ColumnNames.STATUS, status.name());
        cv.put(TableInfos.IssueTable.ColumnNames.ESTIMATED, estimated);
        cv.put(TableInfos.IssueTable.ColumnNames.LOGGED, logged);
        cv.put(TableInfos.IssueTable.ColumnNames.REMAINING, remaining);
        mDbOperations.save(TableInfos.IssueTable.NAME, cv);
    }

    /**
     * Pretending to override from superclass. Trust me - I know what I'm doing.
     * @return Cursor over all rows from the issue table
     */
    static public Cursor getAll() {
        return mDbOperations.query(TableInfos.IssueTable.NAME);
    }

    public long getId() {
        return id;
    }

    public long getBoardKey() {
        return boardKey;
    }

    public Issue setBoardKey(long boardKey) {
        this.boardKey = boardKey;
        return this;
    }

    public long getStoryKey() {
        return storyKey;
    }

    public Issue setStoryKey(long storyKey) {
        this.storyKey = storyKey;
        return this;
    }

    public String getName() {
        return name;
    }

    public Issue setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issue setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Issue setStatus(Status status) {
        this.status = status;
        return this;
    }

    public long getEstimated() {
        return estimated;
    }

    public Issue setEstimated(long estimated) {
        this.estimated = estimated;
        return this;
    }

    public long getLogged() {
        return logged;
    }

    public Issue setLogged(long logged) {
        this.logged = logged;
        return this;
    }

    public long getRemaining() {
        return remaining;
    }

    public Issue setRemaining(long remaining) {
        this.remaining = remaining;
        return this;
    }

    // TODO: 13-Jul-17 write toString method
    @Override
    public String toString() {
        return super.toString();
    }
}
