package com.example.i5.boards.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.i5.boards.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * [PREREQUISITES] {@link DBHelperTest} <br/>
 * Consists of {@link DBOperationsWithSetupTest} and {@link DBOperationsWithoutSetupTest}.
 * If setup isn't performed only thing that needs to be checked is that operations fail immediately
 * If setup is performed, {@link DBOperations} is technically just a layer
 * between DBHelper and {@link com.example.i5.boards.data.TableRow}. The only thing it needs to do
 * is correctly dispatch requests to {@link SQLiteOpenHelper}
 */
@RunWith(Enclosed.class)
public class DBOperationsTest {
    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class)
    public static class DBOperationsWithSetupTest {
        private SQLiteOpenHelper mFakeDbHelper;

        private long preSavedRowId = 1;
        private String preSavedRowName = "testRow1";

        @Before
        public void setUp() {
            mFakeDbHelper = new FakeDBHelper(RuntimeEnvironment.application);
            DBOperations.setupDatabaseForTests(mFakeDbHelper);

            // Insert one row to begin with
            ContentValues cv = new ContentValues(2);
            cv.put(FakeDBHelper.TABLE1_COLUMN_ID, preSavedRowId);
            cv.put(FakeDBHelper.TABLE1_COLUMN_NAME, preSavedRowName);
            long id = mFakeDbHelper.getWritableDatabase()
                    .insert(FakeDBHelper.TABLE1_NAME, null, cv);

            // insert() will return -1 in case of an error. Assert there was no error
            assertNotEquals(-1, id);
        }

        @Test
        public void should_ReturnRow_When_Querying() {
            Cursor cursor = DBOperations.query(FakeDBHelper.TABLE1_NAME);

            // Make sure we fetched exactly one row
            assertNotNull(cursor);
            assertEquals(1, cursor.getCount());
        }

        @Test
        public void should_RestoreCorrectValues_After_Querying() {
            Cursor cursor = DBOperations.query(FakeDBHelper.TABLE1_NAME);

            // Move to first row
            cursor.moveToNext();
            // Assert that the restored values are the same ones we saved
            assertEquals(preSavedRowId, cursor.getLong(0));
            assertEquals(preSavedRowName, cursor.getString(1));
        }

        @Test
        public void should_ReturnRow_When_QueryingById() {
            Cursor cursor = DBOperations.query(FakeDBHelper.TABLE1_NAME, preSavedRowId);

            // Make sure we fetched exactly one row
            assertNotNull(cursor);
            assertEquals(1, cursor.getCount());
        }

        @Test
        public void should_ReturnRow_When_QueryingWithSelection() {
            // Prepare selection
            String selection = FakeDBHelper.TABLE1_COLUMN_ID + " = ?";
            String[] selectionArgs = new String[]{Long.toString(preSavedRowId)};

            Cursor cursor = DBOperations.query(FakeDBHelper.TABLE1_NAME, null,
                    selection, selectionArgs);

            // Make sure we fetched exactly one row
            assertNotNull(cursor);
            assertEquals(1, cursor.getCount());
        }

        @Test
        public void should_HaveExtraRow_After_Inserting() {
            // Insert a new row in table
            ContentValues cv = new ContentValues(2);
            cv.put(FakeDBHelper.TABLE1_COLUMN_ID, 2);
            cv.put(FakeDBHelper.TABLE1_COLUMN_NAME, "testRow2");
            DBOperations.save(FakeDBHelper.TABLE1_NAME, cv);

            // Fetch all rows from the table
            Cursor cursor = mFakeDbHelper.getWritableDatabase().rawQuery(
                    "SELECT * FROM " + FakeDBHelper.TABLE1_NAME, null);

            // There should be two (one from setUp() and one we added now)
            assertNotNull(cursor);
            assertEquals(2, cursor.getCount());
        }

        @After
        public void tearDown() {
            mFakeDbHelper.close();
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class)
    public static class DBOperationsWithoutSetupTest {

        @Test(expected = IllegalStateException.class)
        public void should_ThrowException_When_Querying() {
            DBOperations.query(TableInfos.BoardTable.NAME);
        }

        @Test(expected = IllegalStateException.class)
        public void should_ThrowException_When_Inserting() {
            DBOperations.save(TableInfos.BoardTable.NAME, null);
        }
    }
}