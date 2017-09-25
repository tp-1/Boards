package com.example.i5.boards.data;

import android.content.ContentValues;

import com.example.i5.boards.data.db.DBOperations;
import com.example.i5.boards.data.db.TableInfos;

import org.junit.After;
import org.junit.Before;

/**
 * {@inheritDoc}
 */
public class StoryTest extends TableRowTest {
    @Before
    public void setUp() {
        // Create DBHelper
        // Can't close the DBHelper if I open it like this.
        // I'll have to use setupDatabaseForTests().
        // It has package access. I'll have to change access or create a util class for op tests
//        DBOperations.setupDatabase(RuntimeEnvironment.application);
//
//        Save two stories
//        saveStoryToDatabase("testStory1");
//        saveStoryToDatabase("testStory2");
    }

    @After
    public void tearDown() {
        //DBOperations.
    }

    @Override
    public void should_HaveCorrectFields_When_RestoreFromCursor() {

    }

    @Override
    public void should_ReturnTableRowWithFields_When_RestoringFromId() {

    }

    @Override
    public void should_ReturnCursorOverAllTableRows_When_getAll() {

    }

    @Override
    public void should_HaveCorrectValuesInDatabase_After_save() {

    }

    /**
     * Save story to database, using {@link DBOperations}
     */
    private long saveStoryToDatabase(String name) {
        ContentValues cv = new ContentValues(2);
        cv.put(TableInfos.StoryTable.ColumnNames.NAME, name);
        cv.put(TableInfos.StoryTable.ColumnNames.DESCRIPTION, name + " description");

        return DBOperations.save(TableInfos.StoryTable.NAME, cv);
    }
}
