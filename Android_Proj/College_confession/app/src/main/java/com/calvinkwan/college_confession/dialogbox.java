package com.calvinkwan.college_confession;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.text.Editable;

import com.example.calvinkwan.college_confession.R;

/**
 * Created by Tan on 5/7/2016.
 */
public class dialogbox extends AppCompatActivity{

    EditText input;
    TextView Charlim;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogbox);

        input = (EditText) findViewById(R.id.editText);
        Charlim = (TextView) findViewById(R.id.textView5);

        int length = input.length();


        //Charlim.setText("Chars Left: "+length);
        // Init Widget GUI
        input = (EditText)findViewById(R.id.editText);
        Charlim = (TextView)findViewById(R.id.textView5);

        // Attached Listener to Edit Text Widget
        input.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {

                // Display Remaining Character with respective color
                int count = 1000 - s.length();
                Charlim.setText(Integer.toString(count));


            }
        });

    }



}
