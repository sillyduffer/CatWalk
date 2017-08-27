package com.example.android.catwalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DetailsFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
//        int nameColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_NAME);
//        int breedColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_BREED);
//        int colourColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_COLOUR);
//        int genderColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_GENDER);
//        int locationColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_LOCATION);
//        int imageColumnIndex = cursor.getColumnIndex(CatContract.CatEntry.COLUMN_ITEM_IMAGE);
    }
}
