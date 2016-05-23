package com.example.calvinkwan.college_confession;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CommentsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        TextView PostBody = (TextView) findViewById(R.id.body);
        TextView time = (TextView) findViewById(R.id.minSincePost);
        TextView voteScore = (TextView) findViewById(R.id.voteScore);

        Bundle bundle = getIntent().getExtras();
        String bundlePostbody = bundle.getString("body");
        String bundlePosttime = bundle.getString("time");
        String bundlePostvoteScore = bundle.getString("voteScore");
        int bundlePostID = bundle.getInt("postID");

        Toast.makeText(getApplicationContext(), bundlePostID + "", Toast.LENGTH_LONG).show();

        PostBody.setText(bundlePostbody);
        time.setText(bundlePosttime);
        voteScore.setText(bundlePostvoteScore);

    }
}
