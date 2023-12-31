package com.example.H071211054_finalmobile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowFragment extends Fragment {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "6d8cf777f0f176b8ecc877943e88d331";
    private RecyclerView recyclerView;
    private TvShowAdapter tvAdapter;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TvShowService tvShowService = retrofit.create(TvShowService.class);

        Call<TvShowResponse> call = tvShowService.getAiringTodayTV(API_KEY);

        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    TvShowResponse tvShowResponse = response.body();
                    List<TvShow> tvShow = tvShowResponse.getTvShows();
                    tvAdapter = new TvShowAdapter(tvShow);
                    recyclerView.setAdapter(tvAdapter);

                    CustomLayoutManager layoutManager = new CustomLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(layoutManager);

                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

