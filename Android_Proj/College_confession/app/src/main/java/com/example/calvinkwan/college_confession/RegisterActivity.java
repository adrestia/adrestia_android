package com.example.calvinkwan.college_confession;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    }

    public void ConfirmInfo(View view)
    {
        password1 = mPassword.getText().toString();
        password2 = mConfirmPassword.getText().toString();
        email = mEmail.getText().toString();
        if(validEmail(email))
        {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("password", password1);

            client.get("http://dev.collegeconfessions.party/api", params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int i, PreferenceActivity.Header[] headers, String s, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int i, PreferenceActivity.Header[] headers, String s) {

                        }
            }
            );

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
