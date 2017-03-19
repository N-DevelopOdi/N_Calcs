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

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Calc";
    public static final String TABLE_SUBCLASS = "subclass";
    public static final String TABLE_TYPE = "type";
    public static final String TABLE_CALCS = "calcs";
    public static final String TABLE_FORMULS = "formuls";

    /**
     * Таблица подклассов
     */
    // _ нужен для работы с курсорами, это особенность android
    public static final String KEY_ID_SUBCLASS= "_id_subclass";
    public static final String KEY_NAME_SUBCLASS = "name";
    public static final String KEY_TITLE_SUBCLASS = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DATE_CREATED = "date_created";
    public static final String KEY_DATE_UPDATED = "date_updated";

    /**
     * Таблица типо калькуляторов
     */
    // _ нужен для работы с курсорами, это особенность android
    public static final String KEY_ID_TYPE= "_id_type";
    public static final String KEY_ID_SUBCLASS_TYPE = "id_subclass";
    public static final String KEY_NAME_TYPE = "name";
    public static final String KEY_TITLE_TYPE = "title";
//    public static final String KEY_DATE_CREATED = "date_created";
//    public static final String KEY_DATE_UPDATED = "date_updated";

    /**
     * Таблица калькуляторов
     */
    public static final String KEY_ID_CALCS = "_id_calcs";
    public static final String KEY_ID_TYPE_CALCS = "id_type";
    public static final String KEY_TITLE_CALCS = "title";
//    public static final String KEY_DATE_CREATED = "date_created";
//    public static final String KEY_DATE_UPDATED = "date_updated";

    /**
     * Таблица с формулами
     */
    public static final String KEY_FORMULA_ID = "_id_formula";
    public static final String KEY_ID_CALCS_FORMULA = "id_calcs";
    public static final String KEY_RESULT = "result";
    public static final String KEY_FORMULA = "formula";
//    public static final String KEY_IMAGE = "image";
    public static final String KEY_TEXT_ABOUT = "text_about";
//    public static final String KEY_DATE_CREATED = "date_created";
//    public static final String KEY_DATE_UPDATED = "date_updated";



    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /**
         * Создаем таблицу с подкатегориями
         */
        ContentValues contentValuesSubclass = new ContentValues();

        String[] subclass_name =  {"Math", "Physics", "Finance"};
        String[] subclass_title =  {"Математика", "Физика", "Финансы"};

        db.execSQL("CREATE TABLE " + TABLE_SUBCLASS +
                " ( " + KEY_ID_SUBCLASS + " integer primary key, " +
                KEY_NAME_SUBCLASS + " text, " +
                KEY_TITLE_SUBCLASS + " text, " +
                KEY_IMAGE + " text, " +
                KEY_DATE_CREATED + " text, " +
                KEY_DATE_UPDATED + " text) "
        );

        for (int i = 0; i < subclass_name.length; i++  )
        {
            contentValuesSubclass.put(KEY_NAME_SUBCLASS, subclass_name[i]);
            contentValuesSubclass.put(KEY_TITLE_SUBCLASS, subclass_title[i]);
            contentValuesSubclass.put(KEY_IMAGE, "");
            contentValuesSubclass.put(KEY_DATE_CREATED, "");
            contentValuesSubclass.put(KEY_DATE_UPDATED, "");

            db.insert(TABLE_SUBCLASS, null, contentValuesSubclass);
        }

        /**
         * Создаем таблицу с типами калькуляторов
         */
        ContentValues contentValuesType = new ContentValues();

        Integer[] subclass_id =  {1,1};
        String[] type_name =  {"Geometry", "Algebra"};
        String[] type_title =  {"Геометрия", "Алгебра"};

        db.execSQL("CREATE TABLE " + TABLE_TYPE +
                " ( " + KEY_ID_TYPE + " integer primary key, " +
                KEY_ID_SUBCLASS_TYPE + " integer, " +
                KEY_NAME_TYPE + " text, " +
                KEY_TITLE_TYPE + " text, " +
                KEY_DATE_CREATED + " text, " +
                KEY_DATE_UPDATED + " text) "
        );

        for (int i = 0; i < type_name.length; i++  )
        {
            contentValuesType.put(KEY_ID_SUBCLASS_TYPE, subclass_id[i]);
            contentValuesType.put(KEY_NAME_SUBCLASS, type_name[i]);
            contentValuesType.put(KEY_TITLE_SUBCLASS, type_title[i]);
            contentValuesType.put(KEY_DATE_CREATED, "");
            contentValuesType.put(KEY_DATE_UPDATED, "");

            db.insert(TABLE_TYPE, null, contentValuesType);
        }


        /**
         * Cоздаем таблицу для калькуляторов
         */
        db.execSQL("CREATE TABLE " + TABLE_CALCS +
                " ( " + KEY_ID_CALCS + " integer primary key, " +
                KEY_ID_TYPE_CALCS + " integer, " +
                KEY_TITLE_CALCS + " text, " +
                KEY_DATE_CREATED + " text, " +
                KEY_DATE_UPDATED + " text) "
        );

//        ContentValues contentValuesCalcs = new ContentValues();
//
//        String[] calcs_title = {"Площадь квадрата"};
//        int[] calcs_category = {1};
//
//        for (int i = 0; i < calcs_title.length; i++  )
//        {
//            contentValuesCalcs.put(KEY_ID_TYPE_CALCS, calcs_category[i]);
//            contentValuesCalcs.put(KEY_TITLE_CALCS, calcs_title[i]);
//            contentValuesCalcs.put(KEY_DATE_CREATED, "");
//            contentValuesCalcs.put(KEY_DATE_UPDATED, "");
//
//            db.insert(TABLE_CALCS, null, contentValuesCalcs);
//        }

        /**
         * Cоздаем таблицу с формулами
         */
        db.execSQL("CREATE TABLE " + TABLE_FORMULS +
                " ( " + KEY_FORMULA_ID + " integer primary key, " +
                KEY_ID_CALCS_FORMULA + " integer, " +
                KEY_RESULT + " text, " +
                KEY_FORMULA + " text, " +
                KEY_IMAGE + " text, " +
                KEY_TEXT_ABOUT + " text, " +
                KEY_DATE_CREATED + " text, " +
                KEY_DATE_UPDATED + " text) "
        );

//        ContentValues contentValuesFormula = new ContentValues();
//
//        int[] id_calcs = {1, 1, 1, 2};
//        String[] calcs_result = {"S", "S", "P", "R"};
//        String[] calcs_formula = {"a*a*b", "d*d/2", "c+d*2", "a+b+c/2"};
//
//        for (int i = 0; i < calcs_formula.length; i++  )
//        {
//            contentValuesFormula.put(KEY_ID_CALCS_FORMULA, id_calcs[i]);
//            contentValuesFormula.put(KEY_RESULT, calcs_result[i]);
//            contentValuesFormula.put(KEY_FORMULA, calcs_formula[i]);
//            contentValuesFormula.put(KEY_IMAGE, "");
//            contentValuesFormula.put(KEY_DATE_CREATED, "");
//            contentValuesFormula.put(KEY_DATE_UPDATED, "");
//
//            db.insert(TABLE_FORMULS, null, contentValuesFormula);
//        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_SUBCLASS);
        db.execSQL("drop table if exists " + TABLE_TYPE);
        db.execSQL("drop table if exists " + TABLE_CALCS);
        db.execSQL("drop table if exists " + TABLE_FORMULS);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_SUBCLASS);
        db.execSQL("drop table if exists " + TABLE_TYPE);
        db.execSQL("drop table if exists " + TABLE_CALCS);
        db.execSQL("drop table if exists " + TABLE_FORMULS);

        onCreate(db);
    }
}
