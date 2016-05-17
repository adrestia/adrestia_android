package com.example.calvinkwan.college_confession;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class PostConfession extends AppCompatActivity
{
    private EditText PostConfession;
    private TextView charCount;

    private final TextWatcher PostCharCounter = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            charCount.setText( String.valueOf(1024 - s.length()));
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_confession);

        PostConfession = (EditText) findViewById(R.id.userPostInput);
        charCount = (TextView) findViewById(R.id.textCount);


        charCount.setText("1024");
        PostConfession.addTextChangedListener(PostCharCounter);


    }

}
