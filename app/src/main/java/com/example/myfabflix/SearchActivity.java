package com.example.myfabflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity{

    private String page ="1";
    private String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);


    }




    public void connectToTomcat(View view) {
        final String movie_name = ((EditText) findViewById(R.id.search)).getText().toString();
        Log.d("fullTextSearch", movie_name);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("type", "search");
        params.put("id", "1");
        params.put("sortBy", "r.rating%20DESC");
        params.put("pagesize", "10");
        params.put("page", "1");
        params.put("movie_name", movie_name);


        final Intent goToIntent = new Intent(this, MovieListActivity.class);

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        final StringRequest loginRequest = new StringRequest(Request.Method.GET, "https://18.191.51.103:8443/project4/api/search-genre?" +
                "type=search&id=1&sortBy=r.rating%20DESC&pagesize=10&page="+page+"&movie_name="+movie_name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response$$$$$$$$$$", response);
                        if(response != null || !response.isEmpty()) {
                            goToIntent.putExtra("response", response);
                            goToIntent.putExtra("page", page);
                            goToIntent.putExtra("movie_name", movie_name);
                            startActivity(goToIntent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }  // HTTP POST Form Datz
        };

        loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(loginRequest);




    }
}
