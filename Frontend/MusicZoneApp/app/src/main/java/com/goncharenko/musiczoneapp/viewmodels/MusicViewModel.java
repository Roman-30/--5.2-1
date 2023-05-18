package com.goncharenko.musiczoneapp.viewmodels;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.service.AudioService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<ArrayList<AudioModel>> songsList = new MutableLiveData<>();

    public LiveData<ArrayList<AudioModel>> getSongsList() {
        return songsList;
    }

    public void setSongsList(ArrayList<AudioModel> songsList) {
        this.songsList.setValue(songsList);
    }

    private Executor executor = Executors.newSingleThreadExecutor();

    public void doSomethingAsync() {
        executor.execute(() -> {
            AudioService.getInstance().getJSON().getAllMusic().enqueue(new Callback<List<AudioModel>>() {
                @Override
                public void onResponse(Call<List<AudioModel>> call, Response<List<AudioModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            songsList.postValue((ArrayList<AudioModel>) response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AudioModel>> call, Throwable t) {

                }
            });
        });
    }

    public void loadSongs() {
        doSomethingAsync();
    }
}
