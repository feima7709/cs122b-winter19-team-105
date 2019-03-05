package com.example.myfabflix;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import static android.widget.Toast.LENGTH_SHORT;




public class MovieListActivity extends Activity {
    private String movieName;
    private String page;
    private Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);


        Bundle bundle = getIntent().getExtras();
   //     Toast.makeText(this, "Movies", Toast.LENGTH_LONG).show();

        if(bundle !=null){
            String response = bundle.getString("response");
            this.movieName = bundle.getString("movie_name");
            this.page = bundle.getString("page");
            Log.d("movieName is ", movieName);
            Log.d("page is", page);

        if(response!=null){
            Log.d("search.response", response);
            JsonArray movie_array = new JsonParser().parse(response).getAsJsonArray();

            final ArrayList<Movie> movies = new ArrayList<>();

            JsonElement item = movie_array.get(0);
            JsonObject movie = item.getAsJsonObject();

            this.count = Integer.parseInt(movie.get("count").getAsString());

            Log.d("count is ", Integer.toString(count));

            for (int i=1; i<movie_array.size(); i++) {
                item = movie_array.get(i);
                movie = item.getAsJsonObject();

                String title = movie.get("movie_title").getAsString();

                String year = movie.get("movie_year").getAsString();

                String director = movie.get("movie_director").getAsString();

                ArrayList<String> genres = new ArrayList<>();

                JsonArray g = movie.getAsJsonArray("movie_genre_list");
                for (JsonElement gen : g) {
                    JsonObject genre = gen.getAsJsonObject();
                    genres.add(genre.get("genre_name").getAsString());
                }
                ArrayList<String> stars = new ArrayList<>();

                JsonArray s = movie.getAsJsonArray("movie_star_list");
                for (JsonElement st : s) {
                    JsonObject star = st.getAsJsonObject();
                    stars.add(star.get("star_name").getAsString());
                }

                movies.add(new Movie(title, year, director, genres, stars));
            }

            MovieListAdapter adapter = new MovieListAdapter(movies, this);

            ListView listView = (ListView)findViewById(R.id.movie_list);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = movies.get(position);
//                    String message = String.format("Clicked on position: %d, title: %s, %d, %s", position, movie.getMovieTitle(),
//                            movie.getMovieYear(), movie.getMovieDirector());
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent goToIntent = new Intent(MovieListActivity.this, SingleMovieActivity.class);
                    String movie_name =movie.getMovieTitle();
                    String movie_year =movie.getMovieYear();
                    String movie_director = movie.getMovieDirector();
                    String stars =movie.getStarString();
                    String genres =movie.getGenreString();

                    Log.d("movie info:  ", movie_name +"   "+movie_year+"  "+movie_director);

                    goToIntent.putExtra("movie_name", movie_name);
                    goToIntent.putExtra("movie_year", movie_year);
                    goToIntent.putExtra("movie_director", movie_director);
                    goToIntent.putExtra("stars", stars);
                    goToIntent.putExtra("genres",genres);
                    startActivity(goToIntent);



                }
            });
        }
        }




    }


    public void PrevPage(View view) {
      //  Intent goToIntent = new Intent(this, SearchActivity.class);
        Integer pagenum = Integer.parseInt(page);
        if(pagenum-1<1){
            Toast.makeText(getApplicationContext(), "NO Previous page", LENGTH_SHORT).show();
            return;

        }

        final String prevpage = Integer.toString(pagenum-1);
//        goToIntent.putExtra("movie_name",movieName);
//        goToIntent.putExtra("page", prevpage);
//        startActivity(goToIntent);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("type", "search");
        params.put("id", "1");
        params.put("sortBy", "r.rating%20DESC");
        params.put("pagesize", "10");
        params.put("page", "1");
        params.put("movie_name", movieName);


        final Intent goToIntent = new Intent(this, MovieListActivity.class);

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        final StringRequest loginRequest = new StringRequest(Request.Method.GET, "https://18.191.51.103:8443/project4/api/search-genre?" +
                "type=search&id=1&sortBy=r.rating%20DESC&pagesize=10&page="+prevpage+"&movie_name="+movieName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response$$$$$$$$$$", response);
                        if(response != null || !response.isEmpty()) {
                            goToIntent.putExtra("response", response);
                            goToIntent.putExtra("page", prevpage);
                            goToIntent.putExtra("movie_name", movieName);
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

    public void NextPage(View view) {
     //   Intent goToIntent = new Intent(this, SearchActivity.class);
        Integer pagenum = Integer.parseInt(page);

        if(pagenum+1>count){
            Toast.makeText(getApplicationContext(), "NO Next page", LENGTH_SHORT).show();
            return;

        }

        final String nextpage = Integer.toString(pagenum+1);


        Log.d("next page is ", nextpage);
//        goToIntent.putExtra("movie_name",movieName);
//        goToIntent.putExtra("page", nextpage);
//
//        startActivity(goToIntent);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("type", "search");
        params.put("id", "1");
        params.put("sortBy", "r.rating%20DESC");
        params.put("pagesize", "10");
        params.put("page", "1");
        params.put("movie_name", movieName);


        final Intent goToIntent = new Intent(this, MovieListActivity.class);

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        final StringRequest loginRequest = new StringRequest(Request.Method.GET, "https://18.191.51.103:8443/project4/api/search-genre?" +
                "type=search&id=1&sortBy=r.rating%20DESC&pagesize=10&page="+nextpage+"&movie_name="+movieName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response$$$$$$$$$$", response);
                        if(response != null || !response.isEmpty()) {
                            goToIntent.putExtra("response", response);
                            goToIntent.putExtra("page", nextpage);
                            goToIntent.putExtra("movie_name", movieName);
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
