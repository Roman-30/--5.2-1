package com.goncharenko.musiczoneapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.Toast;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.AddMusicActivity;
import com.goncharenko.musiczoneapp.activities.EditMusicActivity;
import com.goncharenko.musiczoneapp.activities.MainListener;
import com.goncharenko.musiczoneapp.adapters.MusicListAdapter;
import com.goncharenko.musiczoneapp.clickinterface.ButtonClickInterface;
import com.goncharenko.musiczoneapp.clickinterface.ItemClickInterface;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.service.AudioService;
import com.goncharenko.musiczoneapp.viewmodels.MusicViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMusicFragment extends Fragment implements ItemClickInterface, ButtonClickInterface {

    public static final String TAG = AdminMusicFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private ImageView addMusicButton;

    private ArrayList<AudioModel> addSongsList = new ArrayList<>();

    private MusicViewModel musicViewModel;

    private MainListener mainListener;

    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_music, container, false);

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_view);
        addMusicButton = view.findViewById(R.id.add_music_button);
        addMusicButton.setOnClickListener(v -> addMusic());

        email = mainListener.getOnEmail();
        if(email.equals("")){
            return view;
        }
        addSongsList.clear();

        AudioService.getInstance().getJSON().getSavedMusic(email).enqueue(new Callback<List<AudioModel>>() {
            @Override
            public void onResponse(Call<List<AudioModel>> call, Response<List<AudioModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        addSongsList.clear();
                        addSongsList.addAll(response.body());

                        if (addSongsList.size() == 0) {
                            // обработка если нет музыки
                        } else {
                            setRecyclerView(addSongsList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AudioModel>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainListener) {
            mainListener = (MainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SignInListener");
        }
    }

    public void addMusic(){
        Intent intent = new Intent(getActivity(), AddMusicActivity.class);
        intent.putExtra("email", mainListener.getOnEmail());
        startActivity(intent);
        getActivity().finish();
        MediaPlayer myMediaPlayer = MyMediaPlayer.getInstance();
        myMediaPlayer.stop();
    }

    @Override
    public void onItemClick(int id) {
        MyMediaPlayer.getInstance().reset();
        MyMediaPlayer.currentIndex = id;
        
        mainListener.setOnAudioModel(addSongsList);
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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                getContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_bottom_sheet,
                (ConstraintLayout) getActivity().findViewById(R.id.bottomSheetContainer)
        );

        bottomSheetView.findViewById(R.id.add_button).setVisibility(View.GONE);
        bottomSheetView.findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioService
                        .getInstance()
                        .getJSON()
                        .deleteNewMusic(addSongsList.get(id).getId()).enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                Toast.makeText(getContext(),
                                        "Song is removed",
                                        Toast.LENGTH_SHORT).show();

                                addSongsList.remove(id);
                            }

                            @Override
                            public void onFailure(Call<List<String>> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "Error",
                                        Toast.LENGTH_SHORT).show();
                                addSongsList.remove(id);

                            }
                        });

                setRecyclerView(addSongsList);
            }
        });

        bottomSheetView.findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            AudioModel song = addSongsList.get(id);
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditMusicActivity.class);
                intent.putExtra("email", mainListener.getOnEmail());
                intent.putExtra("id", song.getId());
                intent.putExtra("title", song.getTitle());
                intent.putExtra("author", song.getAuthor());
                intent.putExtra("genre", song.getGenre());
                intent.putExtra("path", song.getPath());
                startActivity(intent);
                getActivity().finish();
                MediaPlayer myMediaPlayer = MyMediaPlayer.getInstance();
                myMediaPlayer.stop();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
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