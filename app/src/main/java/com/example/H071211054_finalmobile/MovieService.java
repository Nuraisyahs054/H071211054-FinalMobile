package com.example.H071211054_finalmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("movie/top_rated")
    Call<MovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey
    );
}
