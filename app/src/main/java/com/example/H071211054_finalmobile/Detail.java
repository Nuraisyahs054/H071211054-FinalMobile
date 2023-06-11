package com.example.H071211054_finalmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class Detail extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ImageView favorit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView latar = findViewById(R.id.latar_belakang);
        ImageView back = findViewById(R.id.back);
        favorit = findViewById(R.id.love);
        ImageView poster = findViewById(R.id.poster);
        TextView judul = findViewById(R.id.judul);
        TextView rating = findViewById(R.id.rating);
        TextView sinopsis = findViewById(R.id.sinopsis);
        dbHelper = new DatabaseHelper(this);

        back.setOnClickListener(view -> finish());

        Intent intent = getIntent();
        if (intent.getParcelableExtra("movie") != null) {
            Movie movie = intent.getParcelableExtra("movie");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movie.getPosterPath();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movie.getBackdropUrl();
            judul.setText(movie.getTitle());
            rating.setText(movie.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(poster);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(latar);
            sinopsis.setText(movie.getOverview());

            if (dbHelper.isFavorites(movie.getTitle())) {
                favorit.setImageResource(R.drawable.light_heart);
            }

            favorit.setOnClickListener(view ->  {
                if (!dbHelper.isFavorites(movie.getTitle())) {
                    favorit.setImageResource(R.drawable.light_heart);
                    addToFavorites(movie.getId(), movie.getOverview(), posterUrl, movie.getReleaseDate(), movie.getTitle(), movie.getVoteAverage(), backdropUrl);
                } else {
                    favorit.setImageResource(R.drawable.light_emptyheart);
                    deleteFromFavorites(movie.getTitle());
                }
            });
        } else if (intent.getParcelableExtra("show") != null) {
            TvShow show = intent.getParcelableExtra("show");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + show.getPosterPath();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + show.getBackdropUrl();
            judul.setText(show.getTitle());
            rating.setText(show.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(poster);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(latar);
            sinopsis.setText(show.getOverview());

            if (dbHelper.isFavorites(show.getTitle())) {
                favorit.setImageResource(R.drawable.light_heart);
            }

            favorit.setOnClickListener(view -> {
                if (!dbHelper.isFavorites(show.getTitle())) {
                    favorit.setImageResource(R.drawable.light_heart);
                    addToFavorites(show.getId(), show.getOverview(), posterUrl, show.getTitle(), show.getTitle(), show.getVoteAverage(), backdropUrl);
                } else {
                    favorit.setImageResource(R.drawable.light_emptyheart);
                    deleteFromFavorites(show.getTitle());
                }
            });
        }
        else {
            Favorite favorite = intent.getParcelableExtra("favorite");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + favorite.getPosterPath();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + favorite.getBackdropUrl();
            judul.setText(favorite.getTitle());
            rating.setText(favorite.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(poster);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(latar);
            sinopsis.setText(favorite.getOverview());

            if (dbHelper.isFavorites(favorite.getTitle())) {
                favorit.setImageResource(R.drawable.light_heart);
            }

            favorit.setOnClickListener(view -> {
                if (!dbHelper.isFavorites(favorite.getTitle())) {
                    favorit.setImageResource(R.drawable.light_heart);
                    addToFavorites(favorite.getId(), favorite.getOverview(), posterUrl, favorite.getTitle(), favorite.getTitle(), favorite.getVoteAverage(), backdropUrl);
                } else {
                    favorit.setImageResource(R.drawable.light_emptyheart);
                    deleteFromFavorites(favorite.getTitle());
                }
            });
        }
    }

    private void addToFavorites(int id, String overview, String posterUrl, String releaseDate, String title, double voteAverage, String backdropUrl) {
        Favorite favorite = new Favorite(id, overview, posterUrl, releaseDate, title, voteAverage, backdropUrl);
        long result = dbHelper.insertFavorite(favorite);
        if (result != -1) {
            Toast.makeText(this, "Movie / Show added to favorites", Toast.LENGTH_SHORT).show();
            // Notify MainActivity that favorites have been updated
            if (getParent() instanceof FavoriteUpdateListener) {
                ((FavoriteUpdateListener) getParent()).onFavoritesUpdated();
            }
        } else {
            Toast.makeText(this, "Failed to add to favorite", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteFromFavorites(String nama) {
        long result = dbHelper.deleteFavorite(nama);
        if (result != -1) {
            Toast.makeText(this, "Movie / Show Deleted From favorites", Toast.LENGTH_SHORT).show();
            notifyFavoritesUpdated();
        } else {
            Toast.makeText(this, "Failed to delete movie", Toast.LENGTH_SHORT).show();
        }
    }

    private void notifyFavoritesUpdated() {
        Activity parentActivity = getParent();
        if (parentActivity instanceof FavoriteUpdateListener) {
            FavoriteUpdateListener listener = (FavoriteUpdateListener) parentActivity;
            listener.onFavoritesUpdated();
        }
    }
}
