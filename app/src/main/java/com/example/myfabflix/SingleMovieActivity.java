package com.example.myfabflix;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

public class SingleMovieActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_movie);

        Bundle bundle = getIntent().getExtras();
        String movie_name = bundle.getString("movie_name");
        String movie_year = bundle.getString("movie_year");
        String movie_director = bundle.getString("movie_director");
        String stars = bundle.getString("stars");
        String genres = bundle.getString("genres");

        ((TextView) findViewById(R.id.movie_name)).setText(movie_name);
        ((TextView) findViewById(R.id.movie_year)).setText(movie_year);
        ((TextView) findViewById(R.id.movie_director)).setText(movie_director);
        ((TextView) findViewById(R.id.star)).setText(stars);
        ((TextView) findViewById(R.id.genre)).setText(genres);

    }

}
