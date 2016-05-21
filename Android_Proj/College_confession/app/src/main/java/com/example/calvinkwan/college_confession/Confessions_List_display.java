package com.example.calvinkwan.college_confession;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calvinkwan.college_confession.tabs.SlidingTabLayout;

import org.json.JSONObject;

import java.security.PolicySpi;
import java.util.HashMap;
import java.util.Map;

public class Confessions_List_display extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confessions__list_display);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.newConfession) {
            Intent postIntent = new Intent(getApplicationContext(), PostConfession.class);
            startActivity(postIntent);
            //will open new message activity

        }

        return super.onOptionsItemSelected(item);
    }


    void grabJson(String type) {

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
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
        grabJson("top");
    }

    public void NewConfession(View view)
    {
        grabJson("new");
    }

    public void HotConfession(View view)
    {
        grabJson("hot");
    }
}
