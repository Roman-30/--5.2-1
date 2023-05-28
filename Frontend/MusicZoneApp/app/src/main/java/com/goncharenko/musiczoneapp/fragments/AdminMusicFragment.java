package com.goncharenko.musiczoneapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.AddMusicActivity;
import com.goncharenko.musiczoneapp.adapters.MusicListAdapter;
import com.goncharenko.musiczoneapp.clickinterface.ButtonClickInterface;
import com.goncharenko.musiczoneapp.clickinterface.ItemClickInterface;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.viewmodels.MusicViewModel;

import java.io.File;
import java.util.ArrayList;

public class AdminMusicFragment extends Fragment implements ItemClickInterface, ButtonClickInterface {

    public static final String TAG = AdminMusicFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private ImageView addMusicButton;

    private ArrayList<AudioModel> addSongsList = new ArrayList<>();

    private MusicViewModel musicViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_music, container, false);

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_view);
        addMusicButton = view.findViewById(R.id.add_music_button);
        addMusicButton.setOnClickListener(v -> addMusic());

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
        while (cursor.moveToNext()) {
            AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
            if (new File(songData.getPath()).exists())
                addSongsList.add(songData);
        }

        if(addSongsList.size() == 0){
            // обработка если нет музыки
        }else{
            setRecyclerView(addSongsList);
        }

        return view;
    }

    public void addMusic(){
        Intent intent = new Intent(getActivity(), AddMusicActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int id) {
        MyMediaPlayer.getInstance().reset();
        MyMediaPlayer.currentIndex = id;

        musicViewModel.setSongsList(addSongsList);

        Fragment playerFragment = getActivity().getSupportFragmentManager().findFragmentByTag(PlayerFragment.TAG);
        if(playerFragment != null){
        } else {
            playerFragment = new PlayerFragment();
        }
        setNewFragment(playerFragment, PlayerFragment.TAG);
    }

    @Override
    public void onItemButtonClick(int id) {

    }

    private void setRecyclerView(ArrayList<AudioModel> songsList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MusicListAdapter(songsList, getContext().getApplicationContext(), this::onItemClick, this::onItemButtonClick));
    }
    private void setNewFragment(Fragment fragment, String tag){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }
}