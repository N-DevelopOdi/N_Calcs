package ru.n_develop.n_calcs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ListView listView = (ListView)findViewById(R.id.listView);

// определяем массив типа String
        final String[] catNames = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба"
        };


// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNames);

        listView.setAdapter(adapter);

    }


}
