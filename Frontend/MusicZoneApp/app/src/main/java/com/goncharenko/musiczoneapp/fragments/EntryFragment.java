package com.goncharenko.musiczoneapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.MainListener;
import com.goncharenko.musiczoneapp.activities.RegistrationAccountActivity;
import com.goncharenko.musiczoneapp.activities.SendingCodeActivity;
import com.goncharenko.musiczoneapp.models.JwtRequest;
import com.goncharenko.musiczoneapp.models.JwtResponse;
import com.goncharenko.musiczoneapp.service.AudioService;
import com.goncharenko.musiczoneapp.service.UserService;
import com.goncharenko.musiczoneapp.utill.validator.InputValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryFragment extends Fragment {

    public static final String TAG = EntryFragment.class.getSimpleName();
    private EditText emailInput;
    private EditText passwordInput;

    public static JwtResponse jwt;
    private final String EMAIL_KEY = "email";
    private final String PASSWORD_KEY = "password";
    private String email = "";
    private String password = "";

    private Button signInButton;
    private Button recoverPasswordButton;
    private Button signUpButton;

    private MainListener mainListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            email = savedInstanceState.getString(EMAIL_KEY);
            password = savedInstanceState.getString(PASSWORD_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entry, container, false);

        emailInput = view.findViewById(R.id.email_input);
        passwordInput = view.findViewById(R.id.password_input);

        emailInput.setText(email);
        passwordInput.setText(password);

        signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> signIn());

        recoverPasswordButton = view.findViewById(R.id.recover_password);
        recoverPasswordButton.setOnClickListener(v -> recoverPassword());

        signUpButton = view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(v -> signUp());

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
        outState.putString(EMAIL_KEY, emailInput.toString().trim());
        outState.putString(PASSWORD_KEY, passwordInput.toString().trim());
    }

    public void signIn() {
        if (InputValidator.checkEditText(getActivity(), InputValidator.isValidEmail(emailInput), emailInput) &&
                InputValidator.checkEditText(getActivity(), InputValidator.isValidPassword(passwordInput), passwordInput)) {
            if (isCorrectEmailAndPassword()) {

                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                UserService.getInstance()
                        .getJSON()
                        .login(new JwtRequest(email, password))
                        .enqueue(new Callback<JwtResponse>() {
                            @Override
                            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                                if (response.body() != null) {
                                    jwt = response.body();
                                    System.out.println(jwt);
                                    AccountFragment accountFragment = new AccountFragment();
                                    mainListener.onSignedIn(true);
                                    mainListener.setOnEmail(email);
                                    mainListener.setOnPassword(password);
                                    setNewFragment(accountFragment);

                                    UserService.getInstance().setAccess(jwt.getAccessToken());
                                    AudioService.getInstance().setAccess(jwt.getAccessToken());
                                } else {
                                    onFailure(call, new Throwable());
                                }
                            }

                            @Override
                            public void onFailure(Call<JwtResponse> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "Error",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    public void recoverPassword() {
        Intent intent = new Intent(getActivity(), SendingCodeActivity.class);
        startActivity(intent);
    }

    public void signUp() {
        Intent intent = new Intent(getActivity(),  RegistrationAccountActivity.class);
        startActivity(intent);
    }

    private boolean isCorrectEmailAndPassword(){
        return true;
    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment).commit();
    }
}