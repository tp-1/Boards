package com.example.i5.boards.ui.debugScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.data.db.AndroidDatabaseManager;
import com.example.i5.boards.ui.newItem.NewIssueActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Screen for accessing and changing some debug settings.
 *
 * So far, included in the debug mode:
 * <ul>
 * <li> Android Database Manager view </li>
 * <li> Checkboxes for adding and removing log filters </li>
 * </ul>
 *
 * Enter debug screen through {@link NewIssueActivity}
 */
public class DebugScreenActivity extends AppCompatActivity {
    final static private String TAG = DebugScreenActivity.class.getSimpleName();

    private Button mDebugDbManagerButton;
    private CheckBox mLogFilterUICheckbox;
    private CheckBox mLogFilterDataCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_screen);

        mDebugDbManagerButton = (Button) findViewById(R.id.debug_db_manager_button);
        mLogFilterUICheckbox = (CheckBox) findViewById(R.id.logFilterUICheckbox);
        mLogFilterDataCheckbox = (CheckBox) findViewById(R.id.logFilterDataCheckbox);

        mLogFilterUICheckbox.setChecked(ALog.isFilterEnabled(ALog.UI));
        mLogFilterDataCheckbox.setChecked(ALog.isFilterEnabled(ALog.DATA));

        mDebugDbManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatabaseManager();
            }
        });

        mLogFilterUICheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateLogFilters(isChecked, ALog.UI);
            }
        });

        mLogFilterDataCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateLogFilters(isChecked, ALog.DATA);
            }
        });
    }

    /**
     * Add or remove log filter
     * @param shouldIncludeFilter <code>true</code> if filter should be added
     *                            <code>false</code> if it should be removed
     * @param filter Filter that should be added or removed
     * @see ALog
     */
    private void updateLogFilters(boolean shouldIncludeFilter, int filter) {
        ALog.d(TAG, ALog.UI, "Updating log filters. Filter = %d, add? %b",
                filter, shouldIncludeFilter);

        if (shouldIncludeFilter) {
            ALog.addFilter(filter);
        } else {
            ALog.removeFilter(filter);
        }
    }

    /**
     * Start {@link AndroidDatabaseManager} activity
     */
    private void startDatabaseManager() {
        ALog.d(TAG, ALog.UI, "Starting AndroidDatabaseManager");
        Intent intent = AndroidDatabaseManager.getIntentToStart(DebugScreenActivity.this);
        startActivity(intent);
    }

    /**
     * @return intent that will start this activity
     */
    @NotNull
    public static Intent getIntentToStart(@NotNull Context context) {
        return new Intent(context, DebugScreenActivity.class);
    }
}