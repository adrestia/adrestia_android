package com.example.calvinkwan.college_confession;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity {

    ArrayList<CommentsObjects> ArrayComments = new ArrayList<CommentsObjects>();
    JSONObject commentOBJ = null;
    ListAdapter commentAdapter = null;
    ListView commentsList;
    EditText commentText;
    TextView voteScore;

    int bundlePostID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        TextView PostBody = (TextView) findViewById(R.id.body);
        TextView time = (TextView) findViewById(R.id.minSincePost);
        voteScore = (TextView) findViewById(R.id.CommentVoteScore);


        Bundle bundle = getIntent().getExtras();
        String bundlePostbody = bundle.getString("body");
        String bundlePosttime = bundle.getString("time");
        String bundlePostvoteScore = bundle.getString("voteScore");
        bundlePostID = bundle.getInt("postID");

        //Toast.makeText(getApplicationContext(), bundlePostID + "", Toast.LENGTH_LONG).show();

        PostBody.setText(bundlePostbody);
        time.setText(bundlePosttime);
        voteScore.setText(bundlePostvoteScore);


        GetJSONcomments();
    }

    public void GetJSONcomments()
    {
        commentOBJ = null;
        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);

        if (savedAPIKEY != null) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://dev.collegeconfessions.party/api/posts/" + bundlePostID + "?apikey=" + savedAPIKEY;
            //String url = "https://dev.collegeconfessions.party/api/posts/16?apikey=6c4da0cf269442fdb37b81e09692997e";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    ParseJson(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                        }
                    })

            {
                ;
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    public void PostComment(View view)
    {
        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);

        commentText = (EditText) findViewById(R.id.commentText);

        String commentBody = commentText.getText().toString();

        //Toast.makeText(getApplicationContext(), commentBody, Toast.LENGTH_LONG).show();

        if (savedAPIKEY != null) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://dev.collegeconfessions.party/api/comments?apikey=" + savedAPIKEY;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    commentText.setText("");
                    GetJSONcomments();      //reloads comments with latest comment
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                        }
                    })

            {
                ;

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("post_id", bundlePostID + "");
                    params.put("body", commentText.getText().toString());
                    return params;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    public void CommentPostUpvoting(View view)
    {
        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);
        if (savedAPIKEY != null) {
            //Toast.makeText(getApplicationContext(), temp.body, Toast.LENGTH_LONG).show();
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://dev.collegeconfessions.party/api/posts/upvote?apikey=" + savedAPIKEY;

            // Request a string response from the provided URL.
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    JSONObject responseOBJ = null;
                    try
                    {
                        responseOBJ = new JSONObject(response);
                        String score = responseOBJ.getString("score");
                        voteScore.setText(score);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            //serverResponse.setText("Please make sure your credentials are valid");
                        }
                    }) {
                ;

                @Override
                public Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("post_id", bundlePostID + "");
                    return params;
                }
                //https://gist.github.com/mombrea/7250835
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    public void CommentPostDownvoting(View view)
    {
        SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
        String savedAPIKEY = pref.getString("api_KEY", null);
        if (savedAPIKEY != null)
        {
            //Toast.makeText(getApplicationContext(), temp.body, Toast.LENGTH_LONG).show();
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://dev.collegeconfessions.party/api/posts/downvote?apikey=" + savedAPIKEY;

            // Request a string response from the provided URL.
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    JSONObject responseOBJ = null;
                    try
                    {
                        responseOBJ = new JSONObject(response);
                        String score = responseOBJ.getString("score");
                        voteScore.setText(score);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            //serverResponse.setText("Please make sure your credentials are valid");
                        }
                    }) {
                ;

                @Override
                public Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("post_id", bundlePostID + "");
                    return params;
                }
                //https://gist.github.com/mombrea/7250835
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    public class CommentsObjects
    {
        String commentBody;
        ArrayList<String> dateCreated = new ArrayList<String>();
        int commentID;
        int commentD_votes;
        int commentUp_votes;
        Boolean Is_Like;
        ArrayList<String> dateVoted = new ArrayList<String>();          //date of upvote and downvote
    }

    void ParseJson(String JSONOBJECT)
    {
        if (JSONOBJECT != null)
        {
            try
            {
                commentOBJ = new JSONObject(JSONOBJECT);

                JSONArray commentsARR = commentOBJ.getJSONArray("comments");


                //.Toast.makeText(getApplicationContext(), commentsARR.length() + "", Toast.LENGTH_LONG).show();

                for (int i = 0; i < commentsARR.length(); i++)
                {
                    JSONObject obj = commentsARR.getJSONObject(i);

                    CommentsObjects commentOBJS = new CommentsObjects();

                    //Toast.makeText(getApplicationContext(), commentsARR.length() + "", Toast.LENGTH_LONG).show();
                    if (obj.has("c_body")) {
                        commentOBJS.commentBody = obj.getString("c_body");
                        //Toast.makeText(getApplicationContext(), commentOBJS.commentBody, Toast.LENGTH_LONG).show();
                    }

                    if (obj.has("c_downvotes")) {
                        //Toast.makeText(getApplicationContext(), "downvotres", Toast.LENGTH_LONG).show();
                        commentOBJS.commentD_votes = obj.getInt("c_downvotes");
                    }
                    if (obj.has("c_id")) {
                        //Toast.makeText(getApplicationContext(), "comment id", Toast.LENGTH_LONG).show();
                        commentOBJS.commentID = obj.getInt("c_id");
                    }
                    if (obj.has("c_upvotes")) {
                        //Toast.makeText(getApplicationContext(), "upvotes", Toast.LENGTH_LONG).show();
                        commentOBJS.commentUp_votes = obj.getInt("c_upvotes");
                    }

                    /*
                    if (obj.has("l_is_like")) {
                        //Toast.makeText(getApplicationContext(), "is liked", Toast.LENGTH_LONG).show();
                        commentOBJS.Is_Like = obj.getBoolean("l_is_like");
                    }
                    */

                    //Toast.makeText(getApplicationContext(), "did it make it to the end", Toast.LENGTH_LONG).show();
                    ArrayComments.add(commentOBJS);
                }


                commentsList = (ListView) findViewById(R.id.commentList);
                commentAdapter = new ListAdapter(this);
                commentsList.setAdapter(commentAdapter);        //call creation of listview

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }


    class ListAdapter extends BaseAdapter
    {
        Context context;

        ImageButton upvote = (ImageButton) findViewById(R.id.CommentUpvote);
        ImageButton downVote = (ImageButton) findViewById(R.id.CommentUpvote);


        ListAdapter(Context c)
        {
            context = c;

        }

        @Override
        public int getCount()
        {
            return ArrayComments.size();
        }

        @Override
        public Object getItem(int position)
        {
            return ArrayComments.get(position);
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
            View row = inflater.inflate(R.layout.complete_comment_layout, parent, false);
            TextView body = (TextView) row.findViewById(R.id.commentBody);
            TextView time = (TextView) row.findViewById(R.id.commentText);
            final TextView voteScore = (TextView) row.findViewById(R.id.CommentVoteScore);
            //ImageButton report = (ImageButton) row.findViewById(R.id.CommentReport);
            ImageButton Upvoting = (ImageButton) findViewById(R.id.CommentUpvote);
            ImageButton DownVoting = (ImageButton) findViewById(R.id.CommentDownvote);

            final CommentsObjects temp = ArrayComments.get(position);

            body.setText(temp.commentBody);
            voteScore.setText(temp.commentUp_votes - temp.commentD_votes + "");

            ((ImageButton)row.findViewById(R.id.CommentUpvote)).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
                    String savedAPIKEY = pref.getString("api_KEY", null);
                    if (savedAPIKEY != null)
                    {
                        //Toast.makeText(getApplicationContext(), temp.body, Toast.LENGTH_LONG).show();
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = "https://dev.collegeconfessions.party/api/comments/upvote?apikey=" + savedAPIKEY;

                        // Request a string response from the provided URL.
                        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                JSONObject responseOBJ = null;
                                try
                                {
                                    responseOBJ = new JSONObject(response);
                                    String score = responseOBJ.getString("score");
                                    voteScore.setText(score);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error)
                                    {
                                        //serverResponse.setText("Please make sure your credentials are valid");
                                    }
                                })
                        {;
                            @Override
                            public Map<String, String> getParams()
                            {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("comment_id", temp.commentID + "");
                                return params;
                            }
                            //https://gist.github.com/mombrea/7250835
                        };

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    }
                }
            });

            ((ImageButton)row.findViewById(R.id.CommentDownvote)).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SharedPreferences pref = getSharedPreferences("API_key", MODE_PRIVATE);
                    String savedAPIKEY = pref.getString("api_KEY", null);
                    if (savedAPIKEY != null)
                    {
                        //Toast.makeText(getApplicationContext(), temp.body, Toast.LENGTH_LONG).show();
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = "https://dev.collegeconfessions.party/api/comments/downvote?apikey=" + savedAPIKEY;

                        // Request a string response from the provided URL.
                        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                JSONObject responseOBJ = null;
                                try
                                {
                                    responseOBJ = new JSONObject(response);
                                    String score = responseOBJ.getString("score");
                                    voteScore.setText(score);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error)
                                    {
                                        //serverResponse.setText("Please make sure your credentials are valid");
                                    }
                                })
                        {;
                            @Override
                            public Map<String, String> getParams()
                            {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("comment_id", temp.commentID + "");
                                return params;
                            }
                            //https://gist.github.com/mombrea/7250835
                        };

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    }
                }
            });




            return row;
        }

    }



}
