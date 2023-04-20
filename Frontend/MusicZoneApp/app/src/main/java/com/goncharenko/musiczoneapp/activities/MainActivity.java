package com.goncharenko.musiczoneapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

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
}