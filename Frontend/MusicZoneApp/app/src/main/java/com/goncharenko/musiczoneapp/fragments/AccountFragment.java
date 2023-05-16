package com.goncharenko.musiczoneapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goncharenko.musiczoneapp.activities.EditAccountActivity;
import com.goncharenko.musiczoneapp.activities.MainListener;
import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.UserModel;
import com.goncharenko.musiczoneapp.service.UserService;
import com.goncharenko.musiczoneapp.viewmodels.UserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    public static final String TAG = AccountFragment.class.getSimpleName();
    private Button editAccountButton;
    private Button signOutButton;
    private Button checkMusicButton;
    private Button addMusicButton;

    private TextView name;
    private TextView nickname;
    private TextView email;
    private TextView phoneNumber;

    private MainListener mainListener;

    private String emailFrom = "";
    private String passwordFrom = "";
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        name = view.findViewById(R.id.account_name);
        nickname = view.findViewById(R.id.account_nickname);
        email = view.findViewById(R.id.account_email);
        phoneNumber = view.findViewById(R.id.account_phone);

        Bundle extras = getActivity().getIntent().getExtras();

        emailFrom = mainListener.getOnEmail();
        passwordFrom = mainListener.getOnPassword();

        UserService.getInstance()
                .getJSON()
                .getUserFromId(0)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                        if(response.body() != null) {
                            UserModel user = response.body();
                            if (user != null) {
                                name.append(user.getName() + " " + user.getSurname());
                                nickname.append(user.getNickname());
                                email.append(user.getEmail());
                                phoneNumber.append(user.getPhoneNumber());
                            }
                        } else {
                            onFailure(call, new Throwable());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                        if(emailFrom.equals("admin@ad.min") && passwordFrom.equals("admin111")){
                            name.setText("Admin");
                            nickname.setText("");
                            email.setText("");
                            phoneNumber.setText("");
                        } else {
                            Toast.makeText(view.getContext(),
                                    "Ошибка при входе в аккаунт",
                                    Toast.LENGTH_SHORT).show();
                            singOut();
                        }
                    }
                });

        editAccountButton = view.findViewById(R.id.edit);
        editAccountButton.setOnClickListener(v -> editAccount());

        signOutButton = view.findViewById(R.id.sign_out);
        signOutButton.setOnClickListener(v -> singOut());

        checkMusicButton = view.findViewById(R.id.check_music);
        checkMusicButton.setOnClickListener(v -> checkMusic());

        addMusicButton = view.findViewById(R.id.add_music);
        addMusicButton.setOnClickListener(v -> addMusic());

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

    public void editAccount() {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        startActivity(intent);
    }

    public void singOut() {
        Fragment entryFragment = getActivity().getSupportFragmentManager().findFragmentByTag(EntryFragment.TAG);
        if(entryFragment != null){
            //saveFragmentState(1, myMusicFragment);
        } else {
            entryFragment = new EntryFragment();
        }
        setNewFragment(entryFragment, EntryFragment.TAG);
        mainListener.onSignedIn(false);
    }

    public void checkMusic() {
        Fragment myMusicFragment = getActivity().getSupportFragmentManager().findFragmentByTag(MyMusicFragment.TAG);
        if(myMusicFragment != null){
            //saveFragmentState(1, myMusicFragment);
        } else {
            myMusicFragment = new MyMusicFragment();
        }
        setNewFragment(myMusicFragment, MyMusicFragment.TAG);
    }

    public void addMusic() {
    }

    private void setNewFragment(Fragment fragment, String tag){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }
}