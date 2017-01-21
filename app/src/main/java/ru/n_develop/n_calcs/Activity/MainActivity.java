package ru.n_develop.n_calcs.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class MainActivity extends AppCompatActivity
{


    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

    }

    // запуск окна с деревянными окнами
    public void CategoriesMath(View view)
    {
        Intent intent = new Intent(MainActivity.this, CalcsCategoriesActivity.class);
        intent.putExtra("id_categories",1);
        startActivity(intent);

        Log.e("11", "start");
    }
}
