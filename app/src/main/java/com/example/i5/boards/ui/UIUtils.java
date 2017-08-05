package com.example.i5.boards.ui;

import android.widget.TextView;

/**
 * Utilities used for UI operations.
 */
public class UIUtils {

    /**
     * Check if field is empty (or filled with blank spaces)
     * @param textView {@link TextView} to check for
     */
    public static boolean isFieldEmpty(TextView textView) {
        return textView.getText().toString().trim().isEmpty();
    }
}
