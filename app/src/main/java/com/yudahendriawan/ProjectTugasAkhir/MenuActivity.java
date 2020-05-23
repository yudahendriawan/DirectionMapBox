package com.yudahendriawan.ProjectTugasAkhir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.yudahendriawan.ProjectTugasAkhir.api.ApiClient;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiInterface;
import com.yudahendriawan.ProjectTugasAkhir.config.PrefConfig;
import com.yudahendriawan.ProjectTugasAkhir.fragment.LoginFragment;
import com.yudahendriawan.ProjectTugasAkhir.fragment.MenuFragment;
import com.yudahendriawan.ProjectTugasAkhir.fragment.RegristrationFragment;
import com.yudahendriawan.ProjectTugasAkhir.util.Key;
import com.yudahendriawan.ProjectTugasAkhir.wisata.WisataActivity;

public class MenuActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener, MenuFragment.OnLogoutListener {
//    CardView findRoute, wisata, login;
//    Graph graph;

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            if (prefConfig.readLoginStatus()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MenuFragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFragment()).commit();
            }
        }

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        getSupportActionBar().setTitle(Key.MENU_TITLE);
//
//
//        findRoute = findViewById(R.id.findRoute);
//        wisata = findViewById(R.id.listWisata);
//        login = findViewById(R.id.login);
//        // int vertices = 31;
//        //  graph = new Graph(vertices, this);
//
//        findRoute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // graph.addEdgeDB();
//                // Toast.makeText(v.getContext(), "Get Data Form Database", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), MainActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        wisata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), WisataActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), EmailActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void performRegister() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RegristrationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void performLogin(String name) {
        prefConfig.writeName(name);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MenuFragment())
                .commit();
    }

    @Override
    public void logoutPerform() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }
}
