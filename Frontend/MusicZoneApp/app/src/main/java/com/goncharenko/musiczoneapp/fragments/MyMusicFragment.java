package com.goncharenko.musiczoneapp.fragments;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.MainListener;
import com.goncharenko.musiczoneapp.adapters.MusicListAdapter;
import com.goncharenko.musiczoneapp.clickinterface.ButtonClickInterface;
import com.goncharenko.musiczoneapp.clickinterface.ItemClickInterface;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.service.AudioService;
import com.goncharenko.musiczoneapp.viewmodels.MusicViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yandex.metrica.YandexMetrica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMusicFragment extends Fragment implements ItemClickInterface, ButtonClickInterface {
    public static final String TAG = MyMusicFragment.class.getSimpleName();
    private RecyclerView recyclerView;

    private ImageView searchButton;
    private EditText inputSearch;

    private final String SEARCH_KEY = "search";
    private final String LIST_KEY = "list";
    private final String LIST_SAVED_KEY = "saved";
    private String search = "";
    private ArrayList<AudioModel> songsList = new ArrayList<>();

    private ArrayList<AudioModel> savedAfterSearchSongsList = new ArrayList<>();

    private MainListener mainListener;
    private MusicViewModel musicViewModel;

    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            search = savedInstanceState.getString(SEARCH_KEY);
            savedAfterSearchSongsList = (ArrayList<AudioModel>) savedInstanceState.getSerializable(LIST_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_music, container, false);

        YandexMetrica.reportEvent("Пользователь зашел на фрагмент со своей добаленной музыкой");
        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

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



        email = mainListener.getOnEmail();
        if(email.equals("")){
            return view;
        }
        songsList.clear();

        AudioService.getInstance().getJSON().getSavedMusic(email).enqueue(new Callback<List<AudioModel>>() {
            @Override
            public void onResponse(Call<List<AudioModel>> call, Response<List<AudioModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        songsList.clear();
                        songsList.addAll(response.body());

                        if (songsList.size() == 0) {
                            // обработка если нет музыки
                        } else {
                            if (savedAfterSearchSongsList.size() != 0) {
                                setRecyclerView(savedAfterSearchSongsList);
                            } else {
                                setRecyclerView(songsList);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AudioModel>> call, Throwable t) {

            }
        });


//        musicViewModel.loadSavedSongsList(email);
//        musicViewModel.getSavedSongsList().observe(getViewLifecycleOwner(), audioModels -> {
//            songsList.clear();
//            songsList.addAll(audioModels);
//            if (songsList.size() == 0) {
//                // обработка если нет музыки
//            } else {
//                if (savedAfterSearchSongsList.size() != 0) {
//                    setRecyclerView(savedAfterSearchSongsList);
//                } else {
//                    setRecyclerView(songsList);
//                }
//            }
//        });

//        ArrayList<AudioModel> audioModels = mainListener.getOnAudioModels();
//        songsList = audioModels;
//
//        if(songsList.size() == 0){
//            // обработка если нет музыки
//        }else{
//            if(savedAfterSearchSongsList.size() != 0){
//                setRecyclerView(savedAfterSearchSongsList);
//            }else {
//                setRecyclerView(songsList);
//            }
//        }


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
        outState.putSerializable(LIST_KEY, (Serializable) songsList);
        outState.putSerializable(LIST_SAVED_KEY, (Serializable) songsList);
    }

    public void searchMusic() {
        YandexMetrica.reportEvent("Пользователь ищет музыку");
        String searchQuery = inputSearch.getText().toString().trim();
        if (searchQuery.equals("")){
            if(songsList.size() == 0){
                // обработка если нет музыки
            }else{
                savedAfterSearchSongsList = songsList;
                setRecyclerView(songsList);
            }
        } else {
            savedAfterSearchSongsList = new ArrayList<>();
            for (AudioModel song : songsList) {
                if (song.getTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        song.getAuthor().toLowerCase().contains(searchQuery.toLowerCase())) {
                    savedAfterSearchSongsList.add(song);
                }
            }

            setRecyclerView(savedAfterSearchSongsList);
        }
    }

    @Override
    public void onItemClick(int id) {
        YandexMetrica.reportEvent("Пользователь выбрал музыку для прослушивания");
        MyMediaPlayer.getInstance().reset();
        MyMediaPlayer.currentIndex = id;

        if(savedAfterSearchSongsList.size() != 0){
            mainListener.setOnAudioModel(savedAfterSearchSongsList);
            musicViewModel.setSongsList(savedAfterSearchSongsList);
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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                getContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_bottom_sheet,
                (ConstraintLayout) getActivity().findViewById(R.id.bottomSheetContainer)
        );

        bottomSheetView.findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                songsList.remove(id);
//                setRecyclerView(songsList);

                AudioService
                        .getInstance()
                        .getJSON()
                        .deleteMusic(mainListener.getOnEmail(), songsList.get(id).getId()).enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                Toast.makeText(getContext(),
                                        "Song is deleted",
                                        Toast.LENGTH_SHORT).show();
                                YandexMetrica.reportEvent("Пользователь удалил музыку из своего списка");

                                songsList.remove(id);
                            }

                            @Override
                            public void onFailure(Call<List<String>> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "Error",
                                        Toast.LENGTH_SHORT).show();

                                songsList.remove(songsList);
                            }
                        });

                setRecyclerView(songsList);
            }
        });

        bottomSheetView.findViewById(R.id.add_button).setVisibility(View.GONE);
        bottomSheetView.findViewById(R.id.edit_button).setVisibility(View.GONE);

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