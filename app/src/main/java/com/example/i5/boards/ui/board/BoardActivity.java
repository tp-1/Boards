package com.example.i5.boards.ui.board;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.ui.newItem.NewIssueActivity;

/**
 * Activity for managing the selected Board with list of its stories and issues.
 * UI is managed by {@link BoardFragment}
 * Contains:
 * <ul>
 *     <li>Action bar (which is currently useless)</li>
 *     <li>Fragment for managing the content of a board</li>
 *     <li>Fab which will take the user to create a new issue</li>
 * </ul>
 */
public class BoardActivity extends AppCompatActivity {
    final static private String TAG = BoardActivity.class.getSimpleName();

    /**
     * Request code for starting the {@link NewIssueActivity} for result.
     * This code will be returned in onActivityResult().
     */
    private static final int NEW_ISSUE_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        ALog.v(TAG, ALog.UI, "Creating BoardActivity");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewIssue();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ALog.d(TAG, ALog.UI, "Result received; request code = %d, result code = %d",
                requestCode, resultCode);

        switch (requestCode) {
            case NEW_ISSUE_ACTIVITY_CODE:
                if (resultCode == RESULT_OK) {
                    getBoardFragment().reload();
                }
                break;
        }
    }

    /**
     * Take user to {@link NewIssueActivity}, so he can create a new issue
     */
    private void createNewIssue() {
        ALog.d(TAG, ALog.UI, "Starting NewIssueActivity for result");
        Intent intent = NewIssueActivity.getIntentToStart(this);
        startActivityForResult(intent, NEW_ISSUE_ACTIVITY_CODE);
    }

    /**
     * Get the {@link BoardFragment} that is managing the ui
     */
    private BoardFragment getBoardFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (BoardFragment)fm.findFragmentById(R.id.board_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
