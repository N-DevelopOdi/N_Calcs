package ru.n_develop.n_calcs.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class MainActivity extends AppCompatActivity
{


    DBHelper dbHelper;
    RelativeLayout relativeLayout;
    private List<ImageButton> imageButtonList = new ArrayList<ImageButton>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        relativeLayout = (RelativeLayout)findViewById(R.id.activity_main);

        //layout params for every EditText
        //layout params for every Button
//        LinearLayout.LayoutParams lButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);




//        ImageButton


        for (int i = 0; i < 3; i++)
        {

            RelativeLayout.LayoutParams ButtoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


            ImageButton imageButton = new ImageButton(this);
            imageButton.setLayoutParams(ButtoParams);
            imageButton.setId(i);
//            imageButton.set;

            imageButtonList.add(i, imageButton);
            relativeLayout.addView(imageButton);

//            Button btn =  new Button(this);
//            btn.setLayoutParams(lButtonParams);
//            btn.setId(i);
//            btn.setOnClickListener(getEditText);
//            btn.setText("click!");
//            llt.addView(btn);
        }

//        Button btn =  new Button(this);
//        btn.setLayoutParams(lButtonParams);
//        btn.setId(0);
//
//
////        btn.setOnClickListener(getEditText);
//        btn.setText("click!");
//        relativeLayout.addView(btn);

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
