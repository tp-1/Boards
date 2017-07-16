package com.example.i5.boards.ui.newIssue;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.data.Board;
import com.example.i5.boards.data.Issue;
import com.example.i5.boards.data.Story;
import com.example.i5.boards.data.db.AndroidDatabaseManager;
import com.example.i5.boards.data.db.TableInfos;
import com.example.i5.boards.ui.debugScreen.DebugScreenActivity;
import com.example.i5.boards.ui.newBoard.NewBoardActivity;
import com.example.i5.boards.ui.newStory.NewStoryActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Activity for creating(/editing?) a new issue.
 * TODO create uniform layout for all New..Activity classes?
 * TODO create superclass of all New..Activity classes
 * Contains:
 * <ul>
 *     <li> Hidden debug screen that is set in {@link #setUpDebugEntryPoint}</li>
 *     <li> Spinner for choosing a board + possibility to create a new board </li>
 *     <li> Spinner for choosing a story + possibility to create a new story </li>
 *     <li> Widgets to fill in other {@link Issue} fields </li>
 * </ul>
 * Upon coming back to {@link com.example.i5.boards.ui.board.BoardActivity} with
 * {@link #RESULT_OK}, board screen will be refreshed and data loaded again
 */
public class NewIssueActivity extends AppCompatActivity {
    final static private String TAG = NewIssueActivity.class.getSimpleName();

    Spinner boardSpinner;
    Spinner storySpinner;
    EditText nameText;
    EditText descText;
    Spinner statusSpinner;
    NumberPicker estimatedPicker;
    NumberPicker remainingPicker;
    Button doneButton;

    SimpleCursorAdapter boardSpinnerAdapter;
    SimpleCursorAdapter storySpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_issue);

        ALog.v(TAG, "Creating NewIssueActivity");

        boardSpinner = (Spinner)findViewById(R.id.boardSpinner);
        storySpinner = (Spinner)findViewById(R.id.storySpinner);
        nameText = (EditText)findViewById(R.id.nameText);
        descText = (EditText)findViewById(R.id.descText);
        statusSpinner = (Spinner)findViewById(R.id.statusSpinner);
        estimatedPicker = (NumberPicker)findViewById(R.id.estimatedPicker);
        remainingPicker = (NumberPicker)findViewById(R.id.remainingPicker);
        doneButton = (Button)findViewById(R.id.issueDoneButton);

        setBoardSpinnerAdapter();
        setStorySpinnerAdapter();

        setStatusSpinnerAdapter();

        setUpDebugEntryPoint();
    }

    /**
     * Set the adapter for the {@link #statusSpinner}, which will list status values
     * of an issue ({@link Issue.Status})
     */
    private void setStatusSpinnerAdapter() {
        ArrayAdapter<Issue.Status> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1,
                Issue.Status.values());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinnerAdapter);

        ALog.v(TAG, "Set status spinner adapter");
    }

    /**
     * Set the adapter for the {@link #boardSpinner}, which will list board names
     */
    private void setBoardSpinnerAdapter() {
        boardSpinnerAdapter = setCursorSpinnerAdapter(boardSpinner, Board.getAll(),
                TableInfos.BoardTable.ColumnNames.NAME);
        ALog.v(TAG, "Set board spinner adapter");
    }

    /**
     * Set the adapter for the {@link #storySpinner}, which will list story names
     */
    private void setStorySpinnerAdapter() {
        storySpinnerAdapter = setCursorSpinnerAdapter(storySpinner, Story.getAll(),
                TableInfos.StoryTable.ColumnNames.NAME);
        ALog.v(TAG, "Set story spinner adapter");
    }

    /**
     * Set the adapter for the spinner whose data has to be fetched in cursor,
     * which will then adapt the cursor to spinner items
     * @param columnName Name of the column whose value
     *                    will be used in a spinner item
     * @see SimpleCursorAdapter
     */
    private SimpleCursorAdapter setCursorSpinnerAdapter(Spinner spinner, Cursor cursor,
                                                        String columnName) {
        SimpleCursorAdapter spinnerAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, cursor, new String[] {columnName},
                new int[] {android.R.id.text1}, 0);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        return spinnerAdapter;
    }

    /**
     * Set up action needed to enter debug screen.
     * @see com.example.i5.boards.ui.debugScreen.DebugScreenActivity
     */
    private void setUpDebugEntryPoint() {
        // Enter debug mode when user taps boardLabel a certain number of times
        TextView boardLabel = (TextView)findViewById(R.id.boardLabel);
        boardLabel.setOnClickListener(new View.OnClickListener() {
            private int numberOfClicks;
            private final int NUM_OF_CLICKS_FOR_DB_MANAGER = 3;
            @Override
            public void onClick(View v) {
                if (++numberOfClicks >= NUM_OF_CLICKS_FOR_DB_MANAGER) {
                    numberOfClicks = 0;
                    Intent intent = DebugScreenActivity.getIntentToStart(NewIssueActivity.this);
                    startActivity(intent);
                }
            }
        });
        ALog.d(TAG, "Set debug entry point");
    }

    @Override
    protected void onResume() {
        super.onResume();

        ALog.d(TAG, "Resuming NewIssueActivity, changing spinner cursors");
        boardSpinnerAdapter.changeCursor(Board.getAll());
        storySpinnerAdapter.changeCursor(Story.getAll());
    }

    /**
     * Handles the onClick event of the add Board button
     * Just trying things out.
     * Sorry for the inconsistent code, future me O:)
     * Starts the {@link NewBoardActivity} so user can create a new board
     */
    public void createNewBoard(View view) {
        ALog.d(TAG, "Starting NewBoardActivity");
        // TODO: 15-Jul-17 startActivityForResult
        Intent intent = NewBoardActivity.getIntentToStart(this);
        startActivity(intent);
    }

    /**
     * Handles the onClick event of the add Story button
     * Starts the {@link NewStoryActivity} so user can create a new story
     */
    public void createNewStory(View view) {
        ALog.d(TAG, "Starting NewStoryActivity");
        // TODO: 15-Jul-17 startActivityForResult
        Intent intent = NewStoryActivity.getIntentToStart(this);
        startActivity(intent);
    }

    /**
     * Called when {@link R.id#issueDoneButton} is clicked. Defined as
     * an onClick action in xml
     * Checks if fields are valid and lets the flow continue in case they are
     * @param view View that was clicked (done button)
     */
    public void onDone(View view) {
        if (areFieldsValid()) {
            finishEditing();
        } else {
            Toast.makeText(this, "Some fields are empty",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @return whether all fields were filled correctly and data can be processed
     * TODO add more checks
     */
    private boolean areFieldsValid() {
        boolean valid = true;

        if (nameText.getText().toString().trim().isEmpty()) {
            valid = false;
        } else if (descText.getText().toString().trim().isEmpty()) {
            valid = false;
        }
        ALog.d(TAG, "Validating fields; valid = %b", valid);
        return valid;
    }

    /**
     * Call when user finished editing and his data is validated and ready to be processed.
     * Saves data to database.
     * Sets the result returned to the activity's caller to {@link #RESULT_OK}
     * Finishes the activity.
     */
    private void finishEditing() {
        ALog.d(TAG, "Setting result and finishing activity");

        saveToDatabase();
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * Take data user filled in from the UI and save it to the database.
     * Assumes field validity was already established.
     */
    private void saveToDatabase() {
        ALog.d(TAG, "Taking data from ui and saving to database");

        Cursor boardItem = (Cursor) boardSpinner.getSelectedItem();
        Cursor storyItem = (Cursor) storySpinner.getSelectedItem();

        int boardKey = boardItem.getInt(0); // FIXME: 25-Jun-17 constant
        int storyKey = storyItem.getInt(0); // FIXME: 25-Jun-17 constant
        String name = nameText.getText().toString();
        String desc = descText.getText().toString();
        Issue.Status status = (Issue.Status)statusSpinner.getSelectedItem();
        long estimated = estimatedPicker.getValue();
        long remaining = remainingPicker.getValue();

        Issue issue = new Issue();
        issue.setBoardKey(boardKey)
                .setStoryKey(storyKey)
                .setName(name)
                .setDescription(desc)
                .setStatus(status)
                .setEstimated(estimated)
                .setRemaining(remaining);

        issue.save();
    }

    /**
     * @return intent that will start this activity
     */
    @NotNull
    public static Intent getIntentToStart(@NotNull Context context) {
        return new Intent(context, NewIssueActivity.class);
    }
}