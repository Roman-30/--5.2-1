package com.goncharenko.musiczoneapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.goncharenko.musiczoneapp.R;

public class MyMusicFragment extends Fragment {

    private String[] music = new String[]{"music_1", "music_2", "music_3", "music_4", "music_5",
            "music_1", "music_2", "music_3", "music_4", "music_5",
            "music_1", "music_2", "music_3", "music_4", "music_5"};
    private ListView listView;

    private ImageButton searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_music, container, false);

        listView = view.findViewById(R.id.list_music);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.music_item, R.id.music_name, music);
        listView.setAdapter(adapter);

        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMusic(v);
            }
        });

        return view;
    }

    public void searchMusic(View view) {
    }
}