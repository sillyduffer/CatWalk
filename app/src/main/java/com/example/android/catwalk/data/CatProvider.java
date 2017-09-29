package com.example.android.catwalk.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class CatProvider extends ContentProvider{

    private static final int CATS = 100;
    private static final int CAT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String LOG_TAG = CatProvider.class.getSimpleName();

    private CatDbHelper mDbHelper;

    static {
        sUriMatcher.addURI(CatContract.CONTENT_AUTHORITY, CatContract.PATH_CATS, CATS);

        sUriMatcher.addURI(CatContract.CONTENT_AUTHORITY, CatContract.PATH_CATS + "/#", CAT_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new CatDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case CATS:
                cursor = database.query(CatContract.CatEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CAT_ID:
                selection = CatContract.CatEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(CatContract.CatEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CATS:
                return CatContract.CatEntry.CONTENT_LIST_TYPE;
            case CAT_ID:
                return CatContract.CatEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CATS:
                return insertProduct(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values) {

        String name = values.getAsString(CatContract.CatEntry.COLUMN_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Product requires a name");
        }

        String gender = values.getAsString(CatContract.CatEntry.COLUMN_ITEM_GENDER);
        if (gender == null) {
            throw new IllegalArgumentException("Product requires valid quantity");
        }

        String price = values.getAsString(CatContract.CatEntry.COLUMN_ITEM_COLOUR);
        if (price == null || price.isEmpty()) {
            throw new IllegalArgumentException("Product requires valid colour");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(CatContract.CatEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CATS:
                rowsDeleted = database.delete(CatContract.CatEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CAT_ID:
                selection = CatContract.CatEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(CatContract.CatEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CATS:
                return updateProduct(uri, values, selection, selectionArgs);
            case CAT_ID:
                selection = CatContract.CatEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(CatContract.CatEntry.COLUMN_ITEM_NAME)) {
            String name = values.getAsString(CatContract.CatEntry.COLUMN_ITEM_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if (values.containsKey(CatContract.CatEntry.COLUMN_ITEM_COLOUR)) {
            String colour = values.getAsString(CatContract.CatEntry.COLUMN_ITEM_COLOUR);
            if (colour == null) {
                throw new IllegalArgumentException("Product requires valid quantity");
            }
        }

        if (values.containsKey(CatContract.CatEntry.COLUMN_ITEM_GENDER)) {
            Integer gender = values.getAsInteger(CatContract.CatEntry.COLUMN_ITEM_GENDER);
            if (gender == null) {
                throw new IllegalArgumentException("Product requires valid price");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(CatContract.CatEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

}
