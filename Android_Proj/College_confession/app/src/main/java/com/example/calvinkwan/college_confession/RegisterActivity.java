package com.example.calvinkwan.college_confession;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.support.annotation.BoolRes;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity
{
    EditText mEmail, mPassword, mConfirmPassword;
    String password1, password2, email;
    TextView responseText;



    String strI = Integer.toString(8572);



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        responseText = (TextView) findViewById(R.id.textView6);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);

        String[] items = new String[]{"1", "2", "three"};

        Vector<String>str=new Vector<String>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(getAssets().open("world_universities_and_domains.json")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String line = "";
        try {
            line = in.readLine();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        int index = 0;
        int country_code = 1;
        while (line != null)
        {
            if(line.indexOf("alpha_two_code") != -1 && line.indexOf("US") == -1)
            {
                country_code = 0;
            }

            if(line.indexOf("name") != -1 && country_code == 1)
                //String college_name = line.substring(8,line.length()-2);
                str.add(line.substring(15,line.length()-2));
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
            if (matchingPasswords(password1, password2))
            {
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "http://dev.collegeconfessions.party/api/register";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                            }
                        })

                {;
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("password", password1);
                        params.put("college_id", strI);
                        return params;
                    }
                    //https://gist.github.com/mombrea/7250835

                };

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter a valid edu email", Toast.LENGTH_LONG).show();
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

    public boolean matchingPasswords(String pw1, String pw2)
    {
        if(pw1.equals(pw2))
        {
            return true;
        }
        Toast.makeText(getApplicationContext(),
                "Your have entered a invalid password //" +password1 + "    " + password2, Toast.LENGTH_LONG).show();
        return false;
    }

}
