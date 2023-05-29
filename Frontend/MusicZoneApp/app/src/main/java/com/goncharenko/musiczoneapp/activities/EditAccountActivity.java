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
import com.goncharenko.musiczoneapp.utill.validator.InputValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountActivity extends AppCompatActivity {

    private EditText firstNameInput;
    private String name = "";
    private EditText secondNameInput;
    private String surname = "";
    private EditText nickNameInput;
    private String nickname = "";
    private EditText emailInput;
    private String email = "";
    private EditText phoneNumberInput;
    private String phoneNumber = "";
    private String password = "";
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name = extras.getString("name");
            surname = extras.getString("surname");
            nickname = extras.getString("nickname");
            email = extras.getString("email");
            phoneNumber = extras.getString("phone");
            id = extras.getInt("id");
        }

        firstNameInput = findViewById(R.id.first_name_input);
        firstNameInput.setText(name);

        secondNameInput = findViewById(R.id.second_name_input);
        secondNameInput.setText(surname);

        nickNameInput = findViewById(R.id.nickname_input);
        nickNameInput.setText(nickname);

        emailInput = findViewById(R.id.email_input);
        emailInput.setText(email);

        phoneNumberInput = findViewById(R.id.phone_input);
        phoneNumberInput.setText(phoneNumber);
    }

    public void goBack(View view) {
        goToAccount();
    }

    public void save(View view) {

        String firstName = this.firstNameInput.getText().toString();
        String secondName = this.secondNameInput.getText().toString();
        String nickname = this.nickNameInput.getText().toString();
        String email = this.emailInput.getText().toString();
        String number = this.phoneNumberInput.getText().toString();
        String password = "";

        UserModel dto = new UserModel(id, firstName, secondName, nickname, email, number, password);

        if(checkAllTextView()) {
            UserService.getInstance()
                    .getJSON()
                    .userUpdate(dto)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            Toast.makeText(view.getContext(),
                                    "Аккаунт успешно изменен",
                                    Toast.LENGTH_SHORT).show();
                            goToAccount();
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Toast.makeText(view.getContext(),
                                    "Ошибка",
                                    Toast.LENGTH_SHORT).show();
                            goToAccount();
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
                InputValidator.checkEditText(this, InputValidator.isValidPhoneNumber(phoneNumberInput), phoneNumberInput);
        return bool;
    }

    private void goToAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragment", "Account");
        intent.putExtra("isSignIn", true);
        intent.putExtra("email", emailInput.getText().toString().trim());
        startActivity(intent);
    }
}
