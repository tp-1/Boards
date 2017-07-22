package com.example.i5.boards.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.i5.boards.ALog;
import com.example.i5.boards.data.db.TableInfos;

import java.util.Random;
import java.util.jar.Pack200;

/**
 * Represents rows from the Story table.
 * Stores column values and methods for fetching from
 * and saving rows of this table to database.
 */
public class Story extends TableRow {
    private static final String TAG = Story.class.getSimpleName();

    private long id;
    private String name;
    private String description;

    public Story() {

    }

    public Story(Cursor cursor) {
        restoreFromCursor(cursor);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Story createDummy(String name, String desc) {
        Story story = new Story();
        story.setName(name);
        story.setDescription(desc);
        story.id = new Random(System.currentTimeMillis()).nextLong();

        ALog.d(TAG, ALog.DATA, "Created a dummy story with id = " + story.id);

        return story;
    }

    /**
     * Restore a story from database with given {@link #id}
     * <br/>
     * <b>NOTE: database access</b>
     */
    // TODO: 09-Jul-17 add annotation for db access?
    @Nullable
    public static Story restoreFromId(long id) {
        Cursor c = mDbOperations.query(TableInfos.StoryTable.NAME, id);
        Story story = null;
        if (c.moveToNext()) {
            story = new Story(c);
            ALog.d(TAG, ALog.DATA, "Restored story " + id + " from id");
            ALog.v(TAG, ALog.DATA, "Story:\n", story);
        } else {
            ALog.w(TAG, ALog.DATA, "Couldn't restore story with id = " + id);
        }
        return story;
    }

    /**
     * Pretending to override from superclass. Trust me - I know what I'm doing.
     * @return Cursor over all rows from the story table
     */
    static public Cursor getAll() {
        return mDbOperations.query(TableInfos.StoryTable.NAME);
    }

    @Override
    public void save() {
        ContentValues cv = new ContentValues(2);
        cv.put(TableInfos.StoryTable.ColumnNames.NAME, name);
        cv.put(TableInfos.StoryTable.ColumnNames.DESCRIPTION, description);
        mDbOperations.save(TableInfos.StoryTable.NAME, cv);
    }

    @Override
    protected void restoreFromCursor(Cursor cursor) {
        // FIXME: 13-Jul-17 constants
        id = cursor.getLong(0);
        name = cursor.getString(1);
        description = cursor.getString(2);

        ALog.d(TAG, ALog.DATA, "Restored story " + id + " from cursor");
        ALog.v(TAG, ALog.DATA, "Story:\n", this);
    }

    /**
     * Get all issues with the
     * {@value com.example.i5.boards.data.db.TableInfos.IssueTable.ColumnNames#STORY_KEY}
     *  = {@link #id}
     */
    public Cursor getIssues() {
        String selection = TableInfos.IssueTable.ColumnNames.STORY_KEY + " = ?";
        String[] selectionArgs = new String[]{Long.toString(id)};
        return mDbOperations.query(TableInfos.IssueTable.NAME, null,
                selection, selectionArgs);
    }

    /**
     * Get all issues from this story whose
     * {@link com.example.i5.boards.data.db.TableInfos.IssueTable.ColumnNames#BOARD_KEY}
     * matches the given boardId
     */
    public Cursor getIssuesFromBoard(long boardId) {
        ALog.d(TAG, ALog.DATA, "Getting all issues from a story " + id + " on board " + boardId);

        String selection = TableInfos.IssueTable.ColumnNames.STORY_KEY + " = ? AND " +
                TableInfos.IssueTable.ColumnNames.BOARD_KEY + " = ?";
        String[] selectionArgs = new String[]{Long.toString(id), Long.toString(boardId)};
        return mDbOperations.query(TableInfos.IssueTable.NAME, null,
                selection, selectionArgs);
    }

    @Override
    public String toString() {
        // TODO: 13-Jul-17
        return super.toString();
    }
}
