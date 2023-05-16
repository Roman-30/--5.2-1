package com.goncharenko.musiczoneapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.models.PlaylistModel;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsListAdapter extends RecyclerView.Adapter<PlaylistsListAdapter.ViewHolder>{
    private List<PlaylistModel> playlists = new ArrayList<>();
    private Context context;

    public PlaylistsListAdapter(List<PlaylistModel> playlists, Context context) {
        this.playlists = playlists;
        this.context = context;
    }

    @Override
    public PlaylistsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent,false);
        return new PlaylistsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistsListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PlaylistModel playlistModel = playlists.get(position);
        holder.titleTextView.setText(playlistModel.getTitle());

        if(MyMediaPlayer.currentIndex==position){
            holder.titleTextView.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
        }

        //holder.itemView.setOnClickListener(v -> itemClickInterface.onItemClick(position));
        //holder.optionsButton.setOnClickListener(v -> buttonClickInterface.onItemButtonClick(position));

    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.playlist_title_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
        }
    }

}
