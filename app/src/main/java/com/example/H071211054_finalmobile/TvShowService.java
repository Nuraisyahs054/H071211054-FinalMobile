package com.example.H071211054_finalmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowService {
    @GET("tv/popular")
    Call<TvShowResponse> getAiringTodayTV(
            @Query("api_key") String apiKey
    );
}
