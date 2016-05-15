package com.example.calvinkwan.college_confession;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    EditText mEmail, mPassword;
    String password1, password2, email;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Login(View view)
    {
        mEmail = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);

        password1 = mPassword.getText().toString();
        email = mEmail.getText().toString();



        RegisterActivity letter = new RegisterActivity();

        if(letter.validEmail(email))
        {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://dev.collegeconfessions.party/api/login";

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
                    return params;
                }
                //https://gist.github.com/mombrea/7250835

            };

// Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter a valid edu email", Toast.LENGTH_LONG).show();
        }



        //right now just start new activity for Confessions_List_display
        Intent intent = new Intent(getApplicationContext(), Confessions_List_display.class);
        this.startActivity(intent);

    }

    public void ForgetPassword(View view)
    {

    }

    public void Register(View view)
    {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        this.startActivity(intent);
    }

}
