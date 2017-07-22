package com.example.i5.boards.ui.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.data.Issue;

import static android.R.attr.name;

/**
 * UI representation of Issue object on a Board screen
 */
class IssueOnBoard {
    private static final String TAG = IssueOnBoard.class.getSimpleName();

    /** Issue this UI object represents **/
    private Issue mIssue;

    /** Status column in {@link StoryOnBoard} that this issue belongs to **/
    private ViewGroup mParentView;
    private TextView mNameLabel;
    private TextView mDescriptionLabel;
    private TextView mRemainingLabel;

    /**
     * FIXME inconsistent, StoryOnBoard has same parameters but the first one is the story view
     * @param parentView Column that this issue will be added to
     */
    IssueOnBoard(ViewGroup parentView, Issue issue) {
        mParentView = parentView;
        mIssue = issue;

        ALog.v(TAG, ALog.UI, "Created new IssueOnBoard instance");
    }

    /**
     * Inflate the UI and initialize fields pointing to UI objects.
     * Fill the issue card UI with data stored in {@link #mIssue}
     */
    /*package*/ void draw(Context context) {
        ALog.d(TAG, ALog.UI, "Drawing UI (issueId = %d), initializing ui fields...",
                mIssue.getId());

        LayoutInflater inflater = LayoutInflater.from(context);
        // https://possiblemobile.com/2013/05/layout-inflation-as-intended/
        View view = inflater.inflate(R.layout.issue, mParentView, false);
        mParentView.addView(view);

        mNameLabel = (TextView) view.findViewById(R.id.issue_name);
        mDescriptionLabel = (TextView) view.findViewById(R.id.issue_desc);
        mRemainingLabel = (TextView) view.findViewById(R.id.issue_remaining);

        setTitle();
        setDescription();
        setRemainingTime();
    }

    /**
     * Set title of the issue card UI to stored issue's
     * {@value com.example.i5.boards.data.db.TableInfos.IssueTable.ColumnNames#NAME}
     */
    private void setTitle() {
        mNameLabel.setText(mIssue.getName());
    }

    /**
     * Set description of the issue card UI to stored issue's
     * {@value com.example.i5.boards.data.db.TableInfos.IssueTable.ColumnNames#DESCRIPTION}
     */
    private void setDescription() {
        mDescriptionLabel.setText(mIssue.getDescription());
    }

    /**
     * Set remaining time of the issue card UI to stored issue's
     * {@value com.example.i5.boards.data.db.TableInfos.IssueTable.ColumnNames#REMAINING}
     */
    private void setRemainingTime() {
        mRemainingLabel.setText(Long.toString(mIssue.getRemaining()));
    }
}