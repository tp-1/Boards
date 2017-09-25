package com.example.i5.boards.ui.newItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.data.Story;

import org.jetbrains.annotations.NotNull;

/**
 * Activity for creating(/editing?) a new story.
 * Contains:
 * <ul>
 *     <li> Widgets to fill in {@link Story} fields </li>
 * </ul>
 */
public class NewStoryActivity extends NewItemActivity {
    final static private String TAG = NewStoryActivity.class.getSimpleName();

    private EditText mNameText;
    private EditText mDescriptionText;
    private Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        ALog.v(TAG, ALog.UI, "Creating NewStoryActivity");

        mNameText = (EditText)findViewById(R.id.storyNameText);
        mDescriptionText = (EditText)findViewById(R.id.storyDescText);
        mDoneButton = (Button)findViewById(R.id.storyDoneButton);
    }

    @Override
    protected void saveToDatabase() {
        ALog.d(TAG, ALog.UI, "Taking data from ui and saving to database");

        String name = mNameText.getText().toString();
        String desc = mDescriptionText.getText().toString();

        Story story = new Story();
        story.setName(name);
        story.setDescription(desc);

        story.save();
    }

    @Override
    protected boolean areFieldsValid() {
        boolean valid = true;
        if (mNameText.getText().toString().trim().isEmpty()) {
            valid = false;
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
        return new Intent(context, NewStoryActivity.class);
    }
}
