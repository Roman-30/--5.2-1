package com.goncharenko.musiczoneapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.fragments.AccountFragment;
import com.goncharenko.musiczoneapp.fragments.EntryFragment;
import com.goncharenko.musiczoneapp.fragments.PlayerFragment;
import com.goncharenko.musiczoneapp.fragments.SearchMusicFragment;

public class MainActivity extends AppCompatActivity implements MainListener{

    private FrameLayout frameLayout;

    private boolean isSignIn = false;

    private Fragment firstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frame_layout);
        firstFragment = new SearchMusicFragment();
        setNewFragment(firstFragment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean isSignIn = extras.getBoolean("isSignIn");
            this.isSignIn = isSignIn;

//            Fragment fragment = (Fragment) extras.get("firstFragment");
//            this.firstFragment = fragment;
        }

        if(!checkPermission()){
            requestPermission();
            return;
        }
    }

    public void goHome(View view){
        SearchMusicFragment searchMusicFragment = new SearchMusicFragment();
        setNewFragment(searchMusicFragment);
    }

    public void goPlayer(View view){
        PlayerFragment playerFragment = new PlayerFragment();
        setNewFragment(playerFragment);
    }

    public void goAccount(View view){
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
        ft.replace(R.id.frame_layout, fragment).commit();
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
}