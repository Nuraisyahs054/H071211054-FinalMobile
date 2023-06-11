package com.example.H071211054_finalmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {
    private List<TvShow> tvShowShows;

    public TvShowAdapter(List<TvShow> tvShowShows) {
        this.tvShowShows = tvShowShows;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        TvShow tvShow = tvShowShows.get(position);
        holder.setData(tvShow, context);
    }

    @Override
    public int getItemCount() {
        return tvShowShows != null ? tvShowShows.size() : 0;
    }

    static class TvViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView judul;
        private TextView tahun;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            judul = itemView.findViewById(R.id.judul);
            tahun = itemView.findViewById(R.id.tahun);
        }

        public void setData(TvShow tvShow, Context context) {
            String title = tvShow.getTitle();
            String year = tvShow.getFirstAirDate();
            String gambar = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2/" + tvShow.getPosterPath();
            judul.setText(title);
            tahun.setText(year);
            Glide.with(context)
                    .load(gambar)
                    .into(poster);
            this.itemView.setOnClickListener(view ->  {
                Intent intent = new Intent(itemView.getContext(), Detail.class);
                intent.putExtra("show", tvShow);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}




