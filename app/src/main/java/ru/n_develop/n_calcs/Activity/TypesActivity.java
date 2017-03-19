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
import java.util.Map;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class TypesActivity extends AppCompatActivity
{

    public int idSubclass;

    DBHelper dbHelper;
    SQLiteDatabase database;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        idSubclass = getIntent().getExtras().getInt("id_subclass");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.types);

        // получаем экземпляр элемента ListView
        final ListView listView = (ListView)findViewById(R.id.listView);
        // масиив для хранени названий калькулятор и их id
        final ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_TYPE,
                new String[] {DBHelper.KEY_ID_TYPE, DBHelper.KEY_TITLE_TYPE},
                DBHelper.KEY_ID_SUBCLASS_TYPE + " = ?",
                new String[] {Integer.toString(idSubclass)},
                null, null, DBHelper.KEY_TITLE_TYPE + " ASC");

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_TYPE);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE_TYPE);

            do
            {
                // метод put добавляет с верху массива, поэтому id выше name
                map = new HashMap<String, String>();
                map.put("1_name", cursor.getString(titleIndex));
                map.put("0_id", cursor.getString(idIndex));
                myArrList.add(map);
            }
            while (cursor.moveToNext());
        }
        else
        {
            Log.e("else", "0 rows");
        }
//        final String[] catnames = getResources().getStringArray(R.array.cat_names);
////        Log.e("111111111", catnames[0]);
//        catNamesList = new ArrayList<>(Arrays.asList(catnames));
//

        adapter = new SimpleAdapter(this, myArrList, android.R.layout.simple_list_item_1,
                new String[] {"1_name", "0_id"},
                new int[] {android.R.id.text1});

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap hashMap = myArrList.get(position);
                Collection<String> collection = hashMap.values();
                String[] values = collection.toArray(new String[0]);

                Intent intent = new Intent(TypesActivity.this, ListCalcsActivity.class);
                intent.putExtra("id_type", Integer.parseInt(values[0]));
                startActivity(intent);

            }
        });
    }
}