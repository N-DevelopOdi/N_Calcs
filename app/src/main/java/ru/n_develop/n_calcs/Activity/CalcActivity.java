package ru.n_develop.n_calcs.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class CalcActivity extends AppCompatActivity
{

    public String idCalcs;

    DBHelper dbHelper;
    SQLiteDatabase database;

    LinearLayout llt;
    private List<EditText> editTextList = new ArrayList<EditText>();
    View.OnClickListener getEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        idCalcs = getIntent().getExtras().getString("id_calcs");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcs);


        llt = (LinearLayout) findViewById(R.id.calcs);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_FORMULS,
                new String[] {DBHelper.KEY_ID_CALCS_FORMULA, DBHelper.KEY_FORMULA},
                DBHelper.KEY_ID_CALCS_FORMULA + " = ?",
                new String[] {idCalcs},
                null, null, null);

        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_CALCS_FORMULA);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_FORMULA);

            do
            {
                // метод put добавляет с верху массива, поэтому id выше name
//                map = new HashMap<String, String>();
//                map.put("1_name", cursor.getString(titleIndex));
//                map.put("0_id", cursor.getString(idIndex));
//                myArrList.add(map);


                Log.e("mlog", "ID = " + cursor.getInt(idIndex) +
                        "name = " + cursor.getString(titleIndex));
            }
            while (cursor.moveToNext());
        }
        else
        {
            Log.e("else", "0 rows");
        }


        //layout params for every EditText
        LinearLayout.LayoutParams lEditParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
        //layout params for every Button
        LinearLayout.LayoutParams lButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);

        getEditText = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int editId = v.getId();
                EditText a = editTextList.get(0);
                EditText h = editTextList.get(1);

                int result = Integer.parseInt(a.getText().toString()) * Integer.parseInt(h.getText().toString())  / 2;
                Log.e("editId", Integer.toString(result));

                Toast toast = Toast.makeText(getApplicationContext(),
                        Integer.toString(result), Toast.LENGTH_SHORT);
                toast.show();

            }
        };

        for (int i = 0; i < 2; i++) {
            EditText  editTxt = new EditText(this);
            editTxt.setLayoutParams(lEditParams);
            editTxt.setText("0");
            editTextList.add(i, editTxt);
            llt.addView(editTxt);

//            Button btn =  new Button(this);
//            btn.setLayoutParams(lButtonParams);
//            btn.setId(i);
//            btn.setOnClickListener(getEditText);
//            btn.setText("click!");
//            llt.addView(btn);
        }

        Button btn =  new Button(this);
            btn.setLayoutParams(lButtonParams);
            btn.setId(0);


            btn.setOnClickListener(getEditText);
            btn.setText("click!");
            llt.addView(btn);
    }
}