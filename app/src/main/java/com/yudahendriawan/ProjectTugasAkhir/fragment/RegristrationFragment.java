package com.yudahendriawan.ProjectTugasAkhir.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.yudahendriawan.ProjectTugasAkhir.MenuActivity;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegristrationFragment extends Fragment {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" + //at least 1 digit number
//                    "(?=.*[a-z])" + // at least 1 lowercase alpha
//                    "(?=.*[A-Z])" + // at least 1 uppercase alpha
                    "(?=.*[a-zA-Z])" + //any letter
//                    "(?=.*[!@#$%^&*+=])" + // at least 1 special char in list
                    "(?=\\S+$)" + //no white spaces allowed
                    ".{6,}" + // min 8 max 15
                    "$");

    private TextInputLayout name, user_name, user_password;
    private Button btn_register;
    private ProgressBar progressBar_Loader;

    String nameInput;
    String usernameInput;
    String passwordInput;

    public RegristrationFragment() {
        // Required empty public constructor
    }


    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regristration, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        name = view.findViewById(R.id.name);
        user_name = view.findViewById(R.id.user_name);
        user_password = view.findViewById(R.id.user_password);
        btn_register = view.findViewById(R.id.btn_register);
        progressBar_Loader = view.findViewById(R.id.progress_loader);

        progressBar_Loader.setVisibility(View.INVISIBLE);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar_Loader.setVisibility(View.VISIBLE);
                btn_register.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        confirmInput();
                    }
                }, 1500);

            }
        });
        return view;
    }

    public void performRegistration() {
//        String nama = name.getEditText().getText().toString().trim();
//        String username = user_name.getEditText().getText().toString().trim();
//        String userpassword = user_password.getEditText().getText().toString().trim();


        Call<User> call = MenuActivity.apiInterface.performRegristration(nameInput, usernameInput, passwordInput);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok")) {
                    MenuActivity.prefConfig.displayToast("Registration Success");
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                } else if (response.body().getResponse().equals("exist")) {
                    MenuActivity.prefConfig.displayToast("User Already Exist");
                    progressBar_Loader.setVisibility(View.GONE);
                    btn_register.setVisibility(View.VISIBLE);
                } else if (response.body().getResponse().equals("error")) {
                    MenuActivity.prefConfig.displayToast("Something went wrong");
                    progressBar_Loader.setVisibility(View.GONE);
                    btn_register.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        name.getEditText().setText("");
        user_name.getEditText().setText("");
        user_password.getEditText().setText("");
    }

    private boolean validateName() {
        nameInput = name.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()) {
            name.setError("Field can't be empty");
            name.requestFocus();
            return false;
        } else {
            name.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateUsername() {
        usernameInput = user_name.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            user_name.setError("Field can't be empty");
            user_name.requestFocus();
            return false;
        } else if (usernameInput.length() > 15) {
            user_name.setError("Username too long");
            user_name.requestFocus();
            return false;
        } else {
            user_name.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        passwordInput = user_password.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            user_password.setError("Field can't be empty");
            user_password.requestFocus();
            return false;
            //regex check
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            user_password.setError("Password must contain at least one lowercase letter, one uppercase letter, one numeric digit, and one special character");
            user_password.requestFocus();
            return false;
        } else {
            user_password.setError(null);
//            textInputEmail.setErrorEnabled(false);
            return true;
        }

    }

    public void confirmInput() {
        if (!validateName() | !validateUsername() | !validatePassword()) {
            progressBar_Loader.setVisibility(View.GONE);
            btn_register.setVisibility(View.VISIBLE);
            return;
        } else {
            performRegistration();
        }
    }

}
