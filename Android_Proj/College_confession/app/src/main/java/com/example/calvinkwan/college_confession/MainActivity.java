package com.example.calvinkwan.college_confession;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.PoolingByteArrayOutputStream;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

    EditText email, password;
    TextView serverResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);

        if(savedAPIKEY != null)
        {
            startMain("200");
        }
    }

    public void Login(View view)
    {
        final ProgressDialog loginUser = new ProgressDialog(this);
        loginUser.setTitle("Loading");
        loginUser.setMessage("Please wait");
        loginUser.show();

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        serverResponse = (TextView) findViewById(R.id.serverResponse);

        if(email.length() == 0 && password.length() == 0)
        {
            serverResponse.setText("Please enter in an email and password");
            return;
        }

        final String mEmail, mPassword;
        mEmail = email.getText().toString().trim();
        mPassword = password.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://collegeconfessions.party/api/login";

        // Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                loginUser.dismiss();

                //serverResponse.setText(response);
                try
                {
                    JSONObject responseOBJ = new JSONObject(response);

                    String status = responseOBJ.getString("status");
                    String apiKey = responseOBJ.getString("api_key");

                    SharedPreferences.Editor editor = getSharedPreferences("API_key", MODE_PRIVATE).edit();
                    editor.putString("api_KEY", apiKey);
                    editor.commit();

                    startMain(status);

                }
                catch (Exception e)
                {


                }
            }
        },
        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                serverResponse.setText("Please make sure your credentials are valid");
            }
        })

        {;
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", mEmail);
                params.put("password", mPassword);
                return params;
            }

            //https://gist.github.com/mombrea/7250835

        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }



    public void Register(View view)
    {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        this.startActivity(intent);
    }

    public void startMain(String str)
    {
        if(str.equals("200"))
        {
            Intent intent = new Intent(getApplicationContext(), Confessions_List_display.class);
            this.startActivity(intent);
        }
    }

}
