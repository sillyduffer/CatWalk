package com.example.android.catwalk.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class CatContract {

    //Empty private constructor in order that the class is not initialised
    private CatContract(){
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.catwalk";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CATS = "cats";

    public static final class CatEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CATS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATS;

        public final static String TABLE_NAME = "cats";

        public final static String COLUMN_ITEM_NAME = "name";

        public final static String COLUMN_ITEM_COLOUR = "colour";

        public final static String COLUMN_ITEM_BREED = "breed";

        public final static String COLUMN_ITEM_LOCATION = "location";

        public final static String COLUMN_ITEM_IMAGE = "image";

        public final static String COLUMN_ITEM_GENDER = "gender";

        public final static String GENDER_MALE = "male";
        public final static String GENDER_FEMALE = "female";
        public final static String GENDER_NONBINARY = "non-binary";
        public final static String GENDER_UNKNOWN = "unknown";

    }

}
