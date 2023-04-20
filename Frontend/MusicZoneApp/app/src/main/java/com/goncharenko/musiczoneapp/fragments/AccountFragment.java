package com.goncharenko.musiczoneapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goncharenko.musiczoneapp.activities.EditAccountActivity;
import com.goncharenko.musiczoneapp.activities.MainListener;
import com.goncharenko.musiczoneapp.R;

public class AccountFragment extends Fragment {

    private Button editAccountButton;
    private Button signOutButton;
    private Button checkMusicButton;
    private Button addMusicButton;

    private MainListener mainListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        editAccountButton = view.findViewById(R.id.edit);
        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAccount(v);
            }
        });

        signOutButton = view.findViewById(R.id.sign_out);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singOut(v);
            }
        });

        checkMusicButton = view.findViewById(R.id.check_music);
        checkMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkMusic(v);
            }
        });

        addMusicButton = view.findViewById(R.id.add_music);
        addMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMusic(v);
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

    public void editAccount(View view) {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        startActivity(intent);
    }

    public void singOut(View view) {
        EntryFragment entryFragment = new EntryFragment();
        mainListener.onSignedIn(false);
        setNewFragment(entryFragment);
    }

    public void checkMusic(View view) {
        MyMusicFragment myMusicFragment = new MyMusicFragment();
        setNewFragment(myMusicFragment);
    }

    public void addMusic(View view) {
    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment).commit();
    }
}