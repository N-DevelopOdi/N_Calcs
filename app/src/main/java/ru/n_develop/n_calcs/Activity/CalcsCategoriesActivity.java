package ru.n_develop.n_calcs.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class CalcsCategoriesActivity extends AppCompatActivity
{

    public int idCategories;

    DBHelper dbHelper;
    SQLiteDatabase database;

    private ArrayList<String> catNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        idCategories = getIntent().getExtras().getInt("id_categories");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcs_categories);

        // получаем экземпляр элемента ListView
        final ListView listView = (ListView)findViewById(R.id.listView);

        dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_CALCS,
                new String[] {DBHelper.KEY_ID_CALCS, DBHelper.KEY_TITLE_CALCS},
                DBHelper.KEY_ID_CATEGORY + " = ?",
                new String[] {Integer.toString(idCategories)},
                null, null, null);

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_CALCS);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE_CALCS);

            do
            {
                Log.e("mlog", "ID = " + cursor.getInt(idIndex) +
                        "name = " + cursor.getString(nameIndex));
            }
            while (cursor.moveToNext());
        }
        else
        {
            Log.e("else", "0 rows");
        }
        final String[] catnames = getResources().getStringArray(R.array.cat_names);
        Log.e("111111111", catnames[0]);
        catNamesList = new ArrayList<>(Arrays.asList(catnames));



// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catNamesList);

        Log.e("qwe", adapter.toString());

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
            {
                String name = ((TextView) itemClicked).getText().toString();
                Intent intent = new Intent(CalcsCategoriesActivity.this, CalcsActivity.class);
                intent.putExtra("name_calcs",name);
                startActivity(intent);

//                int text = ((TextView) itemClicked).getId();
//
//                Log.e("121",Integer.toString(position));
//
//                Toast.makeText(getApplicationContext(),
//                        ((TextView) itemClicked).getText(),
//                        Toast.LENGTH_SHORT).show();
            }
        });


    }




}
