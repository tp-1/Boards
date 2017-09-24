package com.example.i5.boards.data;

import com.example.i5.boards.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * [PREREQUISITES] {@link com.example.i5.boards.data.db.DBOperationsTest} <br/>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class TableRowTest {
    @Test
    public abstract void should_HaveCorrectFields_When_RestoreFromCursor();
    @Test
    public abstract void should_ReturnTableRowWithFields_When_RestoringFromId();
    @Test
    public abstract void should_ReturnCursorOverAllTableRows_When_getAll();
    @Test
    public abstract void should_HaveCorrectValuesInDatabase_After_save();
}
