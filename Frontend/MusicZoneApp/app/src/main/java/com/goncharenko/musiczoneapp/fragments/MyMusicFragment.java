package com.goncharenko.musiczoneapp.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.AudioModel;

import java.io.File;
import java.util.ArrayList;

public class MyMusicFragment extends Fragment implements ItemClickInterface{
    public static final String TAG = MyMusicFragment.class.getSimpleName();
    private RecyclerView recyclerView;

    private ImageButton searchButton;
    private EditText inputSearch;

    private final String SEARCH_KEY = "search";
    private final String LIST_KEY = "list";
    private String search = "";
    ArrayList<AudioModel> songsList = new ArrayList<>();

    ArrayList<AudioModel> savedSongsList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            search = savedInstanceState.getString(SEARCH_KEY);
            savedSongsList = (ArrayList<AudioModel>) savedInstanceState.getSerializable(LIST_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_music, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        inputSearch = view.findViewById(R.id.input_search);
        inputSearch.setText(search);

        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMusic();
            }
        });


        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";

        Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext()){
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        if(songsList.size() == 0){
            // обработка если нет музыки
        }else{
            if(savedSongsList.size() != 0){
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new MusicListAdapter(savedSongsList, getContext().getApplicationContext(), this::onItemClick));
            }else {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new MusicListAdapter(songsList, getContext().getApplicationContext(), this::onItemClick));
            }
        }


        return view;
    }

    public void searchMusic() {
        String searchQuery = inputSearch.getText().toString().trim();
        if (searchQuery.equals("")){
            if(songsList.size() == 0){
                // обработка если нет музыки
            }else{
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new MusicListAdapter(songsList, getContext().getApplicationContext(), this::onItemClick));
            }
        } else {
            savedSongsList = new ArrayList<>();
            for (AudioModel song : songsList) {
                if (song.getTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        song.getDuration().toLowerCase().contains(searchQuery.toLowerCase())) {
                    savedSongsList.add(song);
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new MusicListAdapter(savedSongsList, getContext().getApplicationContext(), this::onItemClick));
        }
    }

    @Override
    public void onItemClick(int id) {

    }
}