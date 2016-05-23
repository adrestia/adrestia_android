package com.example.calvinkwan.college_confession;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class CommentsActivity extends AppCompatActivity
{

    int bundlePostID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        GetJSONcomments();

        TextView PostBody = (TextView) findViewById(R.id.body);
        TextView time = (TextView) findViewById(R.id.minSincePost);
        TextView voteScore = (TextView) findViewById(R.id.voteScore);

        Bundle bundle = getIntent().getExtras();
        String bundlePostbody = bundle.getString("body");
        String bundlePosttime = bundle.getString("time");
        String bundlePostvoteScore = bundle.getString("voteScore");
        bundlePostID = bundle.getInt("postID");

        //Toast.makeText(getApplicationContext(), bundlePostID + "", Toast.LENGTH_LONG).show();

        PostBody.setText(bundlePostbody);
        time.setText(bundlePosttime);
        voteScore.setText(bundlePostvoteScore);


    }

    public void GetJSONcomments()
    {
        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);

        if (savedAPIKEY != null) {
            RequestQueue queue = Volley.newRequestQueue(this);
            //String url = //"https://dev.collegeconfessions.party/api/posts/" + bundlePostID + "?apikey=" + savedAPIKEY;
                String url = "https://dev.collegeconfessions.party/api/posts/16?apikey=6c4da0cf269442fdb37b81e09692997e";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
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
                            Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                        }
                    })

            {;};
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    public void PostComment(View view)
    {
        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);

        if (savedAPIKEY != null) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://dev.collegeconfessions.party/api/comments?apikey=" + savedAPIKEY;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                            Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                        }
                    })

            {;
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("post_id", bundlePostID +"");
                    return params;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    public class CommentsObjects
    {
        String commentBody;
        int commentD_votes;
        int commentUp_votes;
        Boolean Is_Like;
        Boolean Is_Voted;
    }
}
