package com.calvinkwan.college_confession;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.calvinkwan.college_confession.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity
{
    EditText mEmail, mPassword, mConfirmPassword;
    String password1, password2, email, College_selcted;
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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(RegisterActivity.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                College_selcted = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });
    }

    public void ConfirmInfo(View view)
    {
        final ProgressDialog registration = new ProgressDialog(this);
        registration.setTitle("Please Wait");
        registration.setMessage("Please wait while we're creating a new user");
        registration.show();

        password1 = mPassword.getText().toString();
        password2 = mConfirmPassword.getText().toString();
        email = mEmail.getText().toString().trim();

        if(validEmail(email))
        {
            if (matchingPasswords(password1, password2))
            {
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "https://collegeconfessions.party/api/register";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        registration.dismiss();

                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject responseOBJ = new JSONObject(response);

                            String status = responseOBJ.getString("status");
                            String message = responseOBJ.getString("error");

                            responseText.setText(message);

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);


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
                                responseText.setText("That didn't work!");
                            }
                        })

                {;
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("password", password1);
                        params.put("college_name", College_selcted);
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
        Toast.makeText(getApplicationContext(), "Your have entered a invalid password //" +password1 + "    " + password2, Toast.LENGTH_LONG).show();
        return false;
    }

}