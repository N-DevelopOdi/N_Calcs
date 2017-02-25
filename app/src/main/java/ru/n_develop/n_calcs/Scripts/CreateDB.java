package ru.n_develop.n_calcs.Scripts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;

import java.util.Date;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

/**
 * Created by dim90 on 23.02.2017.
 */

public class CreateDB extends AppCompatActivity
{

    DBHelper dbHelper;
    SQLiteDatabase database;

    public void createDB (Context context)
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


                    Log.e("text_about", Long.toString(text_about.length));
                    for (int i = 0; i < text_about.length; i++)
                    {
                        contentValuesFormula.put(DBHelper.KEY_ID_CALCS_FORMULA, id_calc);
                        contentValuesFormula.put(DBHelper.KEY_RESULT, parser.getAttributeValue(2));
                        contentValuesFormula.put(DBHelper.KEY_FORMULA, formula[i]);
                        contentValuesFormula.put(DBHelper.KEY_IMAGE, "");
                        contentValuesFormula.put(DBHelper.KEY_DATE_CREATED, date.toString());
                        contentValuesFormula.put(DBHelper.KEY_DATE_UPDATED, date.toString());

                        database.insert(DBHelper.TABLE_FORMULS, null, contentValuesFormula);

                        Log.e("toString", text_about[i]);
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
    }
}
