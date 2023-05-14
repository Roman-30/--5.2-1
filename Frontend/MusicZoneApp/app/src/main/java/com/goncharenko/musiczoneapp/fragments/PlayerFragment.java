package com.goncharenko.musiczoneapp.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.viewmodels.MusicViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerFragment extends Fragment {
    public static final String TAG = PlayerFragment.class.getSimpleName();
    private TextView titleTextView;
    private String title = "Title";
    private final String TITLE_KEY = "title";
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(TITLE_KEY);
            currentTime = savedInstanceState.getString(CURRENT_TIME_KEY);
            totalTime = savedInstanceState.getString(TOTAL_TIME_KEY);
            progressSeekBar = savedInstanceState.getInt(PROGRESS_KEY);
            isPlaying = savedInstanceState.getBoolean(PLAYING_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

        titleTextView = view.findViewById(R.id.song_title);
        titleTextView.setText(title);

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

        musicViewModel.getSongsList().observe(getViewLifecycleOwner(), audioModels -> {
            songsList = audioModels;
            setResourcesWithMusic();
        });

//        Bundle extras = getActivity().getIntent().getExtras();
//        if(extras != null){
//            songsList = (ArrayList<AudioModel>) getActivity().getIntent().getSerializableExtra("LIST");
//        }
//        if(songsList == null || songsList.isEmpty()){
//            return view;
//        }


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTextView.setText(convertToMMSS(mediaPlayer.getCurrentPosition() + ""));

                    isPlaying = mediaPlayer.isPlaying();

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_KEY, titleTextView.toString().trim());
        outState.putString(CURRENT_TIME_KEY, currentTimeTextView.toString().trim());
        outState.putString(TOTAL_TIME_KEY, totalTimeTextView.toString().trim());
        outState.putInt(PROGRESS_KEY, progressSeekBar);
        outState.putBoolean(PLAYING_KEY, isPlaying);
    }

    void setResourcesWithMusic(){
        currentSong = songsList.get(MyMediaPlayer.currentIndex);
        titleTextView.setText(currentSong.getTitle());

        totalTimeTextView.setText(convertToMMSS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v-> pausePlay());
        nextButton.setOnClickListener(v-> playNextSong());
        previousButton.setOnClickListener(v-> playPreviousSong());

        playMusic();
    }


    private void playMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.seekTo(progressSeekBar);
            if(isPlaying) {
                mediaPlayer.start();
            }
            seekBar.setProgress(progressSeekBar);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playNextSong(){
        progressSeekBar = 0;
        isPlaying = true;
        if(MyMediaPlayer.currentIndex== songsList.size()-1)
            return;
        MyMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void playPreviousSong(){
        progressSeekBar = 0;
        isPlaying = true;
        if(MyMediaPlayer.currentIndex== 0)
            return;
        MyMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }


    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}