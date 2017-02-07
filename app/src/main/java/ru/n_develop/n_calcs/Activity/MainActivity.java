package ru.n_develop.n_calcs.Activity;

import android.content.Intent;
import android.database.Cursor;
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
}
