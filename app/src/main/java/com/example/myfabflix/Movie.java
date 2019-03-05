package com.example.myfabflix;

import java.util.ArrayList;

public class Movie {
  //  private String movieId;
    private String movieTitle;
    private String movieYear;
    private String movieDirector;
    private ArrayList<String> genres;
    private ArrayList<String> stars;
 //   private String rating;

    public Movie( String movie_title, String movie_year,
                 String movie_director, ArrayList<String> genreNames, ArrayList<String> starNames) {
      //  this.movieId = movieId;
        this.movieTitle = movie_title;
        this.movieYear = movie_year;
        this.movieDirector = movie_director;
        this.genres = genreNames;
        this.stars = starNames;
      //  this.rating = rating;
    }

//    public String getMovieId() {
//        return movieId;
//    }

    public String getMovieTitle() { return movieTitle; }

    public String getMovieYear() {
        return movieYear;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getStars() { return stars; }

    public String getGenreString (){
        String genreString="";
        for(int i=0; i<genres.size(); i++){
            genreString += genres.get(i);
            genreString +="   ";

        }
        return genreString;
    }

    public String getStarString(){
        String starString="";
        for(int i=0; i<stars.size(); i++){
            starString += stars.get(i);
            starString+="   ";
        }

        return starString;
    }


//    public String getRating() { return rating; }

}
