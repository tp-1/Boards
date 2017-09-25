package com.example.i5.boards.ui.newItem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.i5.boards.ALog;

/**
 * Superclass of all New..Activity classes that are used when
 * creating a new issue/story/board.
 * Contains docs and/or implementations of some common methods.
 * <p>
 *     All subclasses support {@link #startActivityForResult(Intent, int)}
 *     and return {@link #RESULT_OK} if the item was successfuly created
 * </p>
 */
public abstract class NewItemActivity extends AppCompatActivity {
    /**
     * Called when Done button is clicked. Defined as
     * an onClick action in xml
     * Checks if fields are valid and lets the flow continue in case they are
     * @param view View that was clicked (done button)
     */
    @SuppressWarnings("UnusedParameters")
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
    protected abstract boolean areFieldsValid();
    /**
     * Take data user filled in from the UI and save it to the database.
     * Assumes field validity was already established.
     */
    protected abstract void saveToDatabase();
    /**
     * Get tag used for logging (usually = class name)
     */
    protected abstract String getLogTag();

    /**
     * Call when user finished editing and his data is validated and ready to be processed.
     * Saves data to database.
     * Sets the result returned to the activity's caller to {@link #RESULT_OK}
     * Finishes the activity.
     */
    private void finishEditing() {
        ALog.d(getLogTag(), ALog.UI, "Setting result and finishing activity");

        saveToDatabase();
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }
}
