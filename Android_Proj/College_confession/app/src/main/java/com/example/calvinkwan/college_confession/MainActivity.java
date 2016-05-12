package com.example.calvinkwan.college_confession;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Login(View view)
    {

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
