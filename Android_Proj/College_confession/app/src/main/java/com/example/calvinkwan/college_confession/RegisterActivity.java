package com.example.calvinkwan.college_confession;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
/*
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity
{
    EditText mEmail, mPassword, mConfirmPassword;
    String password1, password2, email;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);


        String[] items = new String[]{"1", "2", "three"};

        //
        Vector<String>str=new Vector<String>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(getAssets().open("world_universities_and_domains.json.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = 0;
        int x = 1;
        while (line != null) {

            if(line.indexOf("alpha_two_code") != -1 && line.indexOf("US") == -1)
                x = 0;


            if(line.indexOf("name") != -1 && x == 1)
                //String college_name = line.substring(8,line.length()-2);
                str.add(line.substring(15, line.length() - 2));
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, str);

        spinner.setAdapter(adapter);
        //
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        */

    }

    public void ConfirmInfo(View view)
    {
        password1 = mPassword.getText().toString();
        password2 = mConfirmPassword.getText().toString();
        email = mEmail.getText().toString();
        if(validEmail(email))
        {


        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter a valid edu email", Toast.LENGTH_LONG).show();
        }
        if((password1.equals(password2)))
        {
            //future toast message notification
            Toast.makeText(getApplicationContext(),
                    "Your have entered a valid password", Toast.LENGTH_LONG).show();
        }
        {
            //future toast message notification
            Toast.makeText(getApplicationContext(),
                    password1 + "    " + password2, Toast.LENGTH_LONG).show();
        }
    }

    public static boolean validEmail(String email)
    {
        boolean emailValid = false;
        CharSequence emailInput = email;

        String expression = "(^[\\w\\.-]+@[\\w\\-+\\)]+.edu)";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailInput);


        if(matcher.matches())
        {
            emailValid = true;
        }
        return emailValid;


        //"http://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext"
    }




}
