package com.example.calvinkwan.college_confession;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        if(password1 != password2)
        {
            //future toast message notification
        }
        if(validEmail(email))
        {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("message", "Your message")
                    .build();
            Request request = new Request.Builder()
                    .url("http://www.foo.bar/index.php")
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter a valid edu email", Toast.LENGTH_LONG).show();
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
