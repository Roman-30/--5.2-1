package com.goncharenko.musiczoneapp.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.MainListener;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.service.AudioService;
import com.goncharenko.musiczoneapp.viewmodels.MusicViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerFragment extends Fragment {
    public static final String TAG = PlayerFragment.class.getSimpleName();
    private TextView titleTextView;
    private String title = "Title";
    private final String TITLE_KEY = "title";

    private TextView authorTextView;
    private String author = "Author";
    private final String AUTHOR_KEY = "author";
    private TextView currentTimeTextView;
    private String currentTime = "00:00";
    private final String CURRENT_TIME_KEY = "curr";
    private TextView totalTimeTextView;
    private String totalTime = "00:00";
    private final String TOTAL_TIME_KEY = "total";
    private SeekBar seekBar;
    private int progressSeekBar = 0;
    private final String PROGRESS_KEY = "progress";
    private ImageView pausePlay;
    private ImageView nextButton;
    private ImageView previousButton;
    private ArrayList<AudioModel> songsList = new ArrayList<>();
    private AudioModel currentSong;
    private MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();

    private boolean isPlaying = true;
    private final String PLAYING_KEY = "play";

    private MusicViewModel musicViewModel;

    private MainListener mainListener;

    private File directory;

    private File fileSong;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        directory = new File(getContext().getFilesDir(), "MusicZoneTemp");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        fileSong = new File(directory + "/music_zone_temp_audio.mp3");

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(TITLE_KEY);
            author = savedInstanceState.getString(AUTHOR_KEY);
            currentTime = savedInstanceState.getString(CURRENT_TIME_KEY);
            totalTime = savedInstanceState.getString(TOTAL_TIME_KEY);
            progressSeekBar = savedInstanceState.getInt(PROGRESS_KEY);
            isPlaying = savedInstanceState.getBoolean(PLAYING_KEY);
            songsList.clear();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

        titleTextView = view.findViewById(R.id.song_title);
        titleTextView.setText(title);

        authorTextView = view.findViewById(R.id.song_author);
        authorTextView.setText(author);

        currentTimeTextView = view.findViewById(R.id.current_time);
        currentTimeTextView.setText(currentTime);

        totalTimeTextView = view.findViewById(R.id.total_time);
        totalTimeTextView.setText(totalTime);

        seekBar = view.findViewById(R.id.seek_bar);
        seekBar.setProgress(progressSeekBar);

        pausePlay = view.findViewById(R.id.pause_play);
        nextButton = view.findViewById(R.id.next);
        previousButton = view.findViewById(R.id.previous);

        titleTextView.setSelected(true);
        songsList = mainListener.getOnAudioModels();

//        musicViewModel.getSongsList().observe(getViewLifecycleOwner(), audioModels -> {
//            songsList = audioModels;
//            setResourcesWithMusic();
//        });

//        Bundle extras = getActivity().getIntent().getExtras();
//        if(extras != null){
//            songsList = (ArrayList<AudioModel>) getActivity().getIntent().getSerializableExtra("LIST");
//        }
        if(songsList == null || songsList.isEmpty() || songsList.size() == 0){
            return view;
        }

        setResourcesWithMusic();
        totalTimeTextView.setText(convertToMMSS(mediaPlayer.getDuration() + ""));


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTextView.setText(convertToMMSS(mediaPlayer.getCurrentPosition() + ""));

                    //isPlaying = mediaPlayer.isPlaying();

                    if(mediaPlayer.isPlaying()){
                        progressSeekBar = mediaPlayer.getCurrentPosition();
                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    }else{
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }

                }
                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                    progressSeekBar = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        outState.putString(TITLE_KEY, titleTextView.getText().toString().trim());
        outState.putString(CURRENT_TIME_KEY, currentTimeTextView.getText().toString().trim());
        outState.putString(TOTAL_TIME_KEY, totalTimeTextView.getText().toString().trim());
        outState.putInt(PROGRESS_KEY, progressSeekBar);
        outState.putBoolean(PLAYING_KEY, isPlaying);
        outState.putString(AUTHOR_KEY, authorTextView.getText().toString().trim());
    }

    void setResourcesWithMusic(){
        currentSong = songsList.get(MyMediaPlayer.currentIndex);
        titleTextView.setText(currentSong.getTitle());
        authorTextView.setText(currentSong.getAuthor());

        //totalTimeTextView.setText(convertToMMSS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v-> pausePlay());
        nextButton.setOnClickListener(v-> playNextSong());
        previousButton.setOnClickListener(v-> playPreviousSong());

        playMusic();
    }


    private void playMusic(){
        AudioService.getInstance().getJSON().getMusicFile(currentSong.getPath()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        InputStream stream;
                        stream = response.body().byteStream();
                        loadSong(stream);

                        mediaPlayer.reset();
                        //loadSong(bytes);
                        try {

                            mediaPlayer.setDataSource(fileSong.getPath());
                            mediaPlayer.prepare();
                            mediaPlayer.seekTo(progressSeekBar);

                            if(isPlaying) {
                                mediaPlayer.start();
                            }
                            seekBar.setProgress(progressSeekBar);
                            seekBar.setMax(mediaPlayer.getDuration());
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    playNextSong();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        totalTimeTextView.setText(convertToMMSS(mediaPlayer.getDuration() + ""));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    private void playNextSong(){
        progressSeekBar = 0;
        isPlaying = true;
        if(MyMediaPlayer.currentIndex == songsList.size() - 1) {
            MyMediaPlayer.currentIndex = 0;
        } else {
            MyMediaPlayer.currentIndex += 1;
        }
        mediaPlayer.reset();
        totalTimeTextView.setText(convertToMMSS("0"));
        setResourcesWithMusic();

    }

    private void playPreviousSong(){
        progressSeekBar = 0;
        isPlaying = true;
        if(MyMediaPlayer.currentIndex == 0) {
            MyMediaPlayer.currentIndex = songsList.size() - 1;
        } else {
            MyMediaPlayer.currentIndex -= 1;
        }
        mediaPlayer.reset();
        totalTimeTextView.setText(convertToMMSS("0"));
        setResourcesWithMusic();
    }

    private void pausePlay(){
        totalTimeTextView.setText(convertToMMSS(mediaPlayer.getDuration() + ""));
        if(mediaPlayer.isPlaying()) {
            isPlaying = true;
            mediaPlayer.pause();
        } else {
            isPlaying = false;
            mediaPlayer.start();
        }
    }


    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    private void loadSong(InputStream audioBytes){
        try {
//            File directory = new File(getContext().getFilesDir(), "MusicZoneTemp");
//
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }

//            File tempFile = new File(directory + "/music_zone_temp_audio.mp3");

            ByteArrayOutputStream bis = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            int readNum;
            while ((readNum = audioBytes.read(b, 0, b.length)) != -1){
                bis.write(b, 0, readNum);
            }
            audioBytes.close();

            try(FileOutputStream fos = new FileOutputStream(fileSong)) {
                fos.write(bis.toByteArray());
            }
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.reset();
//            mediaPlayer.setDataSource(tempFile.getPath());
//            mediaPlayer.prepare();
//
//            mediaPlayer.start();
//
//            mediaPlayer.seekTo(progressSeekBar);
//            if(isPlaying) {
//                mediaPlayer.start();
//            }
//            seekBar.setProgress(progressSeekBar);
//            seekBar.setMax(mediaPlayer.getDuration());

//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    mp.release();
//                    tempFile.delete();
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}