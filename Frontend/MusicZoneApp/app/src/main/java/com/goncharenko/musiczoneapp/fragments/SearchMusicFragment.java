package com.goncharenko.musiczoneapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.EditAccountActivity;
import com.goncharenko.musiczoneapp.activities.EditMusicActivity;
import com.goncharenko.musiczoneapp.activities.MainListener;
import com.goncharenko.musiczoneapp.adapters.MusicListAdapter;
import com.goncharenko.musiczoneapp.clickinterface.ButtonClickInterface;
import com.goncharenko.musiczoneapp.clickinterface.ItemClickInterface;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.service.AudioService;
import com.goncharenko.musiczoneapp.viewmodels.MusicViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMusicFragment extends Fragment implements ItemClickInterface, ButtonClickInterface {
    public static final String TAG = SearchMusicFragment.class.getSimpleName();
    private RecyclerView recyclerView;

    private ImageView searchButton;

    private Button checkPlaylistButton;
    private EditText inputSearch;

    private final String SEARCH_KEY = "search";
    private final String LIST_KEY = "list";
    private String search = "";

    private ArrayList<AudioModel> songsList = new ArrayList<>();
    private ArrayList<AudioModel> savedSongsList = new ArrayList<>();

    private MusicViewModel musicViewModel;

    private MainListener mainListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            search = savedInstanceState.getString(SEARCH_KEY);
            savedSongsList = (ArrayList<AudioModel>) savedInstanceState.getSerializable(LIST_KEY);
        }

        songsList.clear();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_music, container, false);

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view);
        inputSearch = view.findViewById(R.id.input_search);
        inputSearch.setText(search);

        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> searchMusic());

//        String[] projection = {
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.DATA,
//                MediaStore.Audio.Media.DURATION
//        };
//
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
//
//        Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
//        while (cursor.moveToNext()) {
//            AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
//            if (new File(songData.getPath()).exists())
//                songsList.add(songData);
//        }

        songsList.clear();
        musicViewModel.loadSongs();
        musicViewModel.getSongsList().observe(getViewLifecycleOwner(), audioModels -> {
            songsList.clear();
            songsList.addAll(audioModels);
            if (songsList.size() == 0) {
                // обработка если нет музыки
            } else {
                if (savedSongsList.size() != 0) {
                    setRecyclerView(savedSongsList);
                } else {
                    setRecyclerView(songsList);
                }
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_KEY, inputSearch.toString().trim());
        outState.putSerializable(LIST_KEY, (Serializable) savedSongsList);
    }

    public void searchMusic() {
        String searchQuery = inputSearch.getText().toString().trim();
        if (searchQuery.equals("")){
            if(songsList.size() == 0){
                // обработка если нет музыки
            }else{
                savedSongsList = songsList;
                setRecyclerView(songsList);
            }
        } else {
            savedSongsList = new ArrayList<>();
            for (AudioModel song : songsList) {
                if (song.getTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        song.getAuthor().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        song.getGenre().toLowerCase().contains(searchQuery.toLowerCase())) {
                    savedSongsList.add(song);
                }
            }

            setRecyclerView(savedSongsList);
        }
    }

    @Override
    public void onItemClick(int id) {
        MyMediaPlayer.getInstance().reset();
        MyMediaPlayer.currentIndex = id;

        if(savedSongsList.size() != 0){
            mainListener.setOnAudioModel(savedSongsList);
            musicViewModel.setSongsList(savedSongsList);
        } else {
            mainListener.setOnAudioModel(songsList);
            musicViewModel.setSongsList(songsList);
        }

        Fragment playerFragment = getActivity().getSupportFragmentManager().findFragmentByTag(PlayerFragment.TAG);
        if(playerFragment != null){
        } else {
            playerFragment = new PlayerFragment();
        }
        setNewFragment(playerFragment, PlayerFragment.TAG);
    }

    @Override
    public void onItemButtonClick(int id) {
        if(!mainListener.getOnSignedIn()){
            Toast.makeText(getContext(),
                    "You are not registered",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                getContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_bottom_sheet,
                (ConstraintLayout) getActivity().findViewById(R.id.bottomSheetContainer)
        );

        bottomSheetView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AudioModel audioModel = songsList.get(id);
//                mainListener.setOnAudioModel(audioModel);

                AudioService
                        .getInstance()
                        .getJSON()
                        .addMusic(mainListener.getOnEmail(), songsList.get(id).getId()).enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                Toast.makeText(getContext(),
                                        "Song is added",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<List<String>> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "Error",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //bottomSheetView.findViewById(R.id.remove_button).setVisibility(View.GONE);
        bottomSheetView.findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioService
                        .getInstance()
                        .getJSON()
                        .deleteNewMusic(songsList.get(id).getId()).enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                Toast.makeText(getContext(),
                                        "Song is removed",
                                        Toast.LENGTH_SHORT).show();

                                songsList.remove(id);
                            }

                            @Override
                            public void onFailure(Call<List<String>> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "Error",
                                        Toast.LENGTH_SHORT).show();
                                songsList.remove(id);

                            }
                        });

                setRecyclerView(songsList);
            }
        });
        //bottomSheetView.findViewById(R.id.edit_button).setVisibility(View.GONE);

        bottomSheetView.findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            AudioModel song = songsList.get(id);
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
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    private void setNewFragment(Fragment fragment, String tag){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void setRecyclerView(ArrayList<AudioModel> songsList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MusicListAdapter(songsList, getContext().getApplicationContext(), this::onItemClick, this::onItemButtonClick));
    }
}