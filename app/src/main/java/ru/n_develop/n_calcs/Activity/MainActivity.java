package ru.n_develop.n_calcs.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.Module.ExportStatics;
import ru.n_develop.n_calcs.R;

public class MainActivity extends AppCompatActivity
{
    DBHelper dbHelper;
    SQLiteDatabase database;

    ExportStatics exportStatics;

    int idSubclass;

    private List<Integer> id_formula = new ArrayList<Integer>();
    private List<Integer> id_calc = new ArrayList<Integer>();
    private List<Integer> count = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();

        if (hasConnection(this))
        {
            long lastDate = __getLastExportDate();
            Date date = new Date();
            //86400 - день
            if(date.getTime() / 1000 - lastDate > 86400 )
            {
                String jsonData = __getExportData();
                Log.e("jsonData", jsonData);

                if (!jsonData.equals(""))
                {
                    exportStatics = new ExportStatics();
                    exportStatics.start(jsonData);

                    try
                    {
                        exportStatics.join();// ждем зовершения потока
                    } catch (InterruptedException ie)
                    {
                        Log.e("pass 0", ie.getMessage());
                    }
                    if (exportStatics.result())
                    {
                        database.execSQL("UPDATE " + DBHelper.TABLE_FORMULS +
                                " SET " + DBHelper.KEY_COUNT + " = 0 " +
                                " WHERE " + DBHelper.KEY_COUNT + " > 0 ");

                        __setLastExportDate();
                    }
                }
            }
        }
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

        Cursor cursor = database.query(DBHelper.TABLE_SUBCLASS,
                new String[] {DBHelper.KEY_ID_SUBCLASS},
                "name = ?",
                new String[] {"Finance"},
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

    public void createDB (View view)
    {
        Date date = new Date();

        XmlPullParser parser = getResources().getXml(R.xml.calcs_mechanics);

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
    }

    public void del(View view)
    {
        database.execSQL("DELETE FROM " + DBHelper.TABLE_CALCS);
        database.execSQL("DELETE FROM " + DBHelper.TABLE_FORMULS);
    }

    /**
     * Некоторые особенности

     1) Если смартфон подключен к Wi-Fi, то метод вернет true. Даже если интернет не оплачен или из роутера выдернут шнур, то метод все равно вернет true.

     2) Если смартфон подключен к мобильной сети, но интернет не оплачен, то метод вернет true.
     * @return
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            Log.e("wifi", Boolean.toString(wifiInfo.isConnected()));
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            Log.e("mob", wifiInfo.toString());

            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            Log.e("3`", wifiInfo.toString());

            return true;
        }
        return false;
    }

    private long __getLastExportDate ()
    {
        // Получаем время последней выгрузки
        Cursor cursor = database.query(DBHelper.TABLE_IMPORT,
                new String[] {DBHelper.KEY_ID_IMPORT, DBHelper.KEY_IMPORT_NAME, DBHelper.KEY_LAST_IMPORT},
                "import_name = ?",
                new String[] {"statistics"}, null, null, null);

        Date date = new Date();
        Log.e("lastDate1", Long.toString(date.getTime()));


        Long lastDate = (long) 0;
        if (cursor.moveToFirst())
        {
            int lastDateCursor = cursor.getColumnIndex(DBHelper.KEY_LAST_IMPORT);
            lastDate = cursor.getLong(lastDateCursor);
            lastDate = lastDate / 1000;
        }

        return lastDate;
    }
    private void __setLastExportDate ()
    {
        Date date = new Date();
        database.execSQL("UPDATE " + DBHelper.TABLE_IMPORT +
                " SET " + DBHelper.KEY_LAST_IMPORT + " = " + date.getTime() +
                " WHERE " + DBHelper.KEY_IMPORT_NAME + " = 'statistics'");

    }

    private String __getExportData()
    {
        try
        {
            JSONObject resultJson = new JSONObject();

            // Получаем список калькуляторов с счетчиками
            Cursor cursor = database.query(DBHelper.TABLE_FORMULS,
                    new String[]{DBHelper.KEY_FORMULA_ID, DBHelper.KEY_ID_CALCS_FORMULA, DBHelper.KEY_COUNT},
                    DBHelper.KEY_COUNT + " > 0 ",
                    null, null, null, null);

            if (cursor.moveToFirst())
            {
                int idFormulaIndex = cursor.getColumnIndex(DBHelper.KEY_FORMULA_ID);
                int idCalcsIndex = cursor.getColumnIndex(DBHelper.KEY_ID_CALCS_FORMULA);
                int CountIndex = cursor.getColumnIndex(DBHelper.KEY_COUNT);


                JSONArray jsonArr = new JSONArray();
                do
                {
                    id_formula.add(cursor.getInt(idFormulaIndex));
                    id_calc.add(cursor.getInt(idCalcsIndex));
                    count.add(cursor.getInt(CountIndex));

                    JSONObject pnObj = new JSONObject();
                    pnObj.put("id_calc", cursor.getInt(idFormulaIndex));
                    pnObj.put("id_formula", cursor.getInt(idCalcsIndex));
                    pnObj.put("count", cursor.getInt(CountIndex));
                    jsonArr.put(pnObj);
                }
                while (cursor.moveToNext());

                return jsonArr.toString();
            }
        }
        catch (Exception e)
        {
            Log.e("Error JSON", "");
        }
        return "";
    }
}
