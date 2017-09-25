package com.example.i5.boards.data;

import com.example.i5.boards.Assumes;
import com.example.i5.boards.BuildConfig;
import com.example.i5.boards.data.db.DBOperationsTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
@Assumes(DBOperationsTest.class)
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
