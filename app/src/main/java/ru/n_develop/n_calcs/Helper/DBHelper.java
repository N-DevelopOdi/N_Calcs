package ru.n_develop.n_calcs.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dim90 on 18.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper
{

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Calc";
    public static final String TABLE_CATEGORY = "category";


    // _ нужен для работы с курсорами, это особенность android
    public static final String KEY_ID_CATEGIRY = "_id_category";
    public static final String KEY_NAME_CATEGORY = "name_category";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DATE_CREATED = "date_created";
    public static final String KEY_DATE_UPDATED = "date_updated";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();

        db.execSQL("CREATE TABLE " + TABLE_CATEGORY +
                " ( " + KEY_ID_CATEGIRY + " integer primary key," +
                KEY_NAME_CATEGORY + " text, " +
                KEY_IMAGE + " text, " +
                KEY_DATE_CREATED + " text, " +
                KEY_DATE_UPDATED + " text) "
        );

        contentValues.put(KEY_NAME_CATEGORY, "Математика");
        contentValues.put(KEY_IMAGE, "");
        contentValues.put(KEY_DATE_CREATED, "");
        contentValues.put(KEY_DATE_UPDATED, "");

        db.insert(TABLE_CATEGORY, null, contentValues);

        contentValues.put(KEY_NAME_CATEGORY, "Физика");
        contentValues.put(KEY_IMAGE, "");
        contentValues.put(KEY_DATE_CREATED, "");
        contentValues.put(KEY_DATE_UPDATED, "");

        db.insert(TABLE_CATEGORY, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_CATEGORY);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_CATEGORY);

        onCreate(db);
    }
}
