package ru.n_develop.n_calcs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
