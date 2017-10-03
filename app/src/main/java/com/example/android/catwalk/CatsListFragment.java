package com.example.android.catwalk;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.catwalk.data.CatContract;

public class CatsListFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CatCursorAdapter mCursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView catListView = (ListView) view.findViewById(R.id.list);

        mCursorAdapter = new CatCursorAdapter(view.getContext(), null);

        View emptyView = view.findViewById(R.id.empty);
        catListView.setEmptyView(emptyView);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                CatContract.CatEntry._ID,
                CatContract.CatEntry.COLUMN_ITEM_NAME
        };

        return new android.support.v4.content.CursorLoader(getView().getContext(),
                CatContract.CatEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
