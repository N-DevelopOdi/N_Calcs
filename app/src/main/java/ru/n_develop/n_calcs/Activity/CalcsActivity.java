package ru.n_develop.n_calcs.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.n_develop.n_calcs.R;

public class CalcsActivity extends AppCompatActivity
{

    public int IdCategories;

    LinearLayout llt;
    private List<EditText> editTextList = new ArrayList<EditText>();
    View.OnClickListener getEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.e("22", "onCreate");

        IdCategories = getIntent().getExtras().getInt("name_calcs");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcs);


        llt = (LinearLayout) findViewById(R.id.calcs);

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
