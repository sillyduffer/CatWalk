package com.example.android.catwalk;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailsFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private TextView mName;
    private TextView mColour;
    private TextView mBreed;
    private TextView mLocation;
    private TextView mGender;
    private ImageView mImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mName = (TextView) view.findViewById(R.id.detail_name);
        mColour = (TextView) view.findViewById(R.id.detail_colour);
        mBreed = (TextView) view.findViewById(R.id.detail_breed);
        mLocation = (TextView) view.findViewById(R.id.detail_location);
        mGender = (TextView) view.findViewById(R.id.detail_gender);
        mImage = (ImageView) view.findViewById(R.id.detail_image);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
//    int nameColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_NAME);
//        int breedColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_BREED);
//        int colourColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_COLOUR);
//        int genderColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_GENDER);
//        int locationColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_LOCATION);
//        int imageColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_IMAGE);