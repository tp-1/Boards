package com.example.i5.boards.ui.board;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.data.Issue;
import com.example.i5.boards.data.Story;

import java.util.ArrayList;

 /**
 * UI representation of {@link Story} object on a Board screen.
 * This class does not mirror its {@link Story} member,
 * because it only represents part of the story that belongs
 * to the selected board ({@link #mBoardId})
 */
class StoryOnBoard {
    final static private String TAG = StoryOnBoard.class.getSimpleName();

     /**
      * Story this UI object represents. {@link StoryOnBoard} will only
      * fetch issues that belong to the board it's displayed on ({@link #mBoardId})
      */
    private final Story mStory;
     /** Board this {@link StoryOnBoard} is being created on **/
    private final long mBoardId;

    private final TextView mStoryNameLabel;
    private final ViewGroup mToDoColumnItemsLayout;
    private final ViewGroup mProgressColumnItemsLayout;
    private final ViewGroup mDoneColumnItemsLayout;

     /** List of {@link IssueOnBoard} objects that will be shown in this {@link StoryOnBoard}) **/
    private final ArrayList<IssueOnBoard> issueItemList = new ArrayList<>();

     /**
      * Constructs a new {@link StoryOnBoard} object
      * @param view representing this story
      * @param story represented by this object
      * @param boardId board that this story ui object belongs to
      */
    StoryOnBoard(View view, Story story, long boardId) {
        mStoryNameLabel = (TextView) view.findViewById(R.id.storyName);
        // FIXME: 09-Jul-17 cast to LinearLayout or ViewGroup? learn basics of java bro
        mToDoColumnItemsLayout = (LinearLayout) view.findViewById(R.id.toDoColumnItems);
        mProgressColumnItemsLayout = (LinearLayout) view.findViewById(R.id.progressColumnItems);
        mDoneColumnItemsLayout = (LinearLayout) view.findViewById(R.id.doneColumnItems);

        mStory = story;
        mBoardId = boardId;

        ALog.v(TAG, ALog.UI, "Created new StoryOnBoard instance, instantiated ui fields");
    }

     /**
      * Fill data of this object. Specifically,
      * fill {@link #issueItemList} with {@link IssueOnBoard} objects
      * belonging to this board and story.
      * Doesn't draw anything.
      */
    /* package */ void loadData() {
        loadIssues();
    }

     /**
      * Fill the story card UI with data stored in {@link #mStory}
      * Inflate the child {@link IssueOnBoard} cards' UIs
      * <p>
      *     NOTE: Fields pointing to UI objects were initialized in the constructor
      *     (unlike in {@link IssueOnBoard} class)
      * </p>
      */
    /* package */ void draw(Context context) {
        ALog.d(TAG, ALog.UI, "Drawing UI (storyId = %d)...", mStory.getId());

        setTitle();
        for (IssueOnBoard iob : issueItemList) {
            iob.draw(context);
        }
    }

     /**
      * Fill {@link #issueItemList} with {@link IssueOnBoard} objects
      * belonging to this board and story.
      * Doesn't draw issues.
      */
    private void loadIssues() {
        ALog.d(TAG, ALog.UI, "Loading issues to put in a story...");

        // FIXME: 15-Jul-17 DATABASE ACCESS
        Cursor cursor = mStory.getIssuesFromBoard(mBoardId);
        if (cursor == null) {
            return;
        }

        while (cursor.moveToNext()) {
            Issue issue;
            ViewGroup issueParentLayout;

            issue = new Issue(cursor);
            // Appropriate story's status column will be parent that the
            // IssueOnBoard is added to
            switch (issue.getStatus()) {
                case TODO:
                    issueParentLayout = mToDoColumnItemsLayout;
                    break;
                case PROGRESS:
                    issueParentLayout = mProgressColumnItemsLayout;
                    break;
                case DONE:
                    issueParentLayout = mDoneColumnItemsLayout;
                    break;
                default:
                    ALog.e(TAG, ALog.UI, "Unrecognized Issue status!");
                    continue;
            }
            issueItemList.add(new IssueOnBoard(issueParentLayout, issue));
        }
    }

     /**
      * Set title of the story UI to stored story's
      * {@value com.example.i5.boards.data.db.TableInfos.StoryTable.ColumnNames#NAME}
      */
    private void setTitle() {
        mStoryNameLabel.setText(mStory.getName());
    }
}