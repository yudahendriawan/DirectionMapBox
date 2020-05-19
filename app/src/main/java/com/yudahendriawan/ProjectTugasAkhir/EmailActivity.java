package com.yudahendriawan.ProjectTugasAkhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class EmailActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" + //at least 1 digit number
                    "(?=.*[a-z])" + // at least 1 lowercase alpha
                    "(?=.*[A-Z])" + // at least 1 uppercase alpha
//                    "(?=.*[a-zA-Z])"+ //any letter
                    "(?=.*[!@#$%^&*+=])" + // at least 1 special char in list
                    "(?=\\S+$)" + //no white spaces allowed
                    ".{6,}" + // min 8 max 15
                    "$");

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private Button confirm;

    String emailInput;
    String usernameInput;
    String passwordInput;

    private String email = "yuda@gmail.com";
    private String username = "yuda";
    private String password = "aA1%Aa";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        textInputEmail = findViewById(R.id.text_input_email);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);

    }

    private boolean validateEmail() {
        emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            textInputEmail.requestFocus();
            return false;
            //regular expression (ReGex)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            textInputEmail.requestFocus();
            return false;
        } else {
            textInputEmail.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            textInputUsername.requestFocus();
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            textInputUsername.requestFocus();
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {
        passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            textInputPassword.requestFocus();
            return false;
            //regex check
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password must contain at least one lowercase letter, one uppercase letter, one numeric digit, and one special character");
            textInputPassword.requestFocus();
            return false;
        } else {
            textInputPassword.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    public void confirmInput(View v) {

        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        } else if (validateEmail() | validateUsername() | validatePassword()) {
            if (!emailInput.equals(email)) {
                textInputEmail.setError("Wrong email");
                textInputEmail.requestFocus();
                return;
            }
            if (!usernameInput.equals(username)) {
                textInputUsername.setError("Wrong username");
                textInputUsername.requestFocus();
                return;
            }
            if (!passwordInput.equals(password)) {
                textInputPassword.setError("Wrong password");
                textInputPassword.requestFocus();
                return;
            } else {
                String input = "Email:" + textInputEmail.getEditText().getText().toString();
                input += "\n";
                input += "Username :" + textInputUsername.getEditText().getText().toString();
                input += "\n";
                input += "Password : " + textInputPassword.getEditText().getText().toString();
                Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }
        }


    }
}
