package com.example.i5.boards.ui.newBoard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.data.Board;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewBoardActivity extends AppCompatActivity {
    final static private String TAG = NewBoardActivity.class.getSimpleName();

    EditText mNameText;
    DatePicker mStartTimePicker;
    Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_board);

        ALog.v(TAG, "Creating NewBoardActivity");

        mNameText = (EditText)findViewById(R.id.boardNameText);
        mStartTimePicker = (DatePicker)findViewById(R.id.boardStartTimePicker);
        mDoneButton = (Button)findViewById(R.id.boardDoneButton);

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDone();
            }
        });
    }

    /**
     * Checks if fields are valid and lets the flow continue in case they are
     */
    private void onDone() {
        if (areFieldsValid()) {
            finishEditing();
        } else {
            Toast.makeText(this, "Some fields are empty",
                    Toast.LENGTH_SHORT).show();
        }
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

        String name = mNameText.getText().toString();
        Calendar calendar = new GregorianCalendar(
                mStartTimePicker.getYear(),
                mStartTimePicker.getMonth(),
                mStartTimePicker.getDayOfMonth());

        Board board = new Board();
        board.setName(name);
        board.setStartTime(calendar.getTimeInMillis());

        board.save();
    }

    /**
     * @return whether all fields were filled correctly and data can be processed
     * TODO add more checks
     */
    private boolean areFieldsValid() {
        boolean valid = true;
        if (mNameText.getText().toString().trim().isEmpty()) {
            valid =  false;
        }
        ALog.d(TAG, "Validating fields; valid = %b", valid);
        return valid;
    }

    /**
     * @return intent that will start this activity
     */
    @NotNull
    public static Intent getIntentToStart(@NotNull Context context) {
        return new Intent(context, NewBoardActivity.class);
    }
}
