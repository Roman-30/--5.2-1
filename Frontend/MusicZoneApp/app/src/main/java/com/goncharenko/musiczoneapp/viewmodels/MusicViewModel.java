package com.goncharenko.musiczoneapp.viewmodels;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.service.AudioService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<ArrayList<AudioModel>> songsList = new MutableLiveData<>();

    private MutableLiveData<ArrayList<AudioModel>> savedSongsList = new MutableLiveData<>();
    private MutableLiveData<InputStream> songFile = new MutableLiveData<>();

    private MutableLiveData<Boolean> isReady = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Executor executorFile = Executors.newSingleThreadExecutor();

    public void loadSongsList() {
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

    public void loadSavedSongsList(String email) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AudioService.getInstance().getJSON().getSavedMusic(email).enqueue(new Callback<List<AudioModel>>() {
                @Override
                public void onResponse(Call<List<AudioModel>> call, Response<List<AudioModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            savedSongsList.postValue((ArrayList<AudioModel>) response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AudioModel>> call, Throwable t) {

                }
            });
        });
    }

    public void doLoadFileAsync(String link){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AudioService.getInstance().getJSON().getMusicFile(link).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            InputStream stream;
                            stream = response.body().byteStream();
//                            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//                            int nRead;
//                            byte[] data = new byte[7496685];
//
//                            while (true) {
//                                try {
//                                    if (!((nRead = stream.read(data, 0, data.length)) != -1)) break;
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                buffer.write(data, 0, nRead);
//                            }

                            songFile.postValue(stream);

//                            try (FileOutputStream fos = new FileOutputStream(
//                                    new File("music.mp3"))) {
//                                fos.write(buffer.toByteArray());
//                                fos.close();
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        });
    }

    public void loadSongs() {
        loadSongsList();
    }

    public void loadSongFile(String link){
        doLoadFileAsync(link);
    }

    public LiveData<ArrayList<AudioModel>> getSongsList() {
        return songsList;
    }

    public void setSongsList(ArrayList<AudioModel> songsList) {
        this.songsList.setValue(songsList);
    }

    public LiveData<InputStream> getSongFile() {
        return songFile;
    }

    public void setSongFile(InputStream songFile) {
        this.songFile.setValue(songFile);
    }

    public void setIsReady(Boolean isReady) {
        this.isReady.setValue(isReady);
    }

    public LiveData<Boolean> getIsReady() {
        return isReady;
    }

    public LiveData<ArrayList<AudioModel>> getSavedSongsList() {
        return savedSongsList;
    }

    public void setSavedSongsList(ArrayList<AudioModel> savedSongsList) {
        this.savedSongsList.setValue(savedSongsList);
    }
}
