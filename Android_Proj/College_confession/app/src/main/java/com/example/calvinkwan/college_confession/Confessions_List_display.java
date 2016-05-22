package com.example.calvinkwan.college_confession;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calvinkwan.college_confession.tabs.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PolicySpi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Confessions_List_display extends AppCompatActivity
{

    ArrayList<ConfessionOBJS> ArrayConfession = new ArrayList<ConfessionOBJS>();
    ListAdapter aa = null;
    ListView confessionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confessions__list_display);

        grabJson("top");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.newConfession)
        {
            Intent postIntent = new Intent(getApplicationContext(), PostConfession.class);
            startActivity(postIntent);
            //will open new message activity

        }

        return super.onOptionsItemSelected(item);
    }


    void grabJson(String type)
    {

        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);
        if (savedAPIKEY != null)
        {
            final ProgressDialog loginUser = new ProgressDialog(this);
            loginUser.setTitle("Loading");
            loginUser.setMessage("Please wait");
            loginUser.show();
            String url;

            RequestQueue queue = Volley.newRequestQueue(this);
            url = "https://dev.collegeconfessions.party/api/" + type +"?apikey=" + savedAPIKEY;

            // Request a string response from the provided URL.
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    loginUser.dismiss();

                    ParseJsonData(response);

                }
            },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(stringRequest);
        }
    }


    public void TopConfession(View view)
    {
        confessionsList.setAdapter(null);
        grabJson("top");

    }

    public void NewConfession(View view)
    {
        confessionsList.setAdapter(null);
        grabJson("new");

    }

    public void HotConfession(View view)
    {
        confessionsList.setAdapter(null);
        grabJson("hot");

    }

    void ParseJsonData(String JSONobj) {
        if (JSONobj != null) {
            try {
                JSONArray array = new JSONArray(JSONobj);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    ConfessionOBJS confessionObject = new ConfessionOBJS();
                    confessionObject.body = obj.getString("p_body");
                    //confessionObject.p_created =
                    String dtStart = obj.getString("p_created");
                    SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        Date date = format.parse(dtStart);
                        Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();
                        confessionObject.p_created = date.toString();
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    confessionObject.comments = obj.getInt("comments");
                    //confessionObject.is_like = obj.getBoolean("l_is_like");
                    //confessionObject.i_voted = obj.getString("l_voted");
                    confessionObject.p_downvotes = obj.getInt("p_downvotes");
                    confessionObject.p_id = obj.getInt("p_id");
                    confessionObject.p_upvotes = obj.getInt("p_upvotes");


                    ArrayConfession.add(confessionObject);
                }

                confessionsList = (ListView) findViewById(R.id.confessionList);
                aa = new ListAdapter(this);
                confessionsList.setAdapter(aa);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    public class ConfessionOBJS
    {
        int comments;               //number of commets
        Boolean is_like;            //if the individual liked the upvoted or downvoted
        String i_voted;             //timestamp of when they upvoted
        String body;                //body of confession
        String p_created;           //when post was created
        int p_downvotes;            //number of downvotes
        int p_id;
        int p_upvotes;              //number of upvotes
    }

    class ListAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<String> bodyArray = new ArrayList<String>();

        ListAdapter(Context c)
        {
            context = c;

        }

        @Override
        public int getCount()
        {
            return ArrayConfession.size();
        }

        @Override
        public Object getItem(int position)
        {
            return ArrayConfession.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.complete_confession_layout, parent, false);
            TextView body = (TextView) row.findViewById(R.id.body);
            TextView time = (TextView) row.findViewById(R.id.minSincePost);
            TextView comments = (TextView) row.findViewById(R.id.CommentButton);
            TextView voteScore = (TextView) row.findViewById(R.id.voteScore);


            ConfessionOBJS temp = ArrayConfession.get(position);
            body.setText(temp.body);
            time.setText(temp.p_created);
            comments.setText("Comments: " + temp.comments);
            voteScore.setText(temp.p_upvotes - temp.p_downvotes + "");

            return row;
        }
    }


}
