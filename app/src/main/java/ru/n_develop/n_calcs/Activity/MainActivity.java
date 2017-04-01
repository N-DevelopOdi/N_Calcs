package ru.n_develop.n_calcs.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.Date;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class MainActivity extends AppCompatActivity
{
    DBHelper dbHelper;
    SQLiteDatabase database;

    int idSubclass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();

    }

    public void CategoriesMath(View view)
    {
        Cursor cursor = database.query(DBHelper.TABLE_SUBCLASS,
                new String[] {DBHelper.KEY_ID_SUBCLASS},
                "name = ?",
                new String[] {"Math"},
                null, null, null);

        if (cursor.moveToFirst())
        {
            int idSubClassCursor = cursor.getColumnIndex(DBHelper.KEY_ID_SUBCLASS);
            idSubclass = cursor.getInt(idSubClassCursor);
        }
        else
        {
            Log.e("else", "0 rows");
        }

        Intent intent = new Intent(MainActivity.this, TypesActivity.class);
        intent.putExtra("id_subclass", idSubclass);
        startActivity(intent);

    }

    public void CategoriesPhysics(View view)
    {
        Cursor cursor = database.query(DBHelper.TABLE_SUBCLASS,
                new String[] {DBHelper.KEY_ID_SUBCLASS},
                "name = ?",
                new String[] {"Physics"},
                null, null, null);

        if (cursor.moveToFirst())
        {
            int idSubClassCursor = cursor.getColumnIndex(DBHelper.KEY_ID_SUBCLASS);
            idSubclass = cursor.getInt(idSubClassCursor);
        }
        else
        {
            Log.e("else", "0 rows");
        }

        Intent intent = new Intent(MainActivity.this, TypesActivity.class);
        intent.putExtra("id_subclass", idSubclass);
        startActivity(intent);
    }

    public void CategoriesFinance(View view)
    {

        Date date = new Date();

        XmlPullParser parser = getResources().getXml(R.xml.calcs_geometry);

        ContentValues contentValuesCalc = new ContentValues();
        ContentValues contentValuesFormula = new ContentValues();

        try
        {
            // продолжаем, пока не достигнем конца документа
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT)
            {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("calc"))
                {

                    contentValuesCalc.put(DBHelper.KEY_ID_TYPE_CALCS, parser.getAttributeValue(0));
                    contentValuesCalc.put(DBHelper.KEY_TITLE_CALCS, parser.getAttributeValue(1));
                    contentValuesCalc.put(DBHelper.KEY_DATE_CREATED, date.toString());
                    contentValuesCalc.put(DBHelper.KEY_DATE_UPDATED, date.toString());

                    long id_calc = database.insert(DBHelper.TABLE_CALCS, null, contentValuesCalc);

                    String[] formula = parser.getAttributeValue(3).split("_");
                    String[] text_about = parser.getAttributeValue(4).split("_");
                    String[] image = parser.getAttributeValue(5).split("_");


                    for (int i = 0; i < formula.length; i++)
                    {
                        contentValuesFormula.put(DBHelper.KEY_ID_CALCS_FORMULA, id_calc);
                        contentValuesFormula.put(DBHelper.KEY_RESULT, parser.getAttributeValue(2));
                        contentValuesFormula.put(DBHelper.KEY_FORMULA, formula[i]);

                        if (text_about.length > i && text_about[i] != null)
                        {
                            contentValuesFormula.put(DBHelper.KEY_TEXT_ABOUT, text_about[i]);
                        }
                        else
                        {
                            contentValuesFormula.put(DBHelper.KEY_TEXT_ABOUT, "Нет описания");
                        }

                        if (image.length > i && image[i] != null)
                        {
                            contentValuesFormula.put(DBHelper.KEY_IMAGE, image[i]);
                        }
                        else
                        {
                            contentValuesFormula.put(DBHelper.KEY_IMAGE, "");
                        }
                        contentValuesFormula.put(DBHelper.KEY_DATE_CREATED, date.toString());
                        contentValuesFormula.put(DBHelper.KEY_DATE_UPDATED, date.toString());

                        database.insert(DBHelper.TABLE_FORMULS, null, contentValuesFormula);

                    }
                }
                parser.next();
            }
        }
        catch (Throwable t)
        {
            Toast.makeText(this,
                    "Ошибка при загрузке XML-документа: " + t.toString(), Toast.LENGTH_LONG)
                    .show();
        }

//        Cursor cursor = database.query(DBHelper.TABLE_SUBCLASS,
//                new String[] {DBHelper.KEY_ID_SUBCLASS},
//                "name = ?",
//                new String[] {"Finance"},
//                null, null, null);
//
//        if (cursor.moveToFirst())
//        {
//            int idSubClassCursor = cursor.getColumnIndex(DBHelper.KEY_ID_SUBCLASS);
//            idSubclass = cursor.getInt(idSubClassCursor);
//        }
//        else
//        {
//            Log.e("else", "0 rows");
//        }

//        Intent intent = new Intent(MainActivity.this, TypesActivity.class);
//        intent.putExtra("id_subclass", idSubclass);
//        startActivity(intent);
    }

    public void del(View view)
    {
        database.execSQL("DELETE FROM " + DBHelper.TABLE_CALCS);
        database.execSQL("DELETE FROM " + DBHelper.TABLE_FORMULS);
    }
}
