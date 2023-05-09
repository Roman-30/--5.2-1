package com.goncharenko.musiczoneapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

    private boolean isSignIn = false;

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
            boolean isSignIn = extras.getBoolean("isSignIn");
            this.isSignIn = isSignIn;

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
        SearchMusicFragment searchMusicFragment = new SearchMusicFragment();
        setNewFragment(searchMusicFragment);
    }

    public void goPlayer(){
        PlayerFragment playerFragment = new PlayerFragment();
        setNewFragment(playerFragment);
    }

    public void goAccount(){
        if(isSignIn) {
            AccountFragment accountFragment = new AccountFragment();
            setNewFragment(accountFragment);
        }else {
            EntryFragment entryFragment = new EntryFragment();
            setNewFragment(entryFragment);
        }
    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
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
        switch (fragment) {
            case "Account":
                activeFragment = new AccountFragment();
                break;
            case "Entry":
                activeFragment = new EntryFragment();
                break;
            case "Player":
                activeFragment = new PlayerFragment();
                break;
            case "My music":
                activeFragment = new MyMusicFragment();
                break;
            case "Search":
                activeFragment = new SearchMusicFragment();
                break;
            default:
                activeFragment = new SearchMusicFragment();
        }

        setNewFragment(activeFragment);
    }
}