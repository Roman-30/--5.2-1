package com.goncharenko.musiczoneapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.goncharenko.musiczoneapp.R;

public class PlayerFragment extends Fragment {

    private ImageButton replayButton;
    private ImageButton playButton;
    private ImageButton nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        replayButton = view.findViewById(R.id.replay_button);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replayMusic(v);
            }
        });

        playButton = view.findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic(v);
            }
        });

        nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusic(v);
            }
        });

        return view;
    }

    public void replayMusic(View view) {
    }

    public void playMusic(View view) {
    }

    public void nextMusic(View view) {
    }
}