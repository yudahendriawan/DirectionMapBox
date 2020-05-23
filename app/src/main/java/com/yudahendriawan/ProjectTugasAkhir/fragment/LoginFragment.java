package com.yudahendriawan.ProjectTugasAkhir.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yudahendriawan.ProjectTugasAkhir.MainActivity;
import com.yudahendriawan.ProjectTugasAkhir.MenuActivity;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView RegText;
    OnLoginFormActivityListener loginFormActivityListener;

    private EditText user_name, user_password;
    private Button btn_login;

    public interface OnLoginFormActivityListener {
        public void performRegister();

        public void performLogin(String name);
    }


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        RegText = view.findViewById(R.id.register_txt);
        user_name = view.findViewById(R.id.user_name);
        user_password = view.findViewById(R.id.user_password);
        btn_login = view.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        RegText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFormActivityListener.performRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;
    }

    private void performLogin() {
        String username = user_name.getText().toString();
        String userpassword = user_password.getText().toString();

        Call<User> call = MenuActivity.apiInterface.performUserLogin(username, userpassword);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok")) {
                    MenuActivity.prefConfig.writeLoginStatus(true);
                    loginFormActivityListener.performLogin(response.body().getName());

                } else if (response.body().getResponse().equals("failed")) {
                    MenuActivity.prefConfig.displayToast("Login Failed, Please try again");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        user_name.setText("");
        user_password.setText("");
    }

}
