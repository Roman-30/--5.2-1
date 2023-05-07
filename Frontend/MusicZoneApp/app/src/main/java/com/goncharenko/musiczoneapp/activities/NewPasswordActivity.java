package com.goncharenko.musiczoneapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.validator.InputValidator;

public class NewPasswordActivity extends AppCompatActivity {
    private EditText passwordInput;
    private EditText verifyPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        passwordInput = findViewById(R.id.new_password_input);
        verifyPasswordInput = findViewById(R.id.verify_password_input);
    }

    public void verifyPassword(View view) {
        if(InputValidator.checkEditText(this, InputValidator.isValidPassword(passwordInput), passwordInput) && InputValidator.checkEditText(this, InputValidator.isValidPassword(verifyPasswordInput), verifyPasswordInput)) {
            String password = passwordInput.getText().toString().trim();
            String verifyPassword = verifyPasswordInput.getText().toString().trim();
            if (verifyPassword.equals(password)) {
                goToEntryAccount();
            }else {
                verifyPasswordInput.setError("Пароль несоответствует набранному до этого");
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

        MainActivity mainActivity = (MainActivity) this.getParent();
        mainActivity.goAccount();
    }
}
