package com.goncharenko.musiczoneapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.NewPasswordActivity;
import com.goncharenko.musiczoneapp.validator.InputValidator;

public class SendingCodeActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText codeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_code);

        emailInput = findViewById(R.id.email_input);
        codeInput = findViewById(R.id.code_input);
    }

    public void sendCode(View view) {
        InputValidator.checkEditText(this, InputValidator.isValidEmail(emailInput), emailInput);
    }

    public void verifyCode(View view) {
        if(InputValidator.checkEditText(this, InputValidator.isValidCode(codeInput), codeInput)) {
            Intent intent = new Intent(this, NewPasswordActivity.class);
            startActivity(intent);
        }
    }

    public void cancel(View view) {
        goToEntryAccount();
    }

    private void goToEntryAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("firstFragment", "new EntryFragment()");
        intent.putExtra("isSignIn", false);
        startActivity(intent);
    }


}
