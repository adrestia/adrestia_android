package com.example.calvinkwan.college_confession;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity
{
    EditText mEmail, mPassword, mConfirmPassword;
    String password1, password2, email;
    TextView responseText;






    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        responseText = (TextView) findViewById(R.id.textView6);
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
                String url ="http://www.google.com";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // Display the first 500 characters of the response string.
                        responseText.setText("Response is: "+ response.substring(0,500));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        responseText.setText("That didn't work!");
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("password", password1);
                        //todo for college ID (Alex Tan)
                        return params;
                    }
                    //https://gist.github.com/mombrea/7250835
                };

// Add the request to the RequestQueue.
            queue.add(stringRequest);


        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Please enter a valid edu email", Toast.LENGTH_LONG).show();
        }

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
                "Your have entered a invalid password" +password1 + "    " + password2, Toast.LENGTH_LONG).show();
        return false;
    }

}
