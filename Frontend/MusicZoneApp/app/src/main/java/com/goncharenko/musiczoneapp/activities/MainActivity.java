package com.goncharenko.musiczoneapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.fragments.AccountFragment;
import com.goncharenko.musiczoneapp.fragments.EntryFragment;
import com.goncharenko.musiczoneapp.fragments.MyMusicFragment;
import com.goncharenko.musiczoneapp.fragments.PlayerFragment;
import com.goncharenko.musiczoneapp.fragments.SearchMusicFragment;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainListener{

    private FrameLayout frameLayout;

    private MenuItem itemHome;
    private MenuItem itemPlayer;
    private MenuItem itemAccount;

    //@State
    private SparseArray<Fragment.SavedState> savedStateSparseArray = new SparseArray<>();

    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    goHome();
                    return true;
                case R.id.navigation_player:
                    goPlayer();
                    return true;
                case R.id.navigation_account:
                    goAccount();
                    return true;
            }
            return false;
        }
    };

    private boolean isSignIn = false;
    private String email = "";
    private String password = "";

    private ArrayList<AudioModel> audioModels = new ArrayList<>();
    private boolean isAdmin = false;

    private Fragment activeFragment;
    private String fragmentName = "Search";
    private FragmentManager ft = getSupportFragmentManager();

    private ImageButton homeButton;
    private ImageButton playerButton;
    private ImageButton accountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                boolean isFirstStart = prefs.getBoolean("isFirstStart", true);

                if (isFirstStart) {
                    Intent i = new Intent(MainActivity.this, CustomIntro.class);
                    startActivity(i);
                    YandexMetrica.reportEvent("Пользователь посмотрел инструкцию к приложению");

                    SharedPreferences.Editor e = prefs.edit();
                    e.putBoolean("isFirstStart", false);
                    e.apply();
                }
            }
        });

        YandexMetrica.reportEvent("Пользователь перешел на главную активность");

        // Start the thread
        t.start();
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frame_layout);
        ft.beginTransaction().add(R.id.frame_layout, new SearchMusicFragment());
        ft.beginTransaction().add(R.id.frame_layout, new PlayerFragment());
        ft.beginTransaction().add(R.id.frame_layout, new EntryFragment());
        ft.beginTransaction().add(R.id.frame_layout, new AccountFragment());
        ft.beginTransaction().add(R.id.frame_layout, new MyMusicFragment());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.isSignIn = extras.getBoolean("isSignIn");
            this.email = extras.getString("email");
            this.password = extras.getString("password");

            fragmentName = (String) extras.get("fragment");
        }

        showFragments(fragmentName);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(!checkPermission()){
            requestPermission();
            return;
        }
    }

    public void goHome(){
        YandexMetrica.reportEvent("Пользователь зашел на фрагмент поиска музыки");
        Fragment searchMusicFragment = getSupportFragmentManager().findFragmentByTag(SearchMusicFragment.TAG);
        if(searchMusicFragment != null){
            saveFragmentState(1, searchMusicFragment);
        } else {
            searchMusicFragment = new SearchMusicFragment();
        }
        setNewFragment(searchMusicFragment, SearchMusicFragment.TAG);
    }

    public void goPlayer(){
        YandexMetrica.reportEvent("Пользователь зашел на фрагмент прослушивания музыки");
        Fragment playerFragment = getSupportFragmentManager().findFragmentByTag(PlayerFragment.TAG);
        if(playerFragment != null){
            saveFragmentState(1, playerFragment);
        } else {
            playerFragment = new PlayerFragment();
        }
        setNewFragment(playerFragment, PlayerFragment.TAG);
    }

    public void goAccount(){
        if(isSignIn) {
            YandexMetrica.reportEvent("Пользователь зашел на фрагмент входа в аккаунт");
            Fragment accountFragment = getSupportFragmentManager().findFragmentByTag(AccountFragment.TAG);
            if(accountFragment != null){
                saveFragmentState(1, accountFragment);
            } else {
                accountFragment = new AccountFragment();
            }
            setNewFragment(accountFragment, AccountFragment.TAG);
        }else {
            YandexMetrica.reportEvent("Пользователь зашел на фрагмент со своим аккаунтом");
            Fragment entryFragment = getSupportFragmentManager().findFragmentByTag(EntryFragment.TAG);
            if (entryFragment != null) {
                saveFragmentState(1, entryFragment);
            } else {
                entryFragment = new EntryFragment();
            }

            setNewFragment(entryFragment, EntryFragment.TAG);
        }
    }

    private void saveFragmentState(int index, Fragment fragment) {
        Fragment.SavedState savedState = getSupportFragmentManager().saveFragmentInstanceState(fragment);
        savedStateSparseArray.put(index, savedState);
    }

    private void restoreFragmentState(int index, Fragment fragment) {
        Fragment.SavedState savedState = savedStateSparseArray.get(index);
        fragment.setInitialSavedState(savedState);
    }

    private void setNewFragment(Fragment fragment, String tag){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment, tag);
        ft.addToBackStack(null);
        activeFragment = fragment;
//        ft.hide(activeFragment);
//        ft.show(fragment);
        ft.commit();
    }

    @Override
    public void onSignedIn(boolean isSignedIn) {
        this.isSignIn = isSignedIn;
    }

    @Override
    public boolean getOnSignedIn() {
        return isSignIn;
    }

    @Override
    public void setOnEmail(String email) {
        this.email = email;
    }

    @Override
    public void setOnPassword(String password) {
        this.password = password;
    }

    @Override
    public void setOnAudioModel(AudioModel audioModel) {
        audioModels.add(audioModel);
    }

    @Override
    public void setOnAudioModel(List<AudioModel> audioModel) {
        audioModels.clear();
        audioModels.addAll(audioModel);
    }

    @Override
    public String getOnEmail() {
        return this.email;
    }

    @Override
    public String getOnPassword() {
        return this.password;
    }

    @Override
    public ArrayList<AudioModel> getOnAudioModels() {
        return audioModels;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTTINGS",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
    }

    public void showFragments(String fragment) {
        String tag = "";
        switch (fragment) {
            case "Account":
                YandexMetrica.reportEvent("Пользователь зашел на фрагмент со своим аккаунтом");
                activeFragment = new AccountFragment();
                tag = AccountFragment.TAG;
                break;
            case "Entry":
                YandexMetrica.reportEvent("Пользователь зашел на фрагмент со входом в аккаунт");
                activeFragment = new EntryFragment();
                tag = EntryFragment.TAG;
                break;
            case "Player":
                YandexMetrica.reportEvent("Пользователь зашел на фрагмент прослушивания музыки");
                activeFragment = new PlayerFragment();
                tag = PlayerFragment.TAG;
                break;
            case "My music":
                YandexMetrica.reportEvent("Пользователь зашел на фрагмент своей музыки");
                activeFragment = new MyMusicFragment();
                tag = MyMusicFragment.TAG;
                break;
            case "Search":
                YandexMetrica.reportEvent("Пользователь зашел на фрагмент поиска музыки");
                activeFragment = new SearchMusicFragment();
                tag = SearchMusicFragment.TAG;
                break;
            default:
                activeFragment = new SearchMusicFragment();
        }

        setNewFragment(activeFragment, tag);
    }
}