package com.example.calvinkwan.college_confession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostConfession extends AppCompatActivity
{
    private EditText PostConfession;
    private TextView charCount, sResponse;

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

    public void PostConfession(View view)
    {
        sResponse = (TextView) findViewById(R.id.sResponse);

        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);

        if(savedAPIKEY != null)
        {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://dev.collegeconfessions.party/api/posts?apikey=" + savedAPIKEY;

            // Request a string response from the provided URL.
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    sResponse.setText(response);
                    try
                    {
                        JSONObject responseOBJ = new JSONObject(response);

                        String status = responseOBJ.getString("status");
                        if(status.equals("200"))
                        {
                            Toast.makeText(getApplicationContext(), "Post was a success", Toast.LENGTH_LONG).show();
                            returnTOConfessionList();

                        }

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
                            sResponse.setText("Please make sure your credentials are valid");
                        }
                    })

            {;
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("body", PostConfession.getText().toString());
                    return params;
                }

                //https://gist.github.com/mombrea/7250835

            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);


        }



    }

    void returnTOConfessionList()
    {
        Intent confessionList = new Intent(getApplicationContext(), Confessions_List_display.class);
        startActivity(confessionList);
    }


}
