package com.goncharenko.musiczoneapp.utill.validator;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputValidator {
    public static boolean isValidFirstName(EditText firstNameInput) {
        String firstName = firstNameInput.getText().toString().trim();
        if(firstName.isEmpty()) {
            firstNameInput.setError("Enter a name");
            return false;
        } else if (firstName.length() > 60){
            firstNameInput.setError("The name is too long");
            return false;
        }else if(!firstName.matches("[a-zA-Z]+")) {
            firstNameInput.setError("The name can contain only letters");
            return false;
        }
        return true;
    }

    public static boolean isValidSecondName(EditText secondNameInput) {
        String secondName = secondNameInput.getText().toString().trim();
        if(secondName.isEmpty()) {
            secondNameInput.setError("Enter your surname name");
            return false;
        } else if (secondName.length() > 60){
            secondNameInput.setError("The surname is too long");
            return false;
        } else if(!secondName.matches("[a-zA-Z]+")) {
            secondNameInput.setError("The surname can contain only letters");
            return false;
        }
        return true;
    }

    public static boolean isValidNickname(EditText nickNameInput) {
        String nickName = nickNameInput.getText().toString().trim();
        if(nickName.isEmpty()) {
            nickNameInput.setError("Enter your nickname");
            return false;
        } else if (nickName.length() > 60){
            nickNameInput.setError("The nickname is too long");
            return false;
        } else if(!nickName.matches("[a-zA-Z0-9]+")) {
            nickNameInput.setError("A nickname can contain only letters and numbers");
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(EditText emailInput) {
        String email = emailInput.getText().toString().trim();
        if(email.isEmpty()) {
            emailInput.setError("Enter email");
            return false;
        } else if (email.length() > 255){
            emailInput.setError("The email is too long");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter the correct email");
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(EditText phoneNumberInput) {
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        if(phoneNumber.isEmpty()) {
            phoneNumberInput.setError("Enter your phone number");
            return false;
        } else if(!phoneNumber.matches("\\d{11}")) {
            phoneNumberInput.setError("Enter the correct phone number (11 digits)");
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(EditText passwordInput) {
        String password = passwordInput.getText().toString().trim();
        if(password.isEmpty()) {
            passwordInput.setError("Enter the password");
            return false;
        } else if(password.length() < 6) {
            passwordInput.setError("The password must contain at least 6 characters");
            return false;
        }
        return true;
    }

    public static boolean isValidCode(EditText passwordInput) {
        String password = passwordInput.getText().toString().trim();
        if(password.isEmpty()) {
            passwordInput.setError("Enter the code");
            return false;
        } else if(password.length() < 6) {
            passwordInput.setError("The code must contain at least 6 characters");
            return false;
        }
        return true;
    }

    public static boolean checkEditText(Context context, boolean cond, EditText editText){
        if(!cond){
            Toast.makeText(context, editText.getError().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
