package com.example.i5.boards.ui.newItem;

import android.content.Context;
import android.content.Intent;
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

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Activity for creating(/editing?) a new board.
 * Contains:
 * <ul>
 *     <li> Widgets to fill in {@link Board} fields </li>
 * </ul>
 */
public class NewBoardActivity extends NewItemActivity {
    final static private String TAG = NewBoardActivity.class.getSimpleName();

    private EditText mNameText;
    private DatePicker mStartTimePicker;
    private Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_board);

        ALog.v(TAG, ALog.UI, "Creating NewBoardActivity");

        mNameText = (EditText)findViewById(R.id.boardNameText);
        mStartTimePicker = (DatePicker)findViewById(R.id.boardStartTimePicker);
        mDoneButton = (Button)findViewById(R.id.boardDoneButton);
    }

    @Override
    protected void saveToDatabase() {
        ALog.d(TAG, ALog.UI, "Taking data from ui and saving to database");

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

    @Override
    protected boolean areFieldsValid() {
        boolean valid = true;
        if (mNameText.getText().toString().trim().isEmpty()) {
            valid =  false;
        }
        ALog.d(TAG, ALog.UI, "Validating fields; valid = %b", valid);
        return valid;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    /**
     * @return intent that will start this activity
     */
    @NotNull
    public static Intent getIntentToStart(@NotNull Context context) {
        return new Intent(context, NewBoardActivity.class);
    }
}
