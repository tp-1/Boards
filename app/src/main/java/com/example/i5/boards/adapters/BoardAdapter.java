package com.example.i5.boards.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.example.i5.boards.data.Board;
import com.example.i5.boards.data.db.TableInfos;

/**
 * Adapts {@value TableInfos.BoardTable#NAME} table cursor information into a given layout.
 * Subclasses need to implement {@link #bindView(View, Context, Cursor)} method.
 */
abstract public class BoardAdapter extends CursorAdapter {
    private int mLayout;

    protected BoardAdapter(Context context, int layout) {
        super(context, Board.getAll(), 0);
        mLayout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(mLayout, parent, false);
    }

    @Override
    abstract public void bindView(View view, Context context, Cursor cursor);
}
