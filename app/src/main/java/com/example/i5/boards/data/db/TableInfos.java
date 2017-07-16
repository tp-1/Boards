package com.example.i5.boards.data.db;

/**
 * Information on tables. Maybe separate them in different classes?<br/>
 * Members follow format:
 * <pre>
 * class Table... {
 *     NAME = ...
 *     class ColumnNames extends ColumnNamesUniversal {
 *         ...
 *     }
 * }
 * </pre>
 */
public class TableInfos {

    static class ColumnNamesUniversal {
        /** Id column of a row that every table should have */
        static final String ID = "_id";
    }

    public static class BoardTable {
        public static final String NAME = "Board";

        public static class ColumnNames extends ColumnNamesUniversal{
            /** Name of the board. Doesn't have to be unique */
            public static final String NAME = "name";
            /** Start time of the sprint represented by this board. Number of millis since epoch  */
            public static final String START_TIME = "start_time";
            //TODO: use with shared prefs' sprint length. probably will need another column since user can change sprint length in the middle of sprint
        }
    }

    public static class StoryTable {
        public static final String NAME = "Story";

        public static class ColumnNames extends ColumnNamesUniversal {
            /** Name of the story. Doesn't have to be unique */
            public static final String NAME = "name";
            /** Description of the story. */
            public static final String DESCRIPTION = "desc";
        }
    }

    public static class IssueTable {
        public static final String NAME = "Issue";

        public static class ColumnNames extends ColumnNamesUniversal {
            /** "foreign key" to board table */
            public static final String BOARD_KEY = "board_key";
            /** "foreign key" to story table */
            public static final String STORY_KEY = "story_key";
            /** Name of the issue. Doesn't have to be unique */
            public static final String NAME = "name";
            public static final String STATUS = "status";
            /** Description of the issue. */
            public static final String DESCRIPTION = "desc";
            /** Estimated time it will take for issue to be finished. In millis. */
            public static final String ESTIMATED = "estimated_time";
            /** Time the user already logged on the issue. In millis. */
            public static final String LOGGED = "logged_time";
            /**
             * Time remaining on the issue. In millis.
             * <p>
             *     {@value #REMAINING} = {@value #ESTIMATED} - {@value #LOGGED},
             *     if >= 0, otherwise 0
             * </p>
             */
            public static final String REMAINING = "remaining_time";
        }
    }
}