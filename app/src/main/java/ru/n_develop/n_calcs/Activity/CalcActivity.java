package ru.n_develop.n_calcs.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.n_develop.n_calcs.Helper.DBHelper;
import ru.n_develop.n_calcs.Helper.Parser.MatchParser;
import ru.n_develop.n_calcs.R;

public class CalcActivity extends AppCompatActivity
{

    public String idCalcs;

    DBHelper dbHelper;
    SQLiteDatabase database;

    LinearLayout llt;

    private List<Map<Integer, EditText>> editTextList = new ArrayList<Map<Integer, EditText>>();
    HashMap<Integer, EditText> map;

    private List<Integer> id_formula = new ArrayList<Integer>();
    private List<String> formuls = new ArrayList<String>();
    private List<String> result = new ArrayList<String>();
    private List<String> text_about = new ArrayList<String>();
    private List<String> image = new ArrayList<String>();

    private List<String> class_formuls = new ArrayList<String>();

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
                new String[] {DBHelper.KEY_FORMULA_ID, DBHelper.KEY_ID_CALCS_FORMULA, DBHelper.KEY_RESULT, DBHelper.KEY_FORMULA, DBHelper.KEY_TEXT_ABOUT, DBHelper.KEY_IMAGE},
                DBHelper.KEY_ID_CALCS_FORMULA + " = ?",
                new String[] {idCalcs},
                null, null, null);

        int i = 0;
        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_FORMULA_ID);
            int resultIndex = cursor.getColumnIndex(DBHelper.KEY_RESULT);
            int formulaIndex = cursor.getColumnIndex(DBHelper.KEY_FORMULA);
            int textAboutIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT_ABOUT);
            int imageIndex = cursor.getColumnIndex(DBHelper.KEY_IMAGE);

            do
            {

                id_formula.add(i, cursor.getInt(idIndex));
                result.add(i,cursor.getString(resultIndex));
                formuls.add(i,cursor.getString(formulaIndex));
                text_about.add(i,cursor.getString(textAboutIndex));
                image.add(i,cursor.getString(imageIndex));

                Log.e("mlog", "ID = " + cursor.getInt(idIndex) +
                        " formula = " + cursor.getString(formulaIndex));
            }
            while (cursor.moveToNext());
        }

        class_formuls.add("FormulaGerona");

        int[] img_ids = {1000,1001,1002,1003,1004,1005,1006,1007,1008};
        int[] text_about_ids = {2000,2001,2002,2003,2004,2005,2006,2007,2008};
        int[][] text_variable_ids = {
                {3000,3001,3002,3003,3004,3005,3006,3007,3008},
                {3009,3010,3011,3012,3013,3014,3015,3016,3017},
                {3018,3019,3020,3021,3022,3023,3024,3025,3026},
                {3027,3028,3029,3030,3031,3032,3033,3034,3035},
                {3036,3037,3038,3039,3040,3041,3042,3043,3044},
                {3045,3046,3047,3048,3049,3050,3051,3052,3053},
               
        };
        final int[][] edit_text_variable_ids = {
                {4000,4001,4002,4003,4004,4005,4006,4007,4008},
                {4009,4010,4011,4012,4013,4014,4015,4016,4017},
                {4018,4019,4020,4021,4022,4023,4024,4025,4026},
                {4027,4028,4029,4030,4031,4032,4033,4034,4035},
                {4036,4037,4038,4039,4040,4041,4042,4043,4044},
                {4045,4046,4047,4048,4049,4050,4051,4052,4053},

        };
        final int[] button_ids = {5000,5001,5002,5003,5004,5005,5006,5007,5008};
        

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

            variable = getVariable(formuls.get(i));

            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setLayoutParams(lRelativeParams);



            // картинка
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(lImageParams);
            imageView.setId(img_ids[i]);

			String filename = "ploshadtreygolnika1";
			Picasso.with(this).load(getResources().getIdentifier(image.get(i), "drawable", getPackageName())).into(imageView);
            relativeLayout.addView(imageView);

            //Описание калькулятора
            final TextView textView = new TextView(this);
            textView.setLayoutParams(lTextParamsAbout);
            textView.setText(text_about.get(i) + "\n" + formuls.get(i));
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

                    TextView textView_variable = new TextView(this);
                    textView_variable.setLayoutParams(lTextVariableParams);
                    textView_variable.setText(variable.get(j) + " = ");
                    textView_variable.setId(text_variable_ids[i][j]);
                    textView_variable.setTextSize(18);
                    textView_variable.setWidth(150);
                    relativeLayout.addView(textView_variable);

                    EditText editTxt = new EditText(this);
                    editTxt.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editTxt.setLayoutParams(lEditParams);
                    editTxt.setTextSize(18);
                    editTxt.setId(edit_text_variable_ids[i][j]);
                    editTxt.setPadding(5, 0, 0, 10);
                    editTxt.setWidth(150);

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
//                    lEditParams.addRule(RelativeLayout., text_variable_ids[i][j]);

                    TextView textView_variable = new TextView(this);
                    textView_variable.setLayoutParams(lTextVariableParams);
                    textView_variable.setText(variable.get(j) + " = ");
                    textView_variable.setId(text_variable_ids[i][j]);
                    textView_variable.setTextSize(18);
                    textView_variable.setWidth(150);
                    relativeLayout.addView(textView_variable);

                    EditText editTxt = new EditText(this);
                    editTxt.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editTxt.setLayoutParams(lEditParams);
                    editTxt.setTextSize(18);
                    editTxt.setId(edit_text_variable_ids[i][j]);
                    editTxt.setPadding(5, 0, 0, 5);
                    editTxt.setWidth(150);

                    map = new HashMap<Integer, EditText>();
                    map.putAll(editTextList.get(i));
                    map.put(j, editTxt);
                    editTextList.add(i, map);
                    relativeLayout.addView(editTxt);

                }

                if (variable.size() == j + 1)
                {
                    //layout params for every Button
                    RelativeLayout.LayoutParams lButtonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lButtonParams.addRule(RelativeLayout.BELOW, text_variable_ids[i][j]);

                    final TextView textResult = new TextView(this);

                    final Button btn =  new Button(this);
                    btn.setLayoutParams(lButtonParams);
                    btn.setId(button_ids[i]);
                    btn.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            MatchParser parser = new MatchParser();

                            // узнали формула
                            int number_el = findIdInt(button_ids, v.getId());
                            String[] formuls1 = formuls.toArray(new String[formuls.size()]);
                            Integer[] ids = id_formula.toArray(new Integer[id_formula.size()]);
                            int id = ids[number_el];
                            Log.e("formuls1, id", formuls1[number_el] + "  "+ Integer.toString(id));

                            // находим переменные
                            List<String> variable_ = new ArrayList<String>();
                            variable_ = getVariable(formuls1[number_el]);

                            Map<Integer, EditText> hashMap = editTextList.get(number_el);

                            // берем то что ввели
                            for (int k = 0; k < hashMap.size(); k++)
                            {

                                Collection<EditText> collection = hashMap.values();
                                EditText[] values = collection.toArray(new EditText[k]);
                                Log.e("type", Integer.toString(values[k].getInputType()));

                                // Меняем переменные на числа из ввода
                                formuls1[number_el] = formuls1[number_el].replace(variable_.get(k), values[k].getText());
                            }
                            Log.e("new_formula", formuls1[number_el]);

                            try
                            {
                                double test = parser.Parse(formuls1[number_el]);
                                textResult.setText(Double.toString(test));
                            }
                            catch (Exception e)
                            {
                                Log.e("error - ", e.toString());
                            }

                            database.execSQL("UPDATE " + DBHelper.TABLE_FORMULS +
                                    " SET " + DBHelper.KEY_COUNT + " = " + DBHelper.KEY_COUNT + " + 1 " +
                                    " WHERE " + DBHelper.KEY_FORMULA_ID + " = " +  id );
                        }
                    });
                    btn.setText("Результат");
                    relativeLayout.addView(btn);

                    //layout params for every Button
                    RelativeLayout.LayoutParams lTextResult = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lTextResult.addRule(RelativeLayout.ALIGN_BOTTOM, button_ids[i]);
                    lTextResult.setMargins(20,0,0,0);
                    lTextResult.addRule(RelativeLayout.RIGHT_OF, button_ids[i]);
                    lTextResult.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    lTextResult.addRule(RelativeLayout.ALIGN_BOTTOM, button_ids[i]);
                    lTextResult.addRule(RelativeLayout.ALIGN_BASELINE, button_ids[i]);

                    textResult.setLayoutParams(lTextResult);
                    relativeLayout.addView(textResult);
                }
            }
            // добавляем новыей layout на базовый
            llt.addView(relativeLayout);
        }

        /**
         * по классам
         */
//        Class classe;
//        for (i = 0; i < class_formuls.size(); i++)

        }

	/**
     * Получаем переменные которые необходимо ввести для расчета
     * @param formula
     * @return
     */
    private ArrayList<String> getVariable (String formula)
    {
        ArrayList<String> varibales = new ArrayList<String>();
        String varibale_string = "";

        for (int i = 0; i < formula.length(); i++)
        {
            // Находим символы между знаков выражений
            if(!isDelim(formula.charAt(i)))
            {
                varibale_string += Character.toString(formula.charAt(i));
            }
            else
            {
                // если найденое не пусто, не формула и не число, значит мы нашли переменную
                if (!varibale_string.equals("") && !isFormulas(varibale_string) && !isNumber(varibale_string))
                {
                    varibales.add(varibale_string);
                }
                varibale_string = "";
            }
        }
        if (!varibale_string.equals("") && !isFormulas(varibale_string) && !isNumber(varibale_string))
        {
            varibales.add(varibale_string);
        }

        // удаляем повторы
        Set<String> new_variable = new HashSet<String>(varibales);
        varibales.clear();
        varibales.addAll(new_variable);

        for (int i = 0; i < varibales.size(); i++)
        {
            Log.e("formula", varibales.get(i));

        }

        return varibales;
    }

	/**
     * Поиск id
     * @param array
     * @param el
     * @return
     */
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

	/**
     * Возвращает true если является разделителем
     * @param c
     * @return
     */
    private boolean isDelim(char c)
    {
        if ((" +-/*%^=()".indexOf(c) != -1))
        {
            return true;
        }
        return false;
    }

	/**
     * Возвращает true если является формулой
     * @param var
     * @return
     */
    private boolean isFormulas(String var)
    {
        String[] formuls = {"sqrt", "sin", "cos", "tan", "PI"};
        boolean b = Arrays.asList(formuls).contains(var);
        return b;


    }

    /**
     * Возвращает true если является числом
     * @param var
     * @return
     */
    private boolean isNumber(String var)
    {
        try
        {
            Log.e("isInt", Double.toString(Double.parseDouble(var)) + " " + var);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
