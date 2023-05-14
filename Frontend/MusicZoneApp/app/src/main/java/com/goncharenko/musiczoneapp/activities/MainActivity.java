package com.goncharenko.musiczoneapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseArray;
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

public class MainActivity extends AppCompatActivity implements MainListener{

    private FrameLayout frameLayout;

    //@State
    SparseArray<Fragment.SavedState> savedStateSparseArray = new SparseArray<>();

    private boolean isSignIn = false;
    private String email = "";
    private String password = "";

    private Fragment activeFragment;
    private String fragmentName = "Search";
    private FragmentManager ft = getSupportFragmentManager();

    private ImageButton homeButton;
    private ImageButton playerButton;
    private ImageButton accountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
        playerButton = findViewById(R.id.player_button);
        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPlayer();
            }
        });
        accountButton = findViewById(R.id.account_button);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAccount();
            }
        });

        if(!checkPermission()){
            requestPermission();
            return;
        }
    }

    public void goHome(){
        Fragment searchMusicFragment = getSupportFragmentManager().findFragmentByTag(SearchMusicFragment.TAG);
        if(searchMusicFragment != null){
            saveFragmentState(1, searchMusicFragment);
        } else {
            searchMusicFragment = new SearchMusicFragment();
        }
        setNewFragment(searchMusicFragment, SearchMusicFragment.TAG);
    }

    public void goPlayer(){
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
            Fragment accountFragment = getSupportFragmentManager().findFragmentByTag(AccountFragment.TAG);
            if(accountFragment != null){
                saveFragmentState(1, accountFragment);
            } else {
                accountFragment = new AccountFragment();
            }
            setNewFragment(accountFragment, AccountFragment.TAG);
        }else {

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
    public void setOnEmail(String email) {
        this.email = email;
    }

    @Override
    public void setOnPassword(String password) {
        this.password = password;
    }

    @Override
    public String getOnEmail() {
        return this.email;
    }

    @Override
    public String getOnPassword() {
        return this.password;
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
                activeFragment = new AccountFragment();
                tag = AccountFragment.TAG;
                break;
            case "Entry":
                activeFragment = new EntryFragment();
                tag = EntryFragment.TAG;
                break;
            case "Player":
                activeFragment = new PlayerFragment();
                tag = PlayerFragment.TAG;
                break;
            case "My music":
                activeFragment = new MyMusicFragment();
                tag = MyMusicFragment.TAG;
                break;
            case "Search":
                activeFragment = new SearchMusicFragment();
                tag = SearchMusicFragment.TAG;
                break;
            default:
                activeFragment = new SearchMusicFragment();
        }

        setNewFragment(activeFragment, tag);
    }
}