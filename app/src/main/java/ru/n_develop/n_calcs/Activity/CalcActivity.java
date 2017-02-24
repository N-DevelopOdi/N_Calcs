package ru.n_develop.n_calcs.Activity;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.R;

public class CalcActivity extends AppCompatActivity
{

    public String idCalcs;

    DBHelper dbHelper;
    SQLiteDatabase database;

    LinearLayout llt;

    private List<Map<Integer, EditText>> editTextList = new ArrayList<Map<Integer, EditText>>();
    HashMap<Integer, EditText> map;

    View.OnClickListener getEditText;

    private List<String> formuls = new ArrayList<String>();
    private List<String> result = new ArrayList<String>();

    private List<String> variable = new ArrayList<String>();
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
            Log.e("else", "0 rows");
        }

        int[] img_ids = {1000,1001,1002};
        int[] text_about_ids = {2001,2002,2003};
        int[][] text_variable_ids = {
                {3001,3002,3003},
                {3004,3005,3006},
                {3007,3008,3009},
        };
        final int[][] edit_text_variable_ids = {
                {4001,4002,4003},
                {4004,4005,4006},
                {4007,4008,4009}
        };
        final int[] button_ids = {5001,5002,5003};

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




//            List<String> variable = new ArrayList<String>();

            Log.e("formula = ", formuls.get(i));
            variable = getVariable(formuls.get(i));

            Log.e("size", Integer.toString(variable.size()));
            Log.e("new_variable", variable.toString());
//            Log.e("variable - ", variable.get(0));


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
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            textView.setId(text_about_ids[i]);
            relativeLayout.addView(textView);

            //Переменные нужно в цмикл потом
            for (int j = 0; j < variable.size(); j++)
            {
                if ( j == 0)
                {
                    //layout params for every TextVariable
                    RelativeLayout.LayoutParams lTextVariableParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
                    lTextVariableParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    lTextVariableParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                    lTextVariableParams.addRule(RelativeLayout.BELOW, text_about_ids[i]);
                    lTextVariableParams.setMargins(20,20,0,0);


                    //layout params for every EditText
                    RelativeLayout.LayoutParams lEditParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lEditParams.addRule(RelativeLayout.ALIGN_BOTTOM, text_variable_ids[i][j]);
                    lEditParams.setMargins(20,0,0,0);
                    lEditParams.addRule(RelativeLayout.RIGHT_OF, text_variable_ids[i][j]);
                    lEditParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    lEditParams.addRule(RelativeLayout.ALIGN_TOP, text_variable_ids[i][j]);

//                    Log.e("i = ", Integer.toString(i));
//                    Log.e("j = ", Integer.toString(j));

                    TextView textView_variable = new TextView(this);
                    textView_variable.setLayoutParams(lTextVariableParams);
                    textView_variable.setText(variable.get(j) + " = ");
                    textView_variable.setId(text_variable_ids[i][j]);
//                textView_variable.setBackgroundColor(Color.BLUE);
                    textView_variable.setTextSize(18);
                    textView_variable.setWidth(150);
                    relativeLayout.addView(textView_variable);

                    EditText editTxt = new EditText(this);
                    editTxt.setLayoutParams(lEditParams);
                    editTxt.setTextSize(18);
                    editTxt.setId(edit_text_variable_ids[i][j]);
                    editTxt.setPadding(5, 0, 0, 5);
                    editTxt.setWidth(150);
//                editTxt.setBackgroundColor(Color.GRAY);
                    map = new HashMap<Integer, EditText>();
                    map.put(j, editTxt);
                    editTextList.add(i, map);
                    relativeLayout.addView(editTxt);
                }
                else
                {
                    //layout params for every TextVariable
                    RelativeLayout.LayoutParams lTextVariableParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
                    lTextVariableParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    lTextVariableParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                    lTextVariableParams.addRule(RelativeLayout.BELOW, text_variable_ids[i][j-1]);
                    lTextVariableParams.setMargins(20,20,0,0);




                    //layout params for every EditText
                    RelativeLayout.LayoutParams lEditParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lEditParams.addRule(RelativeLayout.ALIGN_BOTTOM, text_variable_ids[i][j]);
                    lEditParams.setMargins(20,0,0,0);
                    lEditParams.addRule(RelativeLayout.RIGHT_OF, text_variable_ids[i][j]);
                    lEditParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    lEditParams.addRule(RelativeLayout.ALIGN_TOP, text_variable_ids[i][j]);

                    TextView textView_variable = new TextView(this);
                    textView_variable.setLayoutParams(lTextVariableParams);
                    textView_variable.setText(variable.get(j) + " = ");
                    textView_variable.setId(text_variable_ids[i][j]);
//                textView_variable.setBackgroundColor(Color.BLUE);
                    textView_variable.setTextSize(18);
                    textView_variable.setWidth(150);
                    relativeLayout.addView(textView_variable);

                    EditText editTxt = new EditText(this);
                    editTxt.setLayoutParams(lEditParams);
                    editTxt.setTextSize(18);
                    editTxt.setId(edit_text_variable_ids[i][j]);
//                editTxt.setText("123");
                    editTxt.setPadding(5, 0, 0, 5);
                    editTxt.setWidth(150);
//                editTxt.setBackgroundColor(Color.GRAY);
                    map = new HashMap<Integer, EditText>();
                    map.putAll(editTextList.get(i));
                    map.put(j, editTxt);
                    editTextList.add(i, map);
                    relativeLayout.addView(editTxt);


                }

                Log.e("i = ", Integer.toString(i));
                Log.e("j = ", Integer.toString(j));

                if (variable.size() == j + 1)
                {
                    //layout params for every Button
                    RelativeLayout.LayoutParams lButtonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lButtonParams.addRule(RelativeLayout.BELOW, text_variable_ids[i][j]);

                    Button btn =  new Button(this);
                    btn.setLayoutParams(lButtonParams);
                    btn.setId(button_ids[i]);
                    btn.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                            // узнали формула
//                            Log.e("editId123", Integer.toString(v.getId()));
//                            Log.e("editId123", Integer.toString(findIdInt(button_ids, v.getId())));
                            int number_el = findIdInt(button_ids, v.getId());
                            String[] formuls1 = formuls.toArray(new String[formuls.size()]);
                            Log.e("formuls1", formuls1[number_el]);
                            // находим переменные
                            List<String> variable_ = new ArrayList<String>();
                            variable_ = getVariable(formuls1[number_el]);
                            Log.e("varaible = ", variable_.toString());
                            Log.e("edittext = ", editTextList.get(number_el).toString());

                            Map<Integer, EditText> hashMap = editTextList.get(number_el);

                            Log.e("size =", Integer.toString(hashMap.size()));

                            // берем то что ввели
                            for (int k = 0; k < hashMap.size(); k++)
                            {

                                Collection<EditText> collection = hashMap.values();
                                EditText[] values = collection.toArray(new EditText[k]);
                                Log.e("variable", variable_.get(k));
                                Log.e("values", values[k].getText().toString());

                                // Меняем переменные на числа из ввода
                                formuls1[number_el] = formuls1[number_el].replace(variable_.get(k), values[k].getText());



                            }
                            Log.e("new_formula", formuls1[number_el]);

                        }
                    });
                    btn.setText("Результат");
                    relativeLayout.addView(btn);

                }


            }
//


            // добавляем новыей layout на базовый
            llt.addView(relativeLayout);

            //         функция нажатия на кнопку
            getEditText = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                int editId = v.getId();
//                EditText a = editTextList.get(0);
//                EditText h = editTextList.get(1);
//
//                int result = Integer.parseInt(a.getText().toString()) * Integer.parseInt(h.getText().toString())  / 2;
                    Log.e("editId", "test");

//                Toast toast = Toast.makeText(getApplicationContext(),
//                        Integer.toString(result), Toast.LENGTH_SHORT);
//                toast.show();

                }
            };
        }


    }

    private ArrayList<String> getVariable (String formula)
    {
        ArrayList<String> varibales = new ArrayList<String>();
        char[] formula_char = formula.toCharArray();

//        Log.e("size = ", Integer.toString(formula_char.length));
        for (int i = 0; i<formula_char.length; i++)
        {
            switch (formula_char[i])
            {
                case 'a' : varibales.add(Character.toString(formula_char[i]));
                case 'b' : varibales.add(Character.toString(formula_char[i]));
                case 'c' : varibales.add(Character.toString(formula_char[i]));
                case 'd' : varibales.add(Character.toString(formula_char[i]));
            }
        }

        Set<String> set = new HashSet<String>(varibales);
        varibales.clear();
        varibales.addAll(set);

        return varibales;
    }

    private int findIdInt (int[] array, int el)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == el)
            {
                return i;
            }
        }

        return 9999;
    }
}
