package com.goncharenko.musiczoneapp.adapters;

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
import com.goncharenko.musiczoneapp.clickinterface.ButtonClickInterface;
import com.goncharenko.musiczoneapp.clickinterface.ItemClickInterface;
import com.goncharenko.musiczoneapp.fragments.MyMediaPlayer;
import com.goncharenko.musiczoneapp.models.AudioModel;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    private ArrayList<AudioModel> songsList;
    private Context context;
    private ItemClickInterface itemClickInterface;
    private ButtonClickInterface buttonClickInterface;

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
        holder.authorTextView.setText(songData.getAuthor());

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
        TextView authorTextView;
        ImageView iconImageView;

        ImageButton optionsButton;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            authorTextView = itemView.findViewById(R.id.music_author_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
            optionsButton = itemView.findViewById(R.id.options_button);
            titleTextView.setSelected(true);
            authorTextView.setSelected(true);
        }
    }
}
