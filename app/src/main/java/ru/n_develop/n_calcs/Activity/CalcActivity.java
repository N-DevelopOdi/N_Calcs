package ru.n_develop.n_calcs.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private List<String> formuls = new ArrayList<String>();
    private List<String> result = new ArrayList<String>();

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
                new String[] {DBHelper.KEY_ID_CALCS_FORMULA, DBHelper.KEY_RESULT, DBHelper.KEY_FORMULA},
                DBHelper.KEY_ID_CALCS_FORMULA + " = ?",
                new String[] {idCalcs},
                null, null, null);

        int i = 0;
        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_CALCS_FORMULA);
            int resultIndex = cursor.getColumnIndex(DBHelper.KEY_RESULT);
            int formulaIndex = cursor.getColumnIndex(DBHelper.KEY_FORMULA);

            do
            {
                // метод put добавляет с верху массива, поэтому id выше name
//                map = new HashMap<String, String>();
//                map.put("1_name", cursor.getString(titleIndex));
//                map.put("0_id", cursor.getString(idIndex));
//                myArrList.add(map);

                result.add(i,cursor.getString(resultIndex));
                formuls.add(i,cursor.getString(formulaIndex));


//
//                Log.e("mlog", "ID = " + cursor.getInt(idIndex) +
//                        "formula = " + cursor.getString(formulaIndex));
            }
            while (cursor.moveToNext());
        }
        else
        {
//            Log.e("else", "0 rows");
        }


//        Log.e("formuls = ", Integer.toString(formuls.size()));

        // находим ресурсы
//        Resources res = getResources();
//        TypedArray img_ids = res.obtainTypedArray(R.array.img_formula);
//        TypedArray text_about = res.obtainTypedArray(R.array.text_about);
//
//        Log.e("img_id0 = ", Integer.toString(text_about.getIndex(0)));


        int[] img_ids = {1,2,3};
        int[] text_about_ids = {4,5,6};
        int[] text_variable_ids = {7,8,9};

//        Log.e("img_ids", img_ids.toString());
//        Log.e("text_about", text_about.toString());


//        String[] ids = getResources().getStringArray(R.array.img_formula);
//        Log.e("new_img", ids[0]);


        /**
         * Разбор формул
         */
        for (i = 0; i < formuls.size(); i++)
        {
            //layout params for every LinearLayout
            RelativeLayout.LayoutParams lRelativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);

            //layout params for every ImageView
            RelativeLayout.LayoutParams lImageParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);
            lImageParams.alignWithParent = true;
            lImageParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

            //layout params for every TextAbout
            RelativeLayout.LayoutParams lTextParamsAbout = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
            lTextParamsAbout.addRule(RelativeLayout.BELOW, img_ids[i]);

            //layout params for every TextVariable
            RelativeLayout.LayoutParams lTextVariableParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
            lTextVariableParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            lTextVariableParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
            lTextVariableParams.addRule(RelativeLayout.BELOW, text_about_ids[i]);
            Log.e("1234567890 = ", Integer.toString(text_about_ids[i]));

            //layout params for every EditText
            RelativeLayout.LayoutParams lEditParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lEditParams.addRule(RelativeLayout.BELOW, text_about_ids[i]);
            lEditParams.addRule(RelativeLayout.ALIGN_PARENT_END, text_variable_ids[i]);
            lEditParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            //layout params for every Button
//            RelativeLayout.LayoutParams lButtonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);



            List<String> variable = new ArrayList<String>();

            variable = getVariable(formuls.get(i));

//            Log.e("new_variable", variable.toString());


            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setLayoutParams(lRelativeParams);

            // картинка
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(lImageParams);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setId(img_ids[i]);
            relativeLayout.addView(imageView);

            //Описание калькулятора
            TextView textView = new TextView(this);
            textView.setLayoutParams(lTextParamsAbout);
            textView.setText("Описание калькулятора");
            textView.setGravity(Gravity.CENTER);
            textView.setId(text_about_ids[i]);
            relativeLayout.addView(textView);

            Log.e("text_about_id", String.valueOf(textView.getId()));

            //Переменные нужно в цмикл потом
            TextView textView_variable = new TextView(this);
            textView_variable.setLayoutParams(lTextVariableParams);
            textView_variable.setText("Переменная = ");
            textView_variable.setId(text_variable_ids[i]);
            relativeLayout.addView(textView_variable);

            EditText  editTxt = new EditText(this);
            editTxt.setLayoutParams(lEditParams);
            editTxt.setText("0");
            editTextList.add(i, editTxt);
            relativeLayout.addView(editTxt);
//
//            Button btn =  new Button(this);
//            btn.setLayoutParams(lButtonParams);
//            btn.setId(0);
//            btn.setOnClickListener(getEditText);
//            btn.setText("click!");
//            relativeLayout.addView(btn);

            // добавляем новыей layout на базовый
            llt.addView(relativeLayout);
        }

        // функция нажатия на кнопку
//        getEditText = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int editId = v.getId();
//                EditText a = editTextList.get(0);
//                EditText h = editTextList.get(1);
//
//                int result = Integer.parseInt(a.getText().toString()) * Integer.parseInt(h.getText().toString())  / 2;
//                Log.e("editId", Integer.toString(result));
//
//                Toast toast = Toast.makeText(getApplicationContext(),
//                        Integer.toString(result), Toast.LENGTH_SHORT);
//                toast.show();
//
//            }
//        };
    }

    private ArrayList<String> getVariable (String formula)
    {
        ArrayList<String> varibales = new ArrayList<String>();
        int j = 0;
        char[] formula_char = formula.toCharArray();

        for (int i = 0; i<formula_char.length; i++)
        {
            switch (formula_char[i])
            {
                case 'a' : varibales.add(j, Character.toString(formula_char[i]));
                case 'b' : varibales.add(j, Character.toString(formula_char[i]));
                case 'c' : varibales.add(j, Character.toString(formula_char[i]));
                case 'd' : varibales.add(j, Character.toString(formula_char[i]));
            }
        }

        Set<String> set = new HashSet<String>(varibales);
        varibales.clear();
        varibales.addAll(set);

        return varibales;
    }
}
