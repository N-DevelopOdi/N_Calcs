package ru.n_develop.n_calcs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ru.n_develop.n_calcs.R;

public class CalcsCategoriesActivity extends AppCompatActivity
{

    public int IdCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.e("22", "onCreate");

        IdCategories = getIntent().getExtras().getInt("id_categories");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcs_categories);

        // получаем экземпляр элемента ListView
        final ListView listView = (ListView)findViewById(R.id.listView);

// определяем массив типа String
//        final String[] catNames = new String[] {
//                "Площадь треугольника", "Симба"
//        };


        final String[] catnames = getResources().getStringArray(R.array.cat_names);
        Log.e("111111111", catnames[0]);


// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catnames);

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
