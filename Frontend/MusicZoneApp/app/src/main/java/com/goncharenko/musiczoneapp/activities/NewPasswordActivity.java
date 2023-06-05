package com.goncharenko.musiczoneapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.service.UserService;
import com.goncharenko.musiczoneapp.utill.validator.InputValidator;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPasswordActivity extends AppCompatActivity {
    private EditText passwordInput;
    private EditText verifyPasswordInput;

    private String email;

    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email = extras.getString("email");
            id = extras.getInt("id");
        }

        passwordInput = findViewById(R.id.new_password_input);
        verifyPasswordInput = findViewById(R.id.verify_password_input);
    }

    public void verifyPassword(View view) {
        if(InputValidator.checkEditText(this, InputValidator.isValidPassword(passwordInput), passwordInput) && InputValidator.checkEditText(this, InputValidator.isValidPassword(verifyPasswordInput), verifyPasswordInput)) {
            String password = passwordInput.getText().toString().trim();
            String verifyPassword = verifyPasswordInput.getText().toString().trim();
            if (verifyPassword.equals(password)) {
                UserService.getInstance()
                        .getJSON()
                        .changePassword(id, password)
                        .enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                                Toast.makeText(view.getContext(),
                                        "Update is successful",
                                        Toast.LENGTH_SHORT).show();
                                YandexMetrica.reportEvent("Пользователь изменил свой пароль");
                                goToEntryAccount();
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                                Toast.makeText(view.getContext(),
                                        "Error",
                                        Toast.LENGTH_SHORT).show();
                                goToEntryAccount();
                            }
                        });
                finish();
            }else {
                verifyPasswordInput.setError("The password does not match the one typed before");
                Toast.makeText(this, verifyPasswordInput.getError().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, SendingCodeActivity.class);
        startActivity(intent);
    }

    private void goToEntryAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragment", "Entry");
        intent.putExtra("isSignIn", false);
        startActivity(intent);

    }
}
