package ru.n_develop.n_calcs.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;

/**
 * Created by dim90 on 18.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper
{

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Calc";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_CALCS = "calcs";


    // _ нужен для работы с курсорами, это особенность android
    public static final String KEY_ID_CATEGORY = "_id_category";
    public static final String KEY_NAME_CATEGORY = "name";
    public static final String KEY_TITLE_CATEGORY = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DATE_CREATED = "date_created";
    public static final String KEY_DATE_UPDATED = "date_updated";

    public static final String KEY_ID_CALCS = "_id_calcs";
//    public static final String KEY_ID_CATEGORY = "id_category";
    public static final String KEY_TITLE_CALCS = "title";
//    public static final String KEY_IMAGE = "image";
    public static final String KEY_FORMULA = "formula";
//    public static final String KEY_DATE_CREATED = "date_created";
//    public static final String KEY_DATE_UPDATED = "date_updated";



    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        ContentValues contentValuesCat = new ContentValues();

        String[] categories_name =  {"Math", "Physics", "Finance"};
        String[] categories_title =  {"Математика", "Физика", "Финансы"};


        db.execSQL("CREATE TABLE " + TABLE_CATEGORY +
                " ( " + KEY_ID_CATEGORY + " integer primary key," +
                KEY_NAME_CATEGORY + " text, " +
                KEY_TITLE_CATEGORY + " text, " +
                KEY_IMAGE + " text, " +
                KEY_DATE_CREATED + " text, " +
                KEY_DATE_UPDATED + " text) "
        );

        for (int i = 0; i < categories_name.length; i++  )
        {
            contentValuesCat.put(KEY_NAME_CATEGORY, categories_name[i]);
            contentValuesCat.put(KEY_TITLE_CATEGORY, categories_title[i]);
            contentValuesCat.put(KEY_IMAGE, "");
            contentValuesCat.put(KEY_DATE_CREATED, "");
            contentValuesCat.put(KEY_DATE_UPDATED, "");

            db.insert(TABLE_CATEGORY, null, contentValuesCat);
        }

        // создаем таблицу для калькуляторов
        db.execSQL("CREATE TABLE " + TABLE_CALCS +
                " ( " + KEY_ID_CALCS + " integer primary key," +
                KEY_ID_CATEGORY + " integer, " +
                KEY_TITLE_CALCS + " text, " +
                KEY_IMAGE + " text, " +
                KEY_FORMULA + " text, " +
                KEY_DATE_CREATED + " text, " +
                KEY_DATE_UPDATED + " text) "
        );


        ContentValues contentValuesCalcs = new ContentValues();

        String[] calcs_title = {"Площадь квадрата", "Площадь квадрата"};
        int[] calcs_category = {1, 1};
        String[] calcs_formula = {"a^2", "d^2/2"};

        for (int i = 0; i < calcs_title.length; i++  )
        {
            contentValuesCalcs.put(KEY_ID_CATEGORY, calcs_category[i]);
            contentValuesCalcs.put(KEY_TITLE_CALCS, calcs_title[i]);
            contentValuesCalcs.put(KEY_IMAGE, "");
            contentValuesCalcs.put(KEY_FORMULA, calcs_formula[i]);
            contentValuesCalcs.put(KEY_DATE_CREATED, "");
            contentValuesCalcs.put(KEY_DATE_UPDATED, "");

            db.insert(TABLE_CALCS, null, contentValuesCalcs);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_CATEGORY);
        db.execSQL("drop table if exists " + TABLE_CALCS);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_CATEGORY);
        db.execSQL("drop table if exists " + TABLE_CALCS);

        onCreate(db);
    }
}
