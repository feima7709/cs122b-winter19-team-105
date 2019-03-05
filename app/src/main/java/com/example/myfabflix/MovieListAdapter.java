package com.example.myfabflix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieListAdapter extends ArrayAdapter<Movie>{
    private ArrayList<Movie> movies;

    public MovieListAdapter(ArrayList<Movie> movies, Context context) {
        super(context, R.layout.movie_row, movies);
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.movie_row, parent, false);

        Movie movie = movies.get(position);

        TextView movie_nameView = (TextView)view.findViewById(R.id.movie_name);
        TextView movie_yearView = (TextView)view.findViewById(R.id.movie_year);
        TextView movie_directorView = (TextView)view.findViewById(R.id.movie_director);
        TextView starView = (TextView)view.findViewById(R.id.star);
        TextView genreView = (TextView)view.findViewById(R.id.genre);

        movie_nameView.setText(movie.getMovieTitle());
        movie_yearView.setText("Year : "+ movie.getMovieYear());
        movie_directorView.setText("Director : " + movie.getMovieDirector());
        genreView.setText("Genre : "+movie.getGenreString());
        starView.setText("Stars : "+movie.getStarString());


        return view;
    }

}

