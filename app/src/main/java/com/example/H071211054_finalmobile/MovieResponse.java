package com.example.H071211054_finalmobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> movies;
    public List<Movie> getMovies() {
        return movies;
    }
}



