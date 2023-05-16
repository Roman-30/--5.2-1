package com.goncharenko.musiczoneapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.MainActivity;
import com.goncharenko.musiczoneapp.models.AudioModel;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    ArrayList<AudioModel> songsList;
    Context context;
    ItemClickInterface itemClickInterface;
    ButtonClickInterface buttonClickInterface;

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context, ItemClickInterface itemClickInterface, ButtonClickInterface buttonClickInterface) {
        this.songsList = songsList;
        this.context = context;
        this.itemClickInterface = itemClickInterface;
        this.buttonClickInterface = buttonClickInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_item, parent,false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle());

        if(MyMediaPlayer.currentIndex==position){
            holder.titleTextView.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(v -> itemClickInterface.onItemClick(position));
        holder.optionsButton.setOnClickListener(v -> buttonClickInterface.onItemButtonClick(position));

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView iconImageView;

        ImageButton optionsButton;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
            optionsButton = itemView.findViewById(R.id.options_button);
            titleTextView.setSelected(true);
        }
    }
}
