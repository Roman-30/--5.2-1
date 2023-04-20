package com.goncharenko.musiczoneapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.validator.InputValidator;

public class RegistrationAccountActivity extends AppCompatActivity {

    private EditText firstNameInput;
    private EditText secondNameInput;
    private EditText nickNameInput;
    private EditText emailInput;
    private EditText phoneNumberInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstNameInput = findViewById(R.id.first_name_input);
        secondNameInput = findViewById(R.id.second_name_input);
        nickNameInput = findViewById(R.id.nickname_input);
        emailInput = findViewById(R.id.email_input);
        phoneNumberInput = findViewById(R.id.phone_input);
        passwordInput = findViewById(R.id.password_input);
    }

    public void goBack(View view) {
        goToEntryAccount();
    }

    public void signUp(View view) {
        if(checkAllTextView()) {
            goToAccount();
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
        intent.putExtra("firstFragment", "new AccountFragment()");
        intent.putExtra("isSignIn", true);
        startActivity(intent);
    }

    private void goToEntryAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("firstFragment", "new EntryFragment()");
        intent.putExtra("isSignIn", false);
        startActivity(intent);
    }
}
