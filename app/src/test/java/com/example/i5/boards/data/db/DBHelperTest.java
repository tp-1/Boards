package com.example.i5.boards.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.i5.boards.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link DBHelper}
 * Test that all wanted tables are created when getting the database from the helper
 * There should probably be more tests here
 * @see <a href="http://robolectric.org/getting-started/">Robolectric</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DBHelperTest {
    private DBHelper mDBHelper;

    @Before
    public void setup() {
        /*
         * From https://stackoverflow.com/questions/13009259/how-to-test-methods-that-deal-with-sqlite-database-in-android
         *
         * You should prefer to use IsolatedContext for your database creation. In this case
         * your original database file from the app will remain unmodified.
         * All tests will work with separate testing database file which can be modified or
         * even deleted as many times as you want.
         *
         * You can use Robolectric's RuntimeEnvironment.application as a replacement of Context
         * in your app
         *
         * https://stackoverflow.com/questions/4645461/isolatedcontext-vs-androidtestcase-getcontext
         */
        mDBHelper = DBHelper.getInstance(RuntimeEnvironment.application);
    }

    /**
     * Test that {@link DBHelper} is a singleton. Clearly, I have no idea what I'm doing
     */
    @Test
    public void should_ReuseTheInstance_When_getInstance() {
        DBHelper newDbHelper = DBHelper.getInstance(RuntimeEnvironment.application);
        assertEquals(mDBHelper, newDbHelper);
    }

    @Test
    public void should_createBoardTable_When_CreateDatabase() {
        helper_should_createTable_When_CreateDatabase("Board");
    }

    @Test
    public void should_createStoryTable_When_CreateDatabase() {
        helper_should_createTable_When_CreateDatabase("Story");
    }

    @Test
    public void should_createIssueTable_When_CreateDatabase() {
        helper_should_createTable_When_CreateDatabase("Issue");
    }

    /**
     * Test that table with tableName exists
     */
    private void helper_should_createTable_When_CreateDatabase(String tableName) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM sqlite_master WHERE " +
                        "type='table' AND name='" + tableName + "';", null);
        assertNotNull(c);
        assertNotEquals(0, c.getCount());
    }

    @After
    public void teardown() {
        mDBHelper.close();
    }
}