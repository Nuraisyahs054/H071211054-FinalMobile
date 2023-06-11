package com.example.H071211054_finalmobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {
    @SerializedName("results")
    private List<TvShow> tvShowShows;

    public List<TvShow> getTvShows() {
        return tvShowShows;
    }
}

