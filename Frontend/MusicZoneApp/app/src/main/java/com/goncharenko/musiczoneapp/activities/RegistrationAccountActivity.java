package com.goncharenko.musiczoneapp.activities;

import static androidx.test.InstrumentationRegistry.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.UserModel;
import com.goncharenko.musiczoneapp.service.UserService;
import com.goncharenko.musiczoneapp.utill.validator.InputValidator;
import com.goncharenko.musiczoneapp.viewmodels.UserViewModel;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationAccountActivity extends AppCompatActivity {

    private EditText firstNameInput;
    private EditText secondNameInput;
    private EditText nickNameInput;
    private EditText emailInput;
    private EditText phoneNumberInput;
    private EditText passwordInput;

    private Button signUuButton;

    private UserViewModel userViewModel;

    private MainListener mainListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        YandexMetrica.reportEvent("Пользователь регистрируется в приложении");

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        firstNameInput = findViewById(R.id.first_name_input);
        secondNameInput = findViewById(R.id.second_name_input);
        nickNameInput = findViewById(R.id.nickname_input);
        emailInput = findViewById(R.id.email_input);
        phoneNumberInput = findViewById(R.id.phone_input);
        passwordInput = findViewById(R.id.password_input);

        signUuButton = findViewById(R.id.sign_up);
        signUuButton.setOnClickListener(v -> signUp());
    }

    public void goBack(View view) {
        YandexMetrica.reportEvent("Пользователь вышел из формы регистрации");
        goToEntryAccount();
    }

    public void signUp() {
        if(checkAllTextView()) {
            String firstName = this.firstNameInput.getText().toString();
            String secondName = this.secondNameInput.getText().toString();
            String nickname = this.nickNameInput.getText().toString();
            String email = this.emailInput.getText().toString();
            String number = this.phoneNumberInput.getText().toString();
            String password = this.passwordInput.getText().toString();

            UserModel dto = new UserModel(firstName, secondName, nickname, email, number, password);

            userViewModel.setEmail(dto.getEmail());
            userViewModel.setPassword(dto.getPassword());

            UserService.getInstance()
                    .getJSON()
                    .registration(dto)
                    .enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                            YandexMetrica.reportEvent("Пользователь успешно зарегистрировался");
                            goToEntryAccount();
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(),
                                    "Error",
                                    Toast.LENGTH_SHORT
                            ).show();

                            goToEntryAccount();
                        }
                    });

        }
    }

    private boolean checkAllTextView(){
        boolean bool = true;
        bool = InputValidator.checkEditText(this, InputValidator.isValidFirstName(firstNameInput), firstNameInput) &&
                InputValidator.checkEditText(this, InputValidator.isValidSecondName(secondNameInput), secondNameInput) &&
                InputValidator.checkEditText(this, InputValidator.isValidNickname(nickNameInput), nickNameInput) &&
                InputValidator.checkEditText(this, InputValidator.isValidEmail(emailInput), emailInput) &&
                InputValidator.checkEditText(this, InputValidator.isValidPhoneNumber(phoneNumberInput), phoneNumberInput) &&
                InputValidator.checkEditText(this, InputValidator.isValidPassword(passwordInput), passwordInput);
        return bool;
    }

    private void goToAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragment", "Account");
        intent.putExtra("isSignIn", true);
        intent.putExtra("email", emailInput.getText().toString().trim());
        intent.putExtra("password", passwordInput.getText().toString().trim());
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
