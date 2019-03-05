package com.example.myfabflix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }


    public void connectToTomcat(View view) {

        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        Log.d("email", username);
        Log.d("password", password);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", username);
        params.put("password", password);

        final Intent goToIntent = new Intent(this, SearchActivity.class);

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;


//        final StringRequest afterLoginRequest = new StringRequest(Request.Method.GET, "https://10.0.2.2:8443/2019w-project4-login-example/api/login",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("response2$$$$$$$$", response);
//                        JSONObject jsonObject = new JSONObject();
//                        try {
//                            jsonObject = new JSONObject(response);
//                            Log.d("$$$$$$$$$$$TATU$$$$", jsonObject.getString("status"));
//                            if(jsonObject.getString("status").equals("success")) {
//                                goToIntent.putExtra("message", "Welcome to Fabflix!");
//                                startActivity(goToIntent);
//                            }
//                            else
//                                Toast.makeText(getApplicationContext(), "Incorrect Login Information", Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        ((TextView) findViewById(R.id.http_response)).setText(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("security.error", error.toString());
//                    }
//                }
//        );


        final StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://18.191.51.103:8443/project4/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject = new JSONObject(response);
                            Log.d("status", jsonObject.getString("status"));
                            if(jsonObject.getString("status").equals("success")) {
                                goToIntent.putExtra("message", "Welcome to Fabflix!");
                                startActivity(goToIntent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Incorrect Login Information", Toast.LENGTH_SHORT).show();
                                ((TextView) findViewById(R.id.http_response)).setText(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Add the request to the RequestQueue.
//                        queue.add(afterLoginRequest);
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

//        afterLoginRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(loginRequest);




    }
}