package com.example.android.catwalk;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.catwalk.data.CatContract;

public class CatCursorAdapter extends CursorAdapter {

    public CatCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.name);
        final Uri currentProductUri = ContentUris.withAppendedId(CatContract.CatEntry.CONTENT_URI, cursor.getInt(cursor.getColumnIndexOrThrow((CatContract.CatEntry._ID))));
        int nameColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_NAME);
        String catName = cursor.getString(nameColumnIndex);
        nameView.setText(catName);
    }
}
