package com.example.i5.boards.ui.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i5.boards.ALog;
import com.example.i5.boards.R;
import com.example.i5.boards.data.Story;
import com.example.i5.boards.data.db.DBOperations;

/**
 * Fragment for managing the selected Board with list of it's stories and issues.
 * Board is drawn like this:
 * <ul>
 *     <li>Board name in the action bar</li>
 *     <li>List of stories ({@link StoryOnBoard}) in a ListView</li>
 *     <ul>
 *         <li>Every story inflates it's issues ({@link IssueOnBoard}),
 *         putting them in it's status columns</li>
 *     </ul>
 *     <li>Board is redrawn when user adds another issue</li>
 * </ul>
 *
 * Upon coming back from {@link com.example.i5.boards.ui.newItem.NewIssueActivity} with
 * {@link android.app.Activity#RESULT_OK}, board screen will be refreshed and data loaded again
 */
public class BoardFragment extends Fragment {
    /**
     * Tag used for logs. Tag for handling this fragment with FragmentManager is
     * declared in the xml (never mind, deleted it. Still, should be followed)
     */
    private static final String TAG = BoardFragment.class.getSimpleName();

    private StoryOnBoard mStoryOnBoard;
    //ListView mBoardItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_board, container);
        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ALog.v(TAG, ALog.UI, "View created for BoardFragment");

        DBOperations.setupDatabase(getActivity());
        //mBoardItems = (ListView) view.findViewById(R.id.boardItems);
        // mBoardItems.setAdapter(new StoryAdapter(this, , 0));

        loadData(view);
    }

    /**
     * Load data for this board and draw the UI
     * @param view Fragment's view
     */
    private void loadData(View view) {
        ALog.d(TAG, ALog.UI, "About to load data and draw UI for fragment...");

        View storyView = view.findViewById(R.id.story_on_board);

        // temporarily here, shouldn't be necessary when using ListView
        ((ViewGroup)storyView.findViewById(R.id.toDoColumnItems)).removeAllViews();
        ((ViewGroup)storyView.findViewById(R.id.progressColumnItems)).removeAllViews();
        ((ViewGroup)storyView.findViewById(R.id.doneColumnItems)).removeAllViews();

        // FIXME: 09-Jul-17 restoreFromId a story with id = 1 and boardId = 1
        // FIXME: 09-Jul-17 DATABASE ACCESS
        mStoryOnBoard = new StoryOnBoard(storyView, Story.restoreFromId(1), 1);
        mStoryOnBoard.loadData();
        mStoryOnBoard.draw(getContext());
    }

    /**
     * Call when you need to reload data in current fragment and redraw the UI
     */
    /* package */ void reload() {
        ALog.d(TAG, ALog.UI, "Reload fragment");
        View view = getView();
        if (view != null) {
            loadData(view);
        }
    }
}