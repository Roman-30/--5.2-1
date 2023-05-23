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
            firstNameInput.setError("Введите имя");
            return false;
        } else if(!firstName.matches("[a-zA-Z]+")) {
            firstNameInput.setError("Имя может содержать только буквы");
            return false;
        }
        return true;
    }

    public static boolean isValidSecondName(EditText secondNameInput) {
        String secondName = secondNameInput.getText().toString().trim();
        if(secondName.isEmpty()) {
            secondNameInput.setError("Введите фамилию");
            return false;
        } else if(!secondName.matches("[a-zA-Z]+")) {
            secondNameInput.setError("Фамилия может содержать только буквы");
            return false;
        }
        return true;
    }

    public static boolean isValidNickname(EditText nickNameInput) {
        String nickName = nickNameInput.getText().toString().trim();
        if(nickName.isEmpty()) {
            nickNameInput.setError("Введите никнейм");
            return false;
        } else if(!nickName.matches("[a-zA-Z0-9]+")) {
            nickNameInput.setError("Никнейм может содержать только буквы и цифры");
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(EditText emailInput) {
        String email = emailInput.getText().toString().trim();
        if(email.isEmpty()) {
            emailInput.setError("Введите email");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Введите правильный email");
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(EditText phoneNumberInput) {
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        if(phoneNumber.isEmpty()) {
            phoneNumberInput.setError("Введите номер телефона");
            return false;
        } else if(!phoneNumber.matches("\\d{10}")) {
            phoneNumberInput.setError("Введите правильный номер телефона (10 цифр)");
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(EditText passwordInput) {
        String password = passwordInput.getText().toString().trim();
        if(password.isEmpty()) {
            passwordInput.setError("Введите пароль");
            return false;
        } else if(password.length() < 6) {
            passwordInput.setError("Пароль должен содержать не менее 6 символов");
            return false;
        }
        return true;
    }

    public static boolean isValidCode(EditText passwordInput) {
        String password = passwordInput.getText().toString().trim();
        if(password.isEmpty()) {
            passwordInput.setError("Введите код");
            return false;
        } else if(password.length() < 6) {
            passwordInput.setError("Код должен содержать не менее 6 символов");
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
