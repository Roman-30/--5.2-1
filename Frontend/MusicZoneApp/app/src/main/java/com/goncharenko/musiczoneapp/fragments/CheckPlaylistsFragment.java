package com.goncharenko.musiczoneapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.models.PlaylistModel;
import com.goncharenko.musiczoneapp.viewmodels.MusicViewModel;

import java.util.ArrayList;
import java.util.List;

public class CheckPlaylistsFragment extends Fragment {

    public static final String TAG = CheckPlaylistsFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private List<PlaylistModel> playlists = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_playlists, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        playlists.add(new PlaylistModel("PLaylist_1", "Mike"));
        playlists.add(new PlaylistModel("PLaylist_2", "John"));
        playlists.add(new PlaylistModel("PLaylist_3", "Kurt"));
        playlists.add(new PlaylistModel("PLaylist_4", "Brad"));

        setRecyclerView(playlists);

        return view;
    }

    private void setNewFragment(Fragment fragment, String tag){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }
    
    private void setRecyclerView(List<PlaylistModel> playlists){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PlaylistsListAdapter(playlists, getContext().getApplicationContext()));
    }
}