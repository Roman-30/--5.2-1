package com.goncharenko.musiczoneapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.UserModel;
import com.goncharenko.musiczoneapp.service.UserService;
import com.goncharenko.musiczoneapp.utill.codegenerator.RandomCodeGenerator;
import com.goncharenko.musiczoneapp.utill.validator.InputValidator;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendingCodeActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText codeInput;

    private UserModel user;

    private String code = RandomCodeGenerator.generateCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_code);
        YandexMetrica.reportEvent("Пользователь зашел активность для изменения пароля");

        emailInput = findViewById(R.id.email_input);
        codeInput = findViewById(R.id.code_input);
    }

    public void sendCode(View view) {
        if(InputValidator.checkEditText(this, InputValidator.isValidEmail(emailInput), emailInput)){
            String emailFrom = emailInput.getText().toString().trim();
            UserService.getInstance()
                    .getJSON()
                    .getUserByEmail(emailFrom)
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                            if(response.body() != null) {
                                user = response.body();
                                if (user == null) {
                                    Toast.makeText(view.getContext(),
                                            "There is no user with such mail",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    sendEmail(user.getEmail(), code, view);

                                }
                            } else {
                                onFailure(call, new Throwable());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                            Toast.makeText(view.getContext(),
                                    "Error",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    public void verifyCode(View view) {
        if(InputValidator.checkEditText(this, InputValidator.isValidCode(codeInput), codeInput)) {
            if(codeInput.getText().toString().trim().equals(code)) {
                Intent intent = new Intent(this, NewPasswordActivity.class);
                intent.putExtra("email", user.getEmail());
                intent.putExtra("id", user.getId());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(view.getContext(),
                        "Invalid code",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cancel(View view) {
        YandexMetrica.reportEvent("Пользователь вышел из формы изменения пароля");
        goToEntryAccount();
    }

    private void goToEntryAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragment", "Entry");
        intent.putExtra("isSignIn", false);
        startActivity(intent);
    }

    private void sendEmail(String email, String code, View view){
        UserService.getInstance().getJSON().sendCode(email, code).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Toast.makeText(view.getContext(),
                        "Mail is send",
                        Toast.LENGTH_SHORT).show();
                YandexMetrica.reportEvent("Пользователь отправил код на почту");
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(view.getContext(),
                        "Error",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


}
