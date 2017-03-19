package ru.n_develop.n_calcs.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class ListCalcsActivity extends AppCompatActivity
{

    public int idType;

    DBHelper dbHelper;
    SQLiteDatabase database;

    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        idType = getIntent().getExtras().getInt("id_type");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_calcs);

        // получаем экземпляр элемента ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        // масиив для хранени названий калькулятор и их id
        final ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_CALCS,
                new String[]{DBHelper.KEY_ID_CALCS, DBHelper.KEY_TITLE_CALCS},
                DBHelper.KEY_ID_TYPE_CALCS + " = ?",
                new String[]{Integer.toString(idType)},
                null, null, null);

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_CALCS);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE_CALCS);

            do
            {
                // метод put добавляет с верху массива, поэтому id выше name
                map = new HashMap<String, String>();
                map.put("1_name", cursor.getString(titleIndex));
                map.put("0_id", cursor.getString(idIndex));
                myArrList.add(map);
            }
            while (cursor.moveToNext());
        } else
        {
            Log.e("else list", "0 rows");
        }
        adapter = new SimpleAdapter(this, myArrList, android.R.layout.simple_list_item_1,
                new String[]{"1_name", "0_id"},
                new int[]{android.R.id.text1});

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                HashMap hashMap = myArrList.get(position);
                Collection<String> collection = hashMap.values();
                String[] values = collection.toArray(new String[0]);

                Intent intent = new Intent(ListCalcsActivity.this, CalcActivity.class);
                intent.putExtra("id_calcs", values[0]);
                startActivity(intent);

            }
        });
    }
}
